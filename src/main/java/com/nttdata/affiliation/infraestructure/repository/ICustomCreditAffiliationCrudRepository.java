package com.nttdata.affiliation.infraestructure.repository;

import com.nttdata.affiliation.domain.CreditMovement;
import com.nttdata.affiliation.domain.bean.CreditAffiliation;
import reactor.core.publisher.Flux;

/**
 * ICUSTOMCREDITAFFILIATIONCRUDREPOSITORY.
 * Define las operaciones personalizadas de la Afiliación
 * de Credit de un cliente el cual extiende del Reactive CRUD.
 */
public interface ICustomCreditAffiliationCrudRepository {
    /**
     * Listado de Movimientos por codigo de Afiliacion.
     * @param idAffiliation Codigo del afiliacion.
     * @return Flux<CreditMovement>
     */
    Flux<CreditMovement> findByIdAffiliation(String idAffiliation);
}
