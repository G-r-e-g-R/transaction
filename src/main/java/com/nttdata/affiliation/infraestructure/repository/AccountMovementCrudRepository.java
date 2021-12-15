package com.nttdata.affiliation.infraestructure.repository;

import com.nttdata.affiliation.application.AccountMovementRepository;
import com.nttdata.affiliation.domain.AccountMovement;
import com.nttdata.affiliation.domain.bean.Account;
import com.nttdata.affiliation.domain.bean.AccountAffiliation;
import com.nttdata.affiliation.domain.bean.Customer;
import com.nttdata.affiliation.infraestructure.client.UriService;
import com.nttdata.affiliation.infraestructure.model.dao.AccountMovementDao;
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
 * ACCOUNTMOVEMENTCRUDREPOSITORY.
 * Implementa las operaciones (CRUD) de los movimientos de cuentas bancarias.
 */
@Component
@Slf4j
public class AccountMovementCrudRepository
        implements AccountMovementRepository {

    /**
     * Operaciones de movimientos de cuentas bancarias.
     */
    private final IAccountMovementCrudRepository repository;
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
     * @param iAccountMovementCrudRepository repositorio.
     */
    public
    AccountMovementCrudRepository(
            final ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory,
            final IAccountMovementCrudRepository iAccountMovementCrudRepository) {
        this.repository = iAccountMovementCrudRepository;
        this.webClient = WebClient.builder()
                .baseUrl(UriService.BASE_URI)
                .build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("accountMovement");
    }

    /**
     * Regitra los movimientos de cuentas bancarias de un cliente.
     * @param accountMovement movimiento.
     * @return
     */
    @Override
    public
    Mono<AccountMovement>
    create(AccountMovement accountMovement) {
        log.info("[create] Inicio");
        return repository
                .save(
                        mapAccountMovementToAccountMovementDao(
                                accountMovement
                        )
                )
                .map(this::mapAccountMovementDaoToAccountMovement);
    }

    /**
     * Actualiza el movimiento de cuenta bancaria de un cliente.
     * @param id codigo.
     * @param accountMovement movimiento de cuenta.
     * @return Mono<AccountMovement>
     */
    @Override
    public
    Mono<AccountMovement>
    update(String id, AccountMovement accountMovement) {
        return repository
                .findById(id)
                .flatMap(p ->
                        create(
                                mapAccountMovementDaoToAccountMovement(
                                        p, accountMovement)
                        )
                );
    }

    /**
     * Elimina los datos del movmiento de cuentas bancarias de un cliente.
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
     * Busca por Id los datos del movimiento de cuentas bancarias.
     * @param id codigo.
     * @return Mono<AccountMovement>
     */
    @Override
    public
    Mono<AccountMovement>
    findById(String id) {
        return repository.findById((id))
                .map(this::mapAccountMovementDaoToAccountMovement);
    }

    /**
     * Busca todas los movimientos de cuentas bancarias de un cliente.
     * @return
     */
    @Override
    public
    Flux<AccountMovement>
    findAll() {
        return repository.findAll()
                .map(this::mapAccountMovementDaoToAccountMovement);
    }

    /**
     * Actualiza los datos de la afiliacion.
     * @param idAffiliation codigo de afiliacion.
     * @param accountAffiliation datos de la afiliacion.
     * @return Mono<AccountAffiliation>
     */
    @Override
    public
    Mono<AccountAffiliation>
    putAccountAffiliation(String idAffiliation, AccountAffiliation accountAffiliation) {
        log.info("[putAccountAffiliation] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .put()
                                .uri(
                                        UriService.AFFILIATION_ACCOUNT_PUT,
                                        idAffiliation
                                )
                                .bodyValue(accountAffiliation)
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(AccountAffiliation.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[putAccountAffiliation] Error en la llamada:"
                                    + UriService.AFFILIATION_ACCOUNT_PUT
                                    + idAffiliation +":" + accountAffiliation);
                            return Mono.just(new AccountAffiliation());
                        });
    }

    /**
     * Obtenemos los datos de la afiliación de cuenta bancaria.
     * @param idCustomer Codigo de la afilicacion.
     * @return Mono<AccountAffiliation>
     */
    @Override
    public
    Mono<AccountAffiliation>
    getAccountAffiliationById(String idCustomer) {
        log.info("[getAccountAffiliationById] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.AFFILIATION_ACCOUNT_GET_BY_ID,
                                        idCustomer
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(AccountAffiliation.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getAccountAffiliationById] Error en la llamada:"
                                    + UriService.AFFILIATION_ACCOUNT_GET_BY_ID
                                    + idCustomer);
                            return Mono.just(new AccountAffiliation());
                        });
    }

    /**
     * Obtenemos los datos del cliente.
     * @param idCustomer Codigo del cliente.
     * @return
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
     * Obtenemos los datos del producto: Cuenta Bancaria.
     * @param idAccount codigo de la cuenta bancaria
     * @return Mono<Account>
     */
    @Override
    public
    Mono<Account>
    getProductAccountById(String idAccount) {
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
                                .bodyToMono(Account.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getProductAccountById] Error en la llamada:"
                                    + UriService.PRODUCT_ACCOUNT_GET_BY_ID
                                    + idAccount);
                            return Mono.just(new Account());
                        });
    }

    /**
     * Obtiene los movimientos de una afiliacion.
     * @param idAffiliation Codigo de afiliacion.
     * @return Flux<AccountMovement>
     */
    @Override
    public Flux<AccountMovement> findByIdAffiliation(String idAffiliation) {
        return repository.findByIdAccountAffiliation(idAffiliation);
    }

    /**
     * Crea AccountMovement y asigna los datos de AccountAMovementDao.
     * @param accountMovement Movimiento de cuenta.
     * @return AccountMovementDao
     */
    private AccountMovementDao
    mapAccountMovementToAccountMovementDao(
            final AccountMovement accountMovement) {

        AccountMovementDao accountMovementDao
                = new AccountMovementDao();
        BeanUtils.copyProperties(accountMovement, accountMovementDao);
        return accountMovementDao;
    }
    /**
     * Crea AccountMovement y asigna los datos de AccountMovementDao.
     * @param accountMovementDao movimiento de cuenta.
     * @return AccountMovement
     */
    private
    AccountMovement
    mapAccountMovementDaoToAccountMovement(
            final AccountMovementDao accountMovementDao) {

        log.info("[mapAccountMovementDaoToAccountMovement] Inicio");
        AccountMovement accountMovement = new AccountMovement();
        BeanUtils.copyProperties(accountMovementDao, accountMovement);
        //Complementamos los datos faltantes
        Mono<AccountAffiliation> accountAffiliation = getAccountAffiliationById(accountMovementDao.getIdAccountAffiliation());
        Mono<Customer> customers = getCustomerById(accountAffiliation.block().getIdCustomer());
        accountMovement.setCustomer(customers.block());
        Mono<Account> accounts = getProductAccountById(accountAffiliation.block().getIdAccount());
        accountMovement.setAccount(accounts.block());

        log.info("[mapAccountMovementDaoToAccountMovement] Fin");
        return accountMovement;
    }
    /**
     * Asigna el Id de AccountMovementDao a AccountMovement.
     * @param accountMovementDao movimiento de cuenta Dao.
     * @param accountMovement movimiento de cuenta.
     * @return AccountMovement
     */
    private
    AccountMovement
    mapAccountMovementDaoToAccountMovement(
            final AccountMovementDao accountMovementDao,
            final AccountMovement accountMovement) {

        accountMovement.setId(accountMovementDao.getId());
        return accountMovement;
    }
}
