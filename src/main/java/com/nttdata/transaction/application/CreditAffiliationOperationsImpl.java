package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.CreditAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.CreditAffiliationDao;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * CREDITAFFILIATIONOPERATIONSIMPL: Implementa las operaciones (CRUD) de la afiliación
 *                         de Credito de un cliente
 */
public class CreditAffiliationOperationsImpl implements   CreditAffiliationOperations{
    @Autowired
    CreditAffiliationRepository creditAffiliationRepository;
    @Override
    public Mono<CreditAffiliation> create(CreditAffiliation creditAffiliation) {
        return creditAffiliationRepository.create(creditAffiliation);
    }

    @Override
    public Mono<CreditAffiliation> update(String id, CreditAffiliation creditAffiliation) {
        return creditAffiliationRepository.update(id, creditAffiliation);
    }

    @Override
    public Mono<CreditAffiliationDao> delete(String id) {
        return creditAffiliationRepository.delete(id);
    }

    @Override
    public Mono<CreditAffiliation> findById(String id) {
        return creditAffiliationRepository.findById(id);
    }

    @Override
    public Flux<CreditAffiliation> findAll() {
        return creditAffiliationRepository.findAll();
    }
}
