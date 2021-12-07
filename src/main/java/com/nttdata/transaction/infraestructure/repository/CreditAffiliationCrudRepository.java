package com.nttdata.transaction.infraestructure.repository;

import com.nttdata.transaction.application.CreditAffiliationRepository;
import com.nttdata.transaction.domain.CreditAffiliation;
import com.nttdata.transaction.domain.bean.Credit;
import com.nttdata.transaction.domain.bean.Customer;
import com.nttdata.transaction.infraestructure.client.UriService;
import com.nttdata.transaction.infraestructure.model.dao.CreditAffiliationDao;
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
 * CREDITAFFILIATIONCRUDREPOSITORY.
 * Implementa las operaciones (CRUD) de la afiliación de Credito
 */
@Component
@Slf4j
public class CreditAffiliationCrudRepository
        implements CreditAffiliationRepository {
    /**
     *  Operaciones de afiliación de creditos.
     */
    private final ICreditAffiliationCrudRepository repository;
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
     * @param circuitBreakerFactory
     * @param iCreditAffiliationCrudRepository
     */
    public CreditAffiliationCrudRepository(
            final ReactiveResilience4JCircuitBreakerFactory
                    circuitBreakerFactory,
            final ICreditAffiliationCrudRepository
                    iCreditAffiliationCrudRepository) {
        this.repository = iCreditAffiliationCrudRepository;
        this.webClient = WebClient
                .builder()
                .baseUrl(UriService.BASE_URI)
                .build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("credit");
    }
    /**
     * Regitra las afiliaciones de credito de un cliente.
     * @param creditAffiliation
     * @return Mono<CreditAffiliation>
     */
    @Override
    public Mono<CreditAffiliation> create(
            final CreditAffiliation creditAffiliation) {
        return repository
                .save(
                        mapCreditAffiliationToCreditAffiliationDao(
                                creditAffiliation
                        )
                )
                .map(this::mapCreditAffiliationDaoToCreditAffiliation);
    }
    /**
     * Actualiza las afiliaciones de credito de un cliente.
     * @param id
     * @param creditAffiliation
     * @return Mono<CreditAffiliation>
     */
    @Override
    public Mono<CreditAffiliation> update(
            final String id, final CreditAffiliation creditAffiliation) {
        return repository
                .findById(id)
                .flatMap(p ->  create(
                        mapCreditAffiliationDaoToCreditAffiliation(
                                p, creditAffiliation
                        ))
                );
    }
    /**
     * Elimina los datos de la afiliacion de Credito de un cliente.
     * @param id
     * @return Mono<CreditAffiliationDao>
     */
    @Override
    public Mono<CreditAffiliationDao> delete(final String id) {
        return repository
                .findById(id)
                .flatMap(p -> repository.deleteById(p.getId()).thenReturn(p));
    }
    /**
     * Busca por el Id los datos de la afiliacion de credito de un cliente.
     * @param id
     * @return Mono<CreditAffiliation>
     */
    @Override
    public Mono<CreditAffiliation> findById(final String id) {
        return repository.findById((id))
                .map(this::mapCreditAffiliationDaoToCreditAffiliation);
    }
    /**
     * Busca  los datos de todas las afiliaciones de credito de un cliente.
     * @return Flux<CreditAffiliation>
     */
    @Override
    public Flux<CreditAffiliation> findAll() {
        return repository
                .findAll()
                .map(this::mapCreditAffiliationDaoToCreditAffiliation);
    }
    /*
    mapCreditAffiliationToCreditAffiliationDao:

     */

    /**
     * A la clase CreditAffiliation asigna los datos de CreditAffiliationDao.
     * @param creditAffiliation
     * @return CreditAffiliationDao
     */
    private CreditAffiliationDao mapCreditAffiliationToCreditAffiliationDao(
            final CreditAffiliation creditAffiliation) {
        CreditAffiliationDao creditAffiliationDao = new CreditAffiliationDao();
        BeanUtils.copyProperties(creditAffiliation, creditAffiliationDao);
        return creditAffiliationDao;
    }
    /**
     * A la clase CreditAffiliation asigna los datos de CreditAffiliationDao.
     * @param creditAffiliationDao
     * @return CreditAffiliation
     */
    private CreditAffiliation mapCreditAffiliationDaoToCreditAffiliation(
            final CreditAffiliationDao creditAffiliationDao) {
        log.info("[mapCreditAffiliationDaoToCreditAffiliation] Inicio");
        CreditAffiliation creditAffiliation = new CreditAffiliation();
        BeanUtils.copyProperties(creditAffiliationDao, creditAffiliation);
        Flux<Customer> customers = getCustomerById(creditAffiliationDao);
        creditAffiliation.setCustomer(customers.blockFirst());
        Flux<Credit> credits = getProductCreditById(creditAffiliationDao);
        creditAffiliation.setCredit(credits.blockFirst());
        log.info("[mapCreditAffiliationDaoToCreditAffiliation] Fin");
        return creditAffiliation;
    }
    /**
     * Asigna el Id de CreditAffiliationDao a CreditAffiliation.
     * @param creditAffiliationDao
     * @param creditAffiliation
     * @return CreditAffiliation
     */
    private CreditAffiliation mapCreditAffiliationDaoToCreditAffiliation(
            final CreditAffiliationDao creditAffiliationDao,
            final CreditAffiliation creditAffiliation) {
        creditAffiliation.setId(creditAffiliationDao.getId());
        return creditAffiliation;
    }
    /**
     * Obtenemos los datos del cliente.
     * @param creditAffiliationDao
     * @return Flux<Customer>
     */
    public   Flux<Customer> getCustomerById(
            final CreditAffiliationDao creditAffiliationDao) {
        log.info("[getCustomerById] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.CUSTOMER_GET_BY_ID,
                                        creditAffiliationDao.getIdCustomer()
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(Customer.class),
                throwable -> {
                    log.info("throwable => {}", throwable.toString());
                    log.info("[getCustomerById] Error en la llamada:"
                            + UriService.CUSTOMER_GET_BY_ID
                            + creditAffiliationDao.getIdCustomer());
                    return Flux.just(new Customer());
                });
    }
    /**
     * Obtenemos los datos del producto: Credito.
     * @param creditAffiliationDao
     * @return Flux<Credit>
     */
    public   Flux<Credit> getProductCreditById(
            final CreditAffiliationDao creditAffiliationDao) {
        log.info("[getProductCreditById] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.PRODUCT_CREDIT_GET_BY_ID,
                                        creditAffiliationDao.getIdCredit()
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(Credit.class),
                throwable -> {
                    log.info("throwable => {}", throwable.toString());
                    log.info("[getProductCreditById] Error en la llamada:"
                            + UriService.PRODUCT_CREDIT_GET_BY_ID
                            + creditAffiliationDao.getIdCredit());
                    return Flux.just(new Credit());
                });
    }
}
