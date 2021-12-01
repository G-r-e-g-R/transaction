package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.CreditAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.CreditAffiliationDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * CREDITAFFILIATIONREPOSITORY: Define las operaciones en la BD para
 *                               la afiliacion de un credito y un cliente
 */
public interface CreditAffiliationRepository {
    public Mono<CreditAffiliation> create(CreditAffiliation creditAffiliation);
    public Mono<CreditAffiliation> update(String id, CreditAffiliation creditAffiliation);
    public Mono<CreditAffiliationDao> delete(String id);
    public Mono<CreditAffiliation> findById(String id);
    public Flux<CreditAffiliation> findAll();
}
