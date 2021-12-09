package com.nttdata.transaction.infraestructure.repository;

import com.nttdata.transaction.application.AccountAffiliationRepository;
import com.nttdata.transaction.domain.AccountAffiliation;
import com.nttdata.transaction.domain.bean.Account;
import com.nttdata.transaction.domain.bean.AccountType;
import com.nttdata.transaction.domain.bean.Customer;
import com.nttdata.transaction.domain.bean.CustomerType;
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
     * @param circuitBreakerFactory corto circuito.
     * @param iAccountAffiliationCrudRepository respositorio.
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
     * @param accountAffiliation afiliación de cuenta.
     * @return Mono<AccountAffiliation>
     */
    @Override
    public Mono<AccountAffiliation> create(
            final AccountAffiliation accountAffiliation) {
        log.info("[create] Inicio");
        Flux<Customer> newCustomer = getCustomerById(accountAffiliation.getIdCustomer());
        Flux<Account>  newAccount = getProductAccountById(accountAffiliation.getIdAccount());
        return newCustomer.flatMap(
                a -> {
                    if (a.getCustomerType().name().equals(CustomerType.EMPRESARIAL.name())){
                        return createEnterprise(accountAffiliation,newAccount);
                    }else if (a.getCustomerType().name().equals(CustomerType.PERSONAL.name())){
                        return createPersonal(accountAffiliation,newAccount);
                    }else {
                        return Mono.just(new AccountAffiliation());
                    }
                }
        ).next();

    }

    /**
     * Afiliación de un cliente empresarial.
     * @param accountAffiliation datos de afiliacion.
     * @param newAccount cuenta bancaria.
     * @return Mono<AccountAffiliation>
     */
    private Mono<AccountAffiliation> createEnterprise(
            final AccountAffiliation accountAffiliation,
            Flux<Account>  newAccount
            ){
        return newAccount.flatMap(
                a -> {
                    if (a.getAccountType().name()
                            .equals(AccountType.CUENTA_CORRIENTE.name())){
                        return repositoryCreate(accountAffiliation);
                    }else{
                        return Mono.just(new AccountAffiliation());
                    }
                }
        ).next();

    }
    /**
     * Afiliación de un cliente Personal.
     * @param accountAffiliation datos de afiliacion.
     * @param newAccount cuenta bancaria.
     * @return Mono<AccountAffiliation>
     */
    private Mono<AccountAffiliation> createPersonal(
            final AccountAffiliation accountAffiliation,
            final Flux<Account>  newAccount){
        return repository.findByIdCustomer(accountAffiliation.getIdCustomer())
                .filter(customer -> {
                    log.info("[createPersonal] Cliente Existe validamos el Producto...");
                    return getProductAccountById(customer.getIdAccount())
                            .blockFirst().getAccountType().name()
                            .equals(newAccount.blockFirst().getAccountType().name());

                })
                .map( __ -> new AccountAffiliation())
                .switchIfEmpty(Mono.defer(() -> repositoryCreate(accountAffiliation)))
                .next();
    }

    /**
     * Llamado al repositorio para la creación de la afiliación de cuenta bancaria.
     * @param accountAffiliation
     * @return
     */
    private Mono<AccountAffiliation> repositoryCreate(final AccountAffiliation accountAffiliation) {
        log.info("[repositoryCreate] Creando una a");
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
     * @param id codigo.
     * @param accountAffiliation afiliación de cuenta.
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
    public Mono<Void> delete(final String id) {
        return repository.findById(id)
                .flatMap(p -> repository.deleteById(p.getId()));
    }
    /**
     * Busca por Id los datos de la afiliacion de cuentas bancarias.
     * @param id codigo.
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
     * @param accountAffiliation afiliación de cuenta.
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
     * @param accountAffiliationDao afiliación de cuenta.
     * @return AccountAffiliation
     */
    private AccountAffiliation mapAccountAffiliationDaoToAccountAffiliation(
            final AccountAffiliationDao accountAffiliationDao) {
        log.info("[mapAccountAffiliationDaoToAccountAffiliation] Inicio");
        AccountAffiliation accountAffiliation = new AccountAffiliation();
        BeanUtils.copyProperties(accountAffiliationDao, accountAffiliation);
        //Complementamos los datos faltantes
        Flux<Customer> customers = getCustomerById(accountAffiliationDao.getIdCustomer());
        accountAffiliation.setCustomer(customers.blockFirst());
        Flux<Account> accounts = getProductAccountById(accountAffiliationDao.getIdAccount());
        accountAffiliation.setAccount(accounts.blockFirst());
        log.info("[mapAccountAffiliationDaoToAccountAffiliation] Fin");
        return accountAffiliation;
    }
    /**
     * Asigna el Id de AccountAffiliationDao a AccountAffiliation.
     * @param accountAffiliationDao afiliación de cuenta Dao.
     * @param accountAffiliation afiliación de cuenta.
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
     * @param idCustomer Codigo del cliente.
     * @return Flux<Customer>
     */
    public Flux<Customer> getCustomerById(
            final String idCustomer) {
        log.info("[getCustomerById] Inicio");
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
                                .bodyToFlux(Customer.class),
                throwable -> {
                    log.info("throwable => {}", throwable.toString());
                    log.info("[getCustomerById] Error en la llamada:"
                            + UriService.CUSTOMER_GET_BY_ID
                            + idCustomer);
                    return Flux.just(new Customer());
                });
    }
    /**
     * Obtenemos los datos del producto: Cuenta Bancaria.
     * @param idAccount codigo de la cuenta bancaria
     * @return Flux<Account>
     */
    public Flux<Account> getProductAccountById(
            final String idAccount) {
        log.info("[getProductAccountById] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.PRODUCT_ACCOUNT_GET_BY_ID,
                                        idAccount
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(Account.class),
                throwable -> {
                    log.info("throwable => {}", throwable.toString());
                    log.info("[getProductAccountById] Error en la llamada:"
                            + UriService.PRODUCT_ACCOUNT_GET_BY_ID
                            + idAccount);
                    return Flux.just(new Account());
                });
    }
}
