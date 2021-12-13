package com.nttdata.affiliation.infraestructure.repository;

import com.nttdata.affiliation.application.CreditMovementRepository;
import com.nttdata.affiliation.domain.CreditMovement;
import com.nttdata.affiliation.domain.bean.*;
import com.nttdata.affiliation.infraestructure.client.UriService;
import com.nttdata.affiliation.infraestructure.model.dao.CreditMovementDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CREDITMOVEMENTCRUDREPOSITORY.
 * Implementa las operaciones (CRUD) de los movimientos de Credito
 */
@Component
@Slf4j
public class CreditMovementCrudRepository
        implements CreditMovementRepository {

    /**
     * Operaciones de movimientos de credito.
     */
    private final ICreditMovementCrudRepository repository;
    /**
     * Servicio web cliente.
     */
    private final WebClient webClient;
    /**
     * Circuit Breaker.
     */
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    /**
     * Constructor.
     * @param circuitBreakerFactory corto circuito.
     * @param iCreditMovementCrudRepository repositorio.
     */
    public
    CreditMovementCrudRepository(
            final ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory,
            final ICreditMovementCrudRepository iCreditMovementCrudRepository) {
        this.repository = iCreditMovementCrudRepository;
        this.webClient = WebClient.builder()
                .baseUrl(UriService.BASE_URI)
                .build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("creditMovement");
    }

    /**
     * Regitra los movimientos de credito de un cliente.
     * @param creditMovement movimiento.
     * @return
     */
    @Override
    public Mono<CreditMovement>
    create(CreditMovement creditMovement) {
        log.info("[create] Inicio");
        return repository
                .save(
                        mapCreditMovementToCreditMovementDao(
                                creditMovement
                        )
                )
                .map(this::mapCreditMovementDaoToCreditMovement);
    }

    /**
     * Actualiza el movimiento de credito de un cliente.
     * @param id codigo.
     * @param creditMovement movimiento de credito.
     * @return Mono<CreditMovement>
     */
    @Override
    public
    Mono<CreditMovement>
    update(String id, CreditMovement creditMovement) {
        return repository
                .findById(id)
                .flatMap(p ->
                        create(
                                mapCreditMovementDaoToCreditMovement(
                                        p, creditMovement)
                        )
                );
    }

    /**
     * Elimina los datos del movimiento de credito de un cliente.
     * @param id codigo.
     * @return Mono<Void>
     */
    @Override
    public
    Mono<Void>
    delete(String id) {
        return repository.findById(id)
                .flatMap(p -> repository.deleteById(p.getId()));
    }

    /**
     * Busca por Id los datos del movimiento de credito.
     * @param id codigo.
     * @return Mono<CreditMovement>
     */
    @Override
    public
    Mono<CreditMovement>
    findById(String id) {
        return repository.findById((id))
                .map(this::mapCreditMovementDaoToCreditMovement);
    }

    /**
     * Busca todas los movimientos de credito de un cliente.
     * @return
     */
    @Override
    public Flux<CreditMovement>
    findAll() {
        return repository.findAll()
                .map(this::mapCreditMovementDaoToCreditMovement);
    }

    /**
     * Actualiza los datos de la afiliacion.
     * @param idAffiliation codigo de afiliacion.
     * @param creditAffiliation datos de la afiliacion.
     * @return Mono<CreditAffiliation>
     */
    @Override
    public
    Mono<CreditAffiliation>
    putCreditAffiliation(String idAffiliation, CreditAffiliation creditAffiliation) {
        log.info("[getAccountAffiliationById] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .put()
                                .uri(
                                        UriService.AFFILIATION_CREDIT_PUT,
                                        idAffiliation, creditAffiliation
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(CreditAffiliation.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getAccountAffiliationById] Error en la llamada:"
                                    + UriService.AFFILIATION_CREDIT_PUT
                                    + idAffiliation +":" + creditAffiliation);
                            return Mono.just(new CreditAffiliation());
                        });
    }

    /**
     * Obtenemos los datos de la afiliación de credito.
     * @param idCustomer Codigo de la afilicacion.
     * @return Mono<CreditAffiliation>
     */
    @Override
    public
    Mono<CreditAffiliation>
    getCreditAffiliationById(String idCustomer) {
        log.info("[getAccountAffiliationById] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.AFFILIATION_CREDIT_GET_BY_ID,
                                        idCustomer
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(CreditAffiliation.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getAccountAffiliationById] Error en la llamada:"
                                    + UriService.AFFILIATION_CREDIT_GET_BY_ID
                                    + idCustomer);
                            return Mono.just(new CreditAffiliation());
                        });
    }

    /**
     * Obtenemos los datos del cliente.
     * @param idCustomer Codigo del cliente.
     * @return Mono<Customer>
     */
    @Override
    public
    Mono<Customer>
    getCustomerById(String idCustomer) {
        log.info("[getCustomerById] Inicio:"+idCustomer);
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.CUSTOMER_GET_BY_ID,
                                        idCustomer
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(Customer.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getCustomerById] Error en la llamada:"
                                    + UriService.CUSTOMER_GET_BY_ID
                                    + idCustomer);
                            return Mono.just(new Customer());
                        });
    }

    /**
     * Obtenemos los datos del producto: Credito.
     * @param idCredit codigo del credito
     * @return Mono<Account>
     */
    @Override
    public
    Mono<Credit>
    getProductCreditById(String idCredit) {
        log.info("[getProductAccountById] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.PRODUCT_CREDIT_GET_BY_ID,
                                        idCredit
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(Credit.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getProductAccountById] Error en la llamada:"
                                    + UriService.PRODUCT_CREDIT_GET_BY_ID
                                    + idCredit);
                            return Mono.just(new Credit());
                        });
    }

    /**
     * Obtiene los movimientos por codigo de afiliacion.
     * @param idAffiliation Codigo de afiliacion.
     * @return Flux<CreditMovement>
     */
    @Override
    public Flux<CreditMovement> findByIdAffiliation(String idAffiliation) {
        return repository.findByIdAffiliation(idAffiliation);
    }

    /**
     * Crea CreditMovement y asigna los datos de CreditMovementDao.
     * @param creditMovement Movimiento de credito.
     * @return CreditMovementDao
     */
    private CreditMovementDao
    mapCreditMovementToCreditMovementDao(
            final CreditMovement creditMovement) {

        CreditMovementDao creditMovementDao
                = new CreditMovementDao();
        BeanUtils.copyProperties(creditMovement, creditMovementDao);
        return creditMovementDao;
    }
    /**
     * Crea CreditMovement y asigna los datos de CreditMovementDao.
     * @param creditMovementDao movimiento de credit.
     * @return CreditMovement
     */
    private
    CreditMovement
    mapCreditMovementDaoToCreditMovement(
            final CreditMovementDao creditMovementDao) {

        log.info("[mapCreditMovementDaoToCreditMovement] Inicio");
        CreditMovement creditMovement = new CreditMovement();
        BeanUtils.copyProperties(creditMovementDao, creditMovement);
        //Complementamos los datos faltantes
        Mono<CreditAffiliation> creditAffiliation = getCreditAffiliationById(creditMovementDao.getIdCreditAffiliation());
        Mono<Customer> customers = getCustomerById(creditAffiliation.block().getIdCustomer());
        creditAffiliation.block().setCustomer(customers.block());
        Mono<Credit> credit = getProductCreditById(creditAffiliation.block().getIdCredit());
        creditAffiliation.block().setCredit(credit.block());
        //Asignamos los datos de la afiliacion
        creditMovement.setCreditAffiliation(creditAffiliation.block());
        log.info("[mapAccountMovementDaoToAccountMovement] Fin");
        return creditMovement;
    }
    /**
     * Asigna el Id de CreditMovementDao a CreditMovement.
     * @param creditMovementDao movimiento de credito Dao.
     * @param creditMovement movimiento de credito.
     * @return CreditMovement
     */
    private
    CreditMovement
    mapCreditMovementDaoToCreditMovement(
            final CreditMovementDao creditMovementDao,
            final CreditMovement creditMovement) {

        creditMovement.setId(creditMovementDao.getId());
        return creditMovement;
    }
}
