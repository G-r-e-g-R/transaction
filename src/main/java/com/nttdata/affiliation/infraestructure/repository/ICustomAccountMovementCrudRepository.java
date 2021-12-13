package com.nttdata.affiliation.infraestructure.repository;

import com.nttdata.affiliation.domain.AccountMovement;
import com.nttdata.affiliation.domain.bean.AccountAffiliation;
import reactor.core.publisher.Flux;
/**
 * ICUSTOMACCOUNTMOVEMENTCRUDREPOSITORY.
 * Define las operaciones personalizadas de los movimientos
 * de Cuentas Bancarias de un cliente el cual extiende del Reactive CRUD.
 */
public interface ICustomAccountMovementCrudRepository {
    /**
     * Listado de movmientos de cuentas por Afiliacion.
     * @param idAffiliation Codigo de afiliacion.
     * @return Flux<AccountAffiliation>
     */
    Flux<AccountMovement> findByIdAffiliation(String idAffiliation);
}
