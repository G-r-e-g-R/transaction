package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.CreditAffiliation;
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
     * @param creditAffiliation afiliación de credito.
     * @return Mono<CreditAffiliation>
     */
    Mono<CreditAffiliation>
    create(CreditAffiliation creditAffiliation);

    /**
     * Actualización de un credito para un cliente.
     * @param id codigo.
     * @param creditAffiliation afiliación de credito.
     * @return Mono<CreditAffiliation>
     */
    Mono<CreditAffiliation>
    update(String id, CreditAffiliation creditAffiliation);

    /**
     * Eliminación de un credito para un cliente.
     * @param id codigo.
     * @return Mono<CreditAffiliationDao>
     */
    Mono<Void>
    delete(String id);

    /**
     * Busqueda de un credito de un cliente por Id.
     * @param id codigo.
     * @return Mono<CreditAffiliation>
     */
    Mono<CreditAffiliation>
    findById(String id);

    /**
     * Busqueda de todas las creditos de los clientes.
     * @return Flux<CreditAffiliation>
     */
    Flux<CreditAffiliation>
    findAll();
}
