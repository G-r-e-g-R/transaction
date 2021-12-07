package com.nttdata.transaction.infraestructure.repository;

import com.nttdata.transaction.application.AccountAffiliationRepository;
import com.nttdata.transaction.domain.AccountAffiliation;
import com.nttdata.transaction.domain.bean.Account;
import com.nttdata.transaction.domain.bean.Customer;
import com.nttdata.transaction.infraestructure.client.UriService;
import com.nttdata.transaction.infraestructure.model.dao.AccountAffiliationDao;
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
 * ACCOUNTAFFILIATIONCRUDREPOSITORY.
 * Implementa las operaciones (CRUD) de la afiliación de cuentas bancarias
 */
@Component
@Slf4j
public class AccountAffiliationCrudRepository
        implements AccountAffiliationRepository {
    /**
     * Operaciones de afiliación de cuentas bancarias.
     */
    private final IAccountAffiliationCrudRepository repository;
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
     * @param iAccountAffiliationCrudRepository
     */
    public AccountAffiliationCrudRepository(
            final ReactiveResilience4JCircuitBreakerFactory
                    circuitBreakerFactory,
            final IAccountAffiliationCrudRepository
                    iAccountAffiliationCrudRepository) {
        this.repository = iAccountAffiliationCrudRepository;
        this.webClient = WebClient.builder()
                .baseUrl(UriService.BASE_URI)
                .build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("account");
    }
    /**
     * Regitra las afiliaciones de cuentas bancarias de un cliente.
     * @param accountAffiliation
     * @return Mono<AccountAffiliation>
     */
    @Override
    public Mono<AccountAffiliation> create(
            final AccountAffiliation accountAffiliation) {
        return repository
                .save(
                        mapAccountAffiliationToAccountAffiliationDao(
                                accountAffiliation
                        )
                )
                .map(this::mapAccountAffiliationDaoToAccountAffiliation);
    }
    /**
     * Actualiza las afiliaciones de cuentas bancarias de un cliente.
     * @param id
     * @param accountAffiliation
     * @return Mono<AccountAffiliation>
     */
    @Override
    public Mono<AccountAffiliation> update(
            final String id,
            final AccountAffiliation accountAffiliation) {
        return repository
                .findById(id)
                .flatMap(p ->
                        create(
                                mapAccountAffiliationDaoToAccountAffiliation(
                                        p, accountAffiliation)
                        )
                );
    }
    /**
     * Elimina los datos de la afiliacion de cuentas bancarias de un cliente.
     * @param id
     * @return Mono<AccountAffiliationDao>
     */
    @Override
    public Mono<AccountAffiliationDao> delete(final String id) {
        return repository.findById(id)
                .flatMap(p -> repository.deleteById(p.getId()).thenReturn(p));
    }
    /**
     * Busca por Id los datos de la afiliacion de cuentas bancarias.
     * @param id
     * @return Mono<AccountAffiliation>
     */
    @Override
    public Mono<AccountAffiliation> findById(final String id) {
        return repository.findById((id))
                .map(this::mapAccountAffiliationDaoToAccountAffiliation);
    }
    /**
     * Busca todas las afiliaciones de cuentas bancarias de un cliente.
     * @return Flux<AccountAffiliation>
     */
    @Override
    public Flux<AccountAffiliation> findAll() {
        return repository.findAll()
                .map(this::mapAccountAffiliationDaoToAccountAffiliation);
    }
    /**
     * Crea AccountAffiliation y asigna los datos de AccountAffiliationDao.
     * @param accountAffiliation
     * @return AccountAffiliationDao
     */
    private AccountAffiliationDao mapAccountAffiliationToAccountAffiliationDao(
            final AccountAffiliation accountAffiliation) {
        AccountAffiliationDao accountAffiliationDao
                = new AccountAffiliationDao();
        BeanUtils.copyProperties(accountAffiliation, accountAffiliationDao);
        return accountAffiliationDao;
    }
    /**
     * Crea AccountAffiliation y asigna los datos de AccountAffiliationDao.
     * @param accountAffiliationDao
     * @return AccountAffiliation
     */
    private AccountAffiliation mapAccountAffiliationDaoToAccountAffiliation(
            final AccountAffiliationDao accountAffiliationDao) {
        log.info("[mapAccountAffiliationDaoToAccountAffiliation] Inicio");
        AccountAffiliation accountAffiliation = new AccountAffiliation();
        BeanUtils.copyProperties(accountAffiliationDao, accountAffiliation);
        //Complementamos los datos faltantes
        Flux<Customer> customers = getCustomerById(accountAffiliationDao);
        accountAffiliation.setCustomer(customers.blockFirst());
        Flux<Account> accounts = getProductAccountById(accountAffiliationDao);
        accountAffiliation.setAccount(accounts.blockFirst());
        log.info("[mapAccountAffiliationDaoToAccountAffiliation] Fin");
        return accountAffiliation;
    }
    /**
     * Asigna el Id de AccountAffiliationDao a AccountAffiliation.
     * @param accountAffiliationDao
     * @param accountAffiliation
     * @return AccountAffiliation
     */
    private AccountAffiliation mapAccountAffiliationDaoToAccountAffiliation(
            final AccountAffiliationDao accountAffiliationDao,
            final AccountAffiliation accountAffiliation) {
        accountAffiliation.setId(accountAffiliationDao.getId());
        return accountAffiliation;
    }
    /**
     * Obtenemos los datos del cliente.
     * @param accountAffiliationDao
     * @return Flux<Customer>
     */
    public Flux<Customer> getCustomerById(
            final AccountAffiliationDao accountAffiliationDao) {
        log.info("[getCustomerById] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.CUSTOMER_GET_BY_ID,
                                        accountAffiliationDao.getIdCustomer()
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(Customer.class),
                throwable -> {
                    log.info("throwable => {}", throwable.toString());
                    log.info("[getCustomerById] Error en la llamada:"
                            + UriService.CUSTOMER_GET_BY_ID
                            + accountAffiliationDao.getIdCustomer());
                    return Flux.just(new Customer());
                });
    }
    /**
     * Obtenemos los datos del producto: Cuenta Bancaria.
     * @param accountAffiliationDao
     * @return Flux<Account>
     */
    public Flux<Account> getProductAccountById(
            final AccountAffiliationDao accountAffiliationDao) {
        log.info("[getProductAccountById] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.PRODUCT_ACCOUNT_GET_BY_ID,
                                        accountAffiliationDao.getIdAccount()
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(Account.class),
                throwable -> {
                    log.info("throwable => {}", throwable.toString());
                    log.info("[getProductAccountById] Error en la llamada:"
                            + UriService.PRODUCT_ACCOUNT_GET_BY_ID
                            + accountAffiliationDao.getIdAccount());
                    return Flux.just(new Account());
                });
    }
}
