package com.nttdata.affiliation.infraestructure.repository;

import com.nttdata.affiliation.infraestructure.model.dao.CreditMovementDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
/**
 * ICREDITMOVEMENTCRUDREPOSITORY.
 * Define las operaciones (CRUD) de los movimientos
 * de Credito de un cliente el cual extiende del Reactive CRUD.
 */
public interface ICreditMovementCrudRepository
        extends ReactiveCrudRepository<CreditMovementDao, String>,
        ICustomCreditAffiliationCrudRepository {
}
