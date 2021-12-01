package com.nttdata.transaction.infraestructure.repository;

import com.nttdata.transaction.application.CreditAffiliationRepository;
import com.nttdata.transaction.domain.CreditAffiliation;
import com.nttdata.transaction.domain.bean.Credit;
import com.nttdata.transaction.domain.bean.Customer;
import com.nttdata.transaction.infraestructure.client.CustomerClient;
import com.nttdata.transaction.infraestructure.client.ProductCreditClient;
import com.nttdata.transaction.infraestructure.model.dao.CreditAffiliationDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CREDITAFFILIATIONCRUDREPOSITORY: Implementa las operaciones (CRUD) de la afiliación de Credito
 */
@Component
@Slf4j
public class CreditAffiliationCrudRepository implements CreditAffiliationRepository {
    @Autowired
    ICreditAffiliationCrudRepository repository;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductCreditClient productCreditClient;
    /*
    create: Regitra las afiliaciones de credito de un cliente
     */
    @Override
    public Mono<CreditAffiliation> create(CreditAffiliation creditAffiliation) {
        return repository.save(mapCreditAffiliationToCreditAffiliationDao(creditAffiliation))
                .map(this::mapCreditAffiliationDaoToCreditAffiliation);
    }
    /*
    update: Actualiza las afiliaciones de credito de un cliente
     */
    @Override
    public Mono<CreditAffiliation> update(String id, CreditAffiliation creditAffiliation) {
        return repository.findById(id)
                .flatMap( p ->create(mapCreditAffiliationDaoToCreditAffiliation(p,creditAffiliation)));
    }
    /*
    delete: Elimina los datos de la afiliacion de Credito de un cliente
     */
    @Override
    public Mono<CreditAffiliationDao> delete(String id) {
        return repository.findById(id)
                .flatMap(p -> repository.deleteById(p.getId()).thenReturn(p));
    }
    /*
    findById: Busca por el Id los datos de la afiliacion de credito de un cliente
     */
    @Override
    public Mono<CreditAffiliation> findById(String id) {
        return repository.findById( (id))
                .map( this::mapCreditAffiliationDaoToCreditAffiliation);
    }
    /*
    findAll: Busca  los datos de todas las afiliaciones de credito de un cliente
     */
    @Override
    public Flux<CreditAffiliation> findAll() {
        return repository.findAll()
                .map(this::mapCreditAffiliationDaoToCreditAffiliation);
    }
    /*
    mapCreditAffiliationToCreditAffiliationDao: Crea un clase CreditAffiliation y
                                                  asigna los datos de CreditAffiliationDao
     */
    private CreditAffiliationDao mapCreditAffiliationToCreditAffiliationDao(CreditAffiliation creditAffiliation){
        CreditAffiliationDao CreditAffiliationDao = new CreditAffiliationDao();
        CreditAffiliationDao.setCredit(creditAffiliation.getCredit());
        CreditAffiliationDao.setBalance(creditAffiliation.getBalance());
        CreditAffiliationDao.setBaseAmount(creditAffiliation.getBaseAmount());
        CreditAffiliationDao.setCustomer(creditAffiliation.getCustomer());
        CreditAffiliationDao.setId(creditAffiliation.getId());
        CreditAffiliationDao.setCreditLimit(creditAffiliation.getCreditLimit());
        CreditAffiliationDao.setIdCredit(creditAffiliation.getIdCredit());
        CreditAffiliationDao.setIdCustomer(creditAffiliation.getIdCustomer());
        return CreditAffiliationDao;
    }
    /*
    mapCreditAffiliationDaoToCreditAffiliation: Crea un clase CreditAffiliation y
                                                  asigna los datos de CreditAffiliationDao
     */
    private CreditAffiliation mapCreditAffiliationDaoToCreditAffiliation(CreditAffiliationDao CreditAffiliationDao){
        log.info("[mapCreditAffiliationDaoToCreditAffiliation] Inicio");
        log.info("[--] IdCustomer:"+CreditAffiliationDao.getIdCustomer());
        log.info("[--] IdCredit:"+CreditAffiliationDao.getIdCredit());
        CreditAffiliation CreditAffiliation = new CreditAffiliation();
        Mono<Customer> s = customerClient.getById(CreditAffiliationDao.getIdCustomer());
        log.info("[--] s:"+s.block());
        CreditAffiliation.setCustomer(s.block());
        Mono<Credit> c = productCreditClient.getById(CreditAffiliationDao.getIdCredit());
        log.info("[--] c:"+c);
        CreditAffiliation.setCredit(c.block());
        CreditAffiliation.setCredit(CreditAffiliationDao.getCredit());
        CreditAffiliation.setBalance(CreditAffiliationDao.getBalance());
        CreditAffiliation.setBaseAmount(CreditAffiliationDao.getBaseAmount());
        CreditAffiliation.setId(CreditAffiliationDao.getId());
        CreditAffiliation.setIdCredit(CreditAffiliationDao.getIdCredit());
        CreditAffiliation.setIdCustomer(CreditAffiliationDao.getIdCustomer());
        CreditAffiliation.setCreditLimit(CreditAffiliationDao.getCreditLimit());
        log.info("[mapCreditAffiliationDaoToCreditAffiliation] Fin");
        return CreditAffiliation;
    }

    /*
   mapCreditAffiliationDaoToCreditAffiliation: Asigna el Id de CreditAffiliationDao a CreditAffiliation
   */
    private CreditAffiliation mapCreditAffiliationDaoToCreditAffiliation (CreditAffiliationDao CreditAffiliationDao,  CreditAffiliation CreditAffiliation){
        CreditAffiliation.setId(CreditAffiliationDao.getId());
        return CreditAffiliation;
    }
}
