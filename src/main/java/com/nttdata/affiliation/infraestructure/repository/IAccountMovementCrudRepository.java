package com.nttdata.affiliation.infraestructure.repository;

import com.nttdata.affiliation.infraestructure.model.dao.AccountMovementDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
/**
 * IACCOUNTMOVEMENTCRUDREPOSITORY.
 * Define las operaciones (CRUD) de los movimientos
 * de Cuentas Bancarias de un cliente el cual extiende del Reactive CRUD.
 * Ademas se extiende el repositorio con uno personalizado.
 */
public interface IAccountMovementCrudRepository
        extends ReactiveCrudRepository<AccountMovementDao, String>,
                ICustomAccountMovementCrudRepository{
}
