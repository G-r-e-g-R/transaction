package com.nttdata.affiliation.application;

import com.nttdata.affiliation.domain.CreditMovement;
import com.nttdata.affiliation.domain.CreditMovementType;
import com.nttdata.affiliation.domain.bean.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CREDITMOVEMENTOPERATIONSIMPL.
 * Implementa las operaciones (CRUD) de los movimientos
 *                         de Credito de un cliente.
 */
@Service
@Slf4j
public class CreditMovementOperationsImpl
            implements CreditMovementOperations{

    /**
     * Repositorio de los registros de movimiento de creditos.
     */
    private final CreditMovementRepository creditMovementRepository;

    /**
     * Constructor.
     * @param repository repositorio.
     */
    public
    CreditMovementOperationsImpl(
            final CreditMovementRepository repository) {
        this.creditMovementRepository = repository;
    }
    /**
     * Creación de un movimiento de credito para un cliente.
     * @param creditMovement movimiento de credito.
     * @return Mono<CreditMovement>
     */
    @Override
    public
    Mono<CreditMovement>
    create(CreditMovement creditMovement) {
        //validamos el limite de consumo
        Mono<CreditAffiliation> creditAffiliation
                = getCreditAffiliation(creditMovement.getIdCreditAffiliation());
        return creditAffiliation
                .filter(a ->
                    a.getCreditLimit() > a.getBalance() + creditMovement.getAmount()
                )
                .flatMap(__ -> creditMovementRepository
                        .create(creditMovement)
                        .map(a -> { updateCreditAffiliation(creditMovement);
                                    return a;})
                        .switchIfEmpty(Mono.just(new CreditMovement())))
                .switchIfEmpty(Mono.just(new CreditMovement()));

    }

    /**
     * Actualiza el movimiento de credito.
     * @param id codigo.
     * @param creditMovement movimiento de credito.
     * @return Mono<CreditMovement>
     */
    @Override
    public
    Mono<CreditMovement>
    update(String id, CreditMovement creditMovement) {
        //Actualiza el saldo
        updateCreditAffiliation(creditMovement);
        return creditMovementRepository.findById(id)
                .flatMap(a -> {
                    a.setAmount(creditMovement.getAmount());
                    return creditMovementRepository.update(id,a)
                            .map(b -> {
                                updateCreditAffiliation(creditMovement);
                                return b;
                            });
                })
                .switchIfEmpty(Mono.just(new CreditMovement()));
    }

    /**
     * Elimina un movimiento de credit.
     * @param id codigo.
     * @return Mono<Void>
     */
    @Override
    public
    Mono<Void>
    delete(String id) {
        return creditMovementRepository.findById(id)
                .flatMap(a -> {
                    changeOperation(a);
                    updateCreditAffiliation(a);
                    return creditMovementRepository.delete(id);
                });
    }

    /**
     * Busca un movimimiento de credito.
     * @param id codigo.
     * @return
     */
    @Override
    public
    Mono<CreditMovement>
    findById(String id) {
        return creditMovementRepository.findById(id);
    }

    /**
     * Busca todos los movimientos de creditos.
     * @return
     */
    @Override
    public
    Flux<CreditMovement>
    findAll() {
        return creditMovementRepository.findAll();
    }

    /**
     * Obtenemos los datos de la afiliación del credito.
     * @param idAffiliation Codigo de la afilicacion.
     * @return
     */
    @Override
    public Mono<CreditAffiliation> getCreditAffiliationById(String idAffiliation) {
        log.info("[getCreditAffiliationById] idAffiliation:"+idAffiliation);
        return creditMovementRepository.getCreditAffiliationById(idAffiliation);
    }

    /**
     * Obtenemos los datos del cliente.
     * @param idCustomer Codigo del cliente.
     * @return Mono<Customer>
     */
    @Override
    public Mono<Customer> getCustomerById(String idCustomer) {
        log.info("[getCustomerById] idAccount:"+idCustomer);
        return creditMovementRepository.getCustomerById(idCustomer);
    }

    /**
     * Obtenemos los datos del producto: Creditos.
     * @param idCredit codigo del credito.
     * @return Mono<Credit>
     */
    @Override
    public Mono<Credit> getProductCreditById(String idCredit) {
        log.info("[getProductCreditById] idCredit:"+idCredit);
        return creditMovementRepository.getProductCreditById(idCredit);
    }

    /**
     * Obtiene los movimientos por afiliacion
     * @param idAffiliation Codigo de afiliacion.
     * @return Flux<CreditMovement>
     */
    @Override
    public Flux<CreditMovement> findByIdAffiliation(String idAffiliation) {
        return creditMovementRepository.findByIdAffiliation(idAffiliation);
    }

    /**
     * Actualiza los datos de la afiliacion.
     * @param idAffiliation codigo de afiliacion.
     * @param creditAffiliation datos de la afiliacion.
     * @return
     */
    @Override
    public
    Mono<CreditAffiliation>
    putCreditAffiliation(String idAffiliation, CreditAffiliation creditAffiliation) {
        log.info("[putCreditAffiliation] idAffiliation:"+idAffiliation);
        return creditMovementRepository.putCreditAffiliation(idAffiliation,creditAffiliation);
    }
    /**
     * Obtiene el tipo de operación.
     * @param creditMovement movimiento.
     * @return Integer.
     */
    private
    Integer
    getOperation(CreditMovement creditMovement) {
        return creditMovement.getMovementType()
                .equals(CreditMovementType.PAGOS)
                ?-1 :1;
    }

    /**
     * Obtenemos la afiliación.
     * @param idAffiliation Codigo afiliacion.
     * @return Mono<CreditAffiliation>
     */
    private
    Mono<CreditAffiliation>
    getCreditAffiliation(String idAffiliation) {
        return creditMovementRepository
                .getCreditAffiliationById(
                        idAffiliation
                );
    }

    /**
     * Actualiza el balance de la afiliacion.
     * @param creditAffiliation afiliacion de cuenta.
     * @param operation Tipo de operacion.
     */
    private
    void
    updateBalanceCredit(Mono<CreditAffiliation> creditAffiliation,
                         Integer operation,
                         CreditMovement creditMovement){
        creditAffiliation.map(a -> {
            a.setBalance(a.getBalance() + creditMovement.getAmount()*operation);
            return a;
        }).map(a -> creditMovementRepository.putCreditAffiliation(a.getIdCredit(), a));
    }

    /**
     * Actualiza el saldo de la afiliación.
     * @param creditMovement movimiento
     */
    private
    void
    updateCreditAffiliation(CreditMovement creditMovement) {
        //Identificamos el tipo de operacion.
        final Integer operation = getOperation(creditMovement);
        //Obtenemos la afiliación
        Mono<CreditAffiliation> creditAffiliation
                = getCreditAffiliation(creditMovement.getIdCreditAffiliation());
        //actualizamos la afiliación
        updateBalanceCredit(creditAffiliation, operation, creditMovement);
    }
    /**
     * Cambia la operación para la eliminación del movimiento
     * @param creditMovement movimiento.
     */
    private void changeOperation(CreditMovement creditMovement) {
        creditMovement
                .setMovementType(
                        creditMovement
                                .getMovementType()
                                .equals(
                                        CreditMovementType.PAGOS
                                )
                                ? CreditMovementType.CARGO
                                : CreditMovementType.PAGOS);

    }
}
