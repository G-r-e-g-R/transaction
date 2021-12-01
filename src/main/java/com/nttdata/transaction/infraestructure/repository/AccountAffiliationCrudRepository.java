package com.nttdata.transaction.infraestructure.repository;

import com.nttdata.transaction.application.AccountAffiliationRepository;
import com.nttdata.transaction.domain.AccountAffiliation;
import com.nttdata.transaction.domain.bean.Account;
import com.nttdata.transaction.domain.bean.Customer;
import com.nttdata.transaction.infraestructure.client.CustomerClient;
import com.nttdata.transaction.infraestructure.client.ProductAccountClient;
import com.nttdata.transaction.infraestructure.model.dao.AccountAffiliationDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ACCOUNTAFFILIATIONCRUDREPOSITORY: Implementa las operaciones (CRUD) de la afiliación de cuentas bancarias
 */
@Component
@Slf4j
public class AccountAffiliationCrudRepository implements AccountAffiliationRepository {
    @Autowired
    IAccountAffiliationCrudRepository repository;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductAccountClient productClient;
    /*
    create: Regitra las afiliaciones de cuentas bancarias de un cliente
     */
    @Override
    public Mono<AccountAffiliation> create(AccountAffiliation accountAffiliation) {
        return repository.save(mapAccountAffiliationToAccountAffiliationDao(accountAffiliation))
                .map(this::mapAccountAffiliationDaoToAccountAffiliation);
    }
    /*
    update: Actualiza las afiliaciones de cuentas bancarias de un cliente
     */
    @Override
    public Mono<AccountAffiliation> update(String id, AccountAffiliation accountAffiliation) {
        return repository.findById(id)
                .flatMap( p ->create(mapAccountAffiliationDaoToAccountAffiliation(p,accountAffiliation)));
    }
    /*
    delete: Elimina los datos de la afiliacion de cuentas bancarias de un cliente
     */
    @Override
    public Mono<AccountAffiliationDao> delete(String id) {
        return repository.findById(id)
                .flatMap(p -> repository.deleteById(p.getId()).thenReturn(p));
    }
    /*
    findById: Busca por el Id los datos de la afiliacion de cuentas bancarias de un cliente
     */
    @Override
    public Mono<AccountAffiliation> findById(String id) {
        return repository.findById( (id))
                .map( this::mapAccountAffiliationDaoToAccountAffiliation);
    }
    /*
    findAll: Busca  los datos de todas las afiliaciones de cuentas bancarias de un cliente
     */
    @Override
    public Flux<AccountAffiliation> findAll() {
        return repository.findAll()
                .map(this::mapAccountAffiliationDaoToAccountAffiliation);
    }
    /*
    mapAccountAffiliationToAccountAffiliationDao: Crea un clase AccountAffiliation y
                                                  asigna los datos de AccountAffiliationDao
     */
    private AccountAffiliationDao mapAccountAffiliationToAccountAffiliationDao(AccountAffiliation accountAffiliation){
        AccountAffiliationDao accountAffiliationDao = new AccountAffiliationDao();
        accountAffiliationDao.setAccount(accountAffiliation.getAccount());
        accountAffiliationDao.setBalance(accountAffiliation.getBalance());
        accountAffiliationDao.setBaseAmount(accountAffiliation.getBaseAmount());
        accountAffiliationDao.setCustomer(accountAffiliation.getCustomer());
        accountAffiliationDao.setId(accountAffiliation.getId());
        accountAffiliationDao.setStatus(accountAffiliation.getStatus());
        accountAffiliationDao.setNumber(accountAffiliation.getNumber());
        accountAffiliationDao.setMovementDay(accountAffiliation.getMovementDay());
        accountAffiliationDao.setNumberOfHolder(accountAffiliation.getNumberOfHolder());
        accountAffiliationDao.setNumberOfSigner(accountAffiliation.getNumberOfSigner());
        accountAffiliationDao.setIdAccount(accountAffiliation.getIdAccount());
        accountAffiliationDao.setIdCustomer(accountAffiliation.getIdCustomer());
        return accountAffiliationDao;
    }
    /*
    mapAccountAffiliationDaoToAccountAffiliation: Crea un clase AccountAffiliation y
                                                  asigna los datos de AccountAffiliationDao
     */
    private AccountAffiliation mapAccountAffiliationDaoToAccountAffiliation(AccountAffiliationDao accountAffiliationDao){
        log.info("[mapAccountAffiliationDaoToAccountAffiliation] Inicio");
        log.info("[--] IdCustomer:"+accountAffiliationDao.getIdCustomer());
        log.info("[--] IdAccount:"+accountAffiliationDao.getIdAccount());
        AccountAffiliation accountAffiliation = new AccountAffiliation();
        Mono<Customer> s = customerClient.getById(accountAffiliationDao.getIdCustomer());
        log.info("[--] s:"+s.block());
        accountAffiliation.setCustomer(s.block());
        Mono<Account> c = productClient.getById(accountAffiliationDao.getIdAccount());
        log.info("[--] c:"+c);
        accountAffiliation.setAccount(c.block());
        accountAffiliation.setAccount(accountAffiliationDao.getAccount());
        accountAffiliation.setBalance(accountAffiliationDao.getBalance());
        accountAffiliation.setBaseAmount(accountAffiliationDao.getBaseAmount());
        accountAffiliation.setId(accountAffiliationDao.getId());
        accountAffiliation.setStatus(accountAffiliationDao.getStatus());
        accountAffiliation.setNumber(accountAffiliationDao.getNumber());
        accountAffiliation.setMovementDay(accountAffiliationDao.getMovementDay());
        accountAffiliation.setNumberOfHolder(accountAffiliationDao.getNumberOfHolder());
        accountAffiliation.setNumberOfSigner(accountAffiliationDao.getNumberOfSigner());
        accountAffiliation.setIdAccount(accountAffiliationDao.getIdAccount());
        accountAffiliation.setIdCustomer(accountAffiliationDao.getIdCustomer());
        log.info("[mapAccountAffiliationDaoToAccountAffiliation] Fin");
        return accountAffiliation;
    }

    /*
   mapAccountAffiliationDaoToAccountAffiliation: Asigna el Id de AccountAffiliationDao a AccountAffiliation
   */
    private AccountAffiliation mapAccountAffiliationDaoToAccountAffiliation (AccountAffiliationDao accountAffiliationDao,  AccountAffiliation accountAffiliation){
        accountAffiliation.setId(accountAffiliationDao.getId());
        return accountAffiliation;
    }
}
