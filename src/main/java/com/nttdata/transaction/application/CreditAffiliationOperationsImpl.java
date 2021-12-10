package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.CreditAffiliation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * CREDITAFFILIATIONOPERATIONSIMPL.
 * Implementa las operaciones (CRUD) de la afiliación
 *                         de Credito de un cliente
 */
@Service
public class CreditAffiliationOperationsImpl
        implements   CreditAffiliationOperations {
    /**
     * Repositorio de los registros (Afiliación) de creditos.
     */
    private final CreditAffiliationRepository creditAffiliationRepository;

    /**
     * Constructor.
     * @param repository repositorio.
     */
    public
    CreditAffiliationOperationsImpl(
            final CreditAffiliationRepository repository) {
      this.creditAffiliationRepository = repository;
    }

    /**
     * Creación de credito para un cliente.
     * @param creditAffiliation afiliación de credito.
     * @return Mono<CreditAffiliation>
     */
    @Override
    public
    Mono<CreditAffiliation>
    create(final CreditAffiliation creditAffiliation) {
        return creditAffiliationRepository.create(creditAffiliation);
    }

    /**
     * Actualización de un credito para un cliente.
     * @param id codigo.
     * @param creditAffiliation afiliación de credito.
     * @return Mono<CreditAffiliation>
     */
    @Override
    public
    Mono<CreditAffiliation>
    update(final String id, final CreditAffiliation creditAffiliation) {
        return creditAffiliationRepository.update(id, creditAffiliation);
    }

    /**
     * Eliminación de un credito para un cliente.
     * @param id codigo.
     * @return Mono<CreditAffiliationDao>
     */
    @Override
    public
    Mono<Void>
    delete(final String id) {
        return creditAffiliationRepository.delete(id);
    }

    /**
     * Busqueda de un credito de un cliente por Id.
     * @param id codigo.
     * @return Mono<CreditAffiliation>
     */
    @Override
    public
    Mono<CreditAffiliation>
    findById(final String id) {
        return creditAffiliationRepository.findById(id);
    }

    /**
     * Busqueda de todas las creditos de los clientes.
     * @return Flux<CreditAffiliation>
     */
    @Override
    public
    Flux<CreditAffiliation>
    findAll() {
        return creditAffiliationRepository.findAll();
    }
}
