package com.nttdata.transaction.infraestructure.repository;

import com.nttdata.transaction.infraestructure.model.dao.AccountAffiliationDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
/**
 * IACCOUNTAFFILIATIONCRUDREPOSITORY.
 * Define las operaciones (CRUD) de la Afiliación
 * de Cuentas Bancarias de un cliente el cual extiende del Reactive CRUD.
 */
public interface IAccountAffiliationCrudRepository
        extends ReactiveCrudRepository<AccountAffiliationDao, String> {
}
