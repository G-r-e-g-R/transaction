package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.AccountAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.AccountAffiliationDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ACCOUNTAFFILIATIONOPERATIONS: Define las operaciones (CRUD) de la afiliación
 *                               de Cuentas Bancarias de un cliente
 */
public interface AccountAffiliationOperations {
    public Mono<AccountAffiliation> create(AccountAffiliation accountAffiliation);
    public Mono<AccountAffiliation> update(String id, AccountAffiliation accountAffiliation);
    public Mono<AccountAffiliationDao> delete(String id);
    public Mono<AccountAffiliation> findById(String id);
    public Flux<AccountAffiliation> findAll();
}
