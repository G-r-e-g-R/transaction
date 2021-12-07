package com.nttdata.transaction.infraestructure.repository;

import com.nttdata.transaction.infraestructure.model.dao.CreditAffiliationDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * ICREDITAFFILIATIONCRUDREPOSITORY.
 * Define las operaciones (CRUD) de la Afiliación
 * de Credito de un cliente el cual extiende del Reactive CRUD.
 */
public interface ICreditAffiliationCrudRepository
        extends ReactiveCrudRepository<CreditAffiliationDao, String> {
}
