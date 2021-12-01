package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.AccountAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.AccountAffiliationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * CUSTOMEROPERATIONSIMPL: Implementa las operaciones (CRUD) de la afiliación
 *                         de Cuentas Bancarias de un cliente
 */
@Service
public class AccountAffiliationOperationsImpl implements  AccountAffiliationOperations{
    @Autowired
    AccountAffiliationRepository repository;

    @Override
    public Mono<AccountAffiliation> create(AccountAffiliation accountAffiliation) {
        return repository.create(accountAffiliation);
    }

    @Override
    public Mono<AccountAffiliation> update(String id, AccountAffiliation accountAffiliation) {
        return repository.update(id, accountAffiliation);
    }

    @Override
    public Mono<AccountAffiliationDao> delete(String id) {
        return repository.delete(id);
    }

    @Override
    public Mono<AccountAffiliation> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<AccountAffiliation> findAll() {
        return repository.findAll();
    }
}
