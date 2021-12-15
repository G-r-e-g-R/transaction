package com.nttdata.affiliation.application;

import com.nttdata.affiliation.domain.AccountMovement;
import com.nttdata.affiliation.domain.AccountMovementType;
import com.nttdata.affiliation.domain.bean.Account;
import com.nttdata.affiliation.domain.bean.AccountAffiliation;
import com.nttdata.affiliation.domain.bean.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * ACCOUNTMOVEMENTOPEATIONSIMPL.
 * Implementa las operaciones (CRUD) de los movimientos
 *                         de Cuentas Bancarias de un cliente
 */
@Service
@Slf4j
public class AccountMovementOperationsImpl
        implements  AccountMovementOperations{
    /**
     * Repositorio de los movimientos de las cuentas bancarias.
     */
    private final  AccountMovementRepository repository;

    /**
     * Constructor.
     * @param accountMovementRepository
     */
    public AccountMovementOperationsImpl(AccountMovementRepository accountMovementRepository){
        this.repository = accountMovementRepository;
    }

    /**
     * Registro de un movimiento de cuenta bancaria.
     * @param accountMovement movimiento.
     * @return Mono<AccountMovement>
     */
    @Override
    public
    Mono<AccountMovement>
    create(AccountMovement accountMovement) {
        //Registramos la operacion.
        return repository.create(accountMovement)
                .map(a -> { updateAccountAffiliation(accountMovement); return a;})
                .switchIfEmpty(Mono.just(new AccountMovement()));
    }

    /**
     * Actualiza el movimiento de cuenta bancaria.
     * @param id codigo.
     * @param accountMovement movimiento de cuenta.
     * @return Mono<AccountMovement>
     */
    @Override
    public
    Mono<AccountMovement>
    update(String id, AccountMovement accountMovement) {
        //Actualiza el saldo
        updateAccountAffiliation(accountMovement);
        return repository.findById(id)
                .flatMap(a -> {
                    a.setAmount(accountMovement.getAmount());
                    return repository.update(id,a)
                            .map(b -> {
                                updateAccountAffiliation(accountMovement);
                                return b;
                            });
                })
                .switchIfEmpty(Mono.just(new AccountMovement()));
    }

    /**
     * Obtiene el tipo de operación.
     * @param accountMovement movimiento.
     * @return Integer.
     */
    private
    Integer
    getOperation(AccountMovement accountMovement) {
        return accountMovement.getMovementType()
                .equals(AccountMovementType.RETIRO)
                ?-1 :1;
    }

    /**
     * Obtenemos la afiliación.
     * @param idAffiliation Codigo afiliacion.
     * @return Mono<AccountAffiliation>
     */
    private
    Mono<AccountAffiliation>
    getAccountAffiliation(String idAffiliation) {
        log.info("[getAccountAffiliation] idAffiliation: "+idAffiliation);
        return repository
                .getAccountAffiliationById(
                        idAffiliation
                );
    }

    /**
     * Actualiza el balance de la afiliacion.
     * @param accountAffiliation afiliacion de cuenta.
     * @param operation Tipo de operacion.
     */
    private
    void
    updateBalanceAccount(AccountAffiliation accountAffiliation,
                         Integer operation,
                         AccountMovement accountMovement){
        log.info("[updateBalanceAccount] Inicio - operation: "+operation+ " accountMovement:"+accountMovement);
        log.info("[updateBalanceAccount] accountAffiliation: "+accountAffiliation);
        accountAffiliation.setBalance(
                accountAffiliation.getBalance() + accountMovement.getAmount()*operation);
        repository.putAccountAffiliation(accountAffiliation.getId(), accountAffiliation).subscribe();
        log.info("[updateBalanceAccount] accountAffiliation.getBalance(): "+accountAffiliation.getBalance());
        log.info("[updateBalanceAccount] Fin: ");
    }

    /**
     * Actualiza el saldo de la afiliación.
     * @param accountMovement movimiento
     */
    private
    void
    updateAccountAffiliation(AccountMovement accountMovement) {
        //Identificamos el tipo de operacion.
        final Integer operation = getOperation(accountMovement);
        //Obtenemos la afiliación
        log.info("[updateAccountAffiliation] idAffiliation:"+accountMovement.getIdAccountAffiliation());
        getAccountAffiliation(accountMovement.getIdAccountAffiliation())
                .flatMap(a -> {updateBalanceAccount(a, operation, accountMovement); return Mono.empty();}).subscribe();


    }

    /**
     * Elimina un movimiento de cuenta bancaria.
     * @param id codigo.
     * @return
     */
    @Override
    public
    Mono<Void>
    delete(String id) {
        return repository.findById(id)
                .flatMap(a -> {
                    changeOperation(a);
                    updateAccountAffiliation(a);
                    return repository.delete(id);
                });
    }

    /**
     * Cambia la operación para la eliminación del movimiento
     * @param accountMovement movimiento.
     */
    private void changeOperation(AccountMovement accountMovement) {
        accountMovement
                .setMovementType(
                        accountMovement
                                .getMovementType()
                                .equals(
                                        AccountMovementType.RETIRO
                                )
                                ? AccountMovementType.DEPOSITO
                                : AccountMovementType.RETIRO);

    }

    /**
     * Busca un movimimiento de cuenta bancaria.
     * @param id codigo.
     * @return  Mono<AccountMovement>
     */
    @Override
    public
    Mono<AccountMovement>
    findById(String id) {
        return repository.findById(id);
    }

    /**
     * Busca todos los movimientos de cuentas bancarias.
     * @return Flux<AccountMovement>
     */
    @Override
    public
    Flux<AccountMovement>
    findAll() {
        return repository.findAll();
    }

    /**
     * Obtenemos la afilicación de la cuenta bancaria.
     * @param idAffiliation Codigo de la afilicacion.
     * @return Mono<AccountAffiliation>
     */
    @Override
    public
    Mono<AccountAffiliation>
    getAccountAffiliationById(String idAffiliation) {
        log.info("[getAccountAffiliationById] idAffiliation:"+idAffiliation);
        return repository.getAccountAffiliationById(idAffiliation);
    }

    /**
     * Actualiza los datos de la afiliacion.
     * @param idAffiliation codigo de afiliacion.
     * @param accountAffiliation datos de la afiliacion.
     * @return
     */
    @Override
    public
    Mono<AccountAffiliation>
    putAccountAffiliation(String idAffiliation, AccountAffiliation accountAffiliation) {
        log.info("[putAccountAffiliation] idAffiliation:"+idAffiliation);
        return repository.putAccountAffiliation(idAffiliation,accountAffiliation);
    }

    /**
     * Obtenemos los datos del cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<Customer>
     */
    @Override
    public
    Mono<Customer>
    getCustomerById(String idCustomer) {
        log.info("[getCustomerById] idAccount:"+idCustomer);
        return repository.getCustomerById(idCustomer);
    }

    /**
     * Obtenemos los datos del producto: Cuenta Bancaria.
     * @param idAccount codigo de la cuenta bancaria
     * @return Flux<Account>
     */
    @Override
    public
    Mono<Account>
    getProductAccountById(String idAccount) {
        log.info("[getProductAccountById] idAccount:"+idAccount);
        return repository.getProductAccountById(idAccount);
    }
    /**
     * Listado de movmientos de cuentas por Afiliacion.
     * @param idAffiliation Codigo de afiliacion.
     * @return Flux<AccountMovement>
     */
    @Override
    public
    Flux<AccountMovement>
    findByIdAffiliation(String idAffiliation) {
        return repository.findByIdAffiliation(idAffiliation);
    }

}
