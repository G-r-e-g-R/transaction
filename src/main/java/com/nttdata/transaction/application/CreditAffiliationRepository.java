package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.CreditAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.CreditAffiliationDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * CREDITAFFILIATIONREPOSITORY.
 * Define las operaciones en la BD para
 *                               la afiliacion de un credito y un cliente
 */
public interface CreditAffiliationRepository {
    /**
     * Creación de credito para un cliente.
     * @param creditAffiliation
     * @return Mono<CreditAffiliation>
     */
    Mono<CreditAffiliation> create(CreditAffiliation creditAffiliation);

    /**
     * Actualización de un credito para un cliente.
     * @param id
     * @param creditAffiliation
     * @return Mono<CreditAffiliation>
     */
    Mono<CreditAffiliation> update(String id,
                                   CreditAffiliation creditAffiliation);

    /**
     * Eliminación de un credito para un cliente.
     * @param id
     * @return Mono<CreditAffiliationDao>
     */
    Mono<CreditAffiliationDao> delete(String id);

    /**
     * Busqueda de un credito de un cliente por Id.
     * @param id
     * @return Mono<CreditAffiliation>
     */
    Mono<CreditAffiliation> findById(String id);

    /**
     * Busqueda de todas las creditos de los clientes.
     * @return Flux<CreditAffiliation>
     */
    Flux<CreditAffiliation> findAll();
}
