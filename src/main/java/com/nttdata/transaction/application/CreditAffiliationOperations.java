package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.CreditAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.CreditAffiliationDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CREDITAFFILIATIONOPERATIONS: Define las operaciones (CRUD) de la afiliación
 *                               de Creditos de un cliente
 */
public interface CreditAffiliationOperations {
    public Mono<CreditAffiliation> create(CreditAffiliation creditAffiliation);
    public Mono<CreditAffiliation> update(String id, CreditAffiliation creditAffiliation);
    public Mono<CreditAffiliationDao> delete(String id);
    public Mono<CreditAffiliation> findById(String id);
    public Flux<CreditAffiliation> findAll();
}
