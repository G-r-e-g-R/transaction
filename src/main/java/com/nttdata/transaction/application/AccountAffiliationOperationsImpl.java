package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.AccountAffiliation;
import com.nttdata.transaction.domain.bean.Account;
import com.nttdata.transaction.domain.bean.AccountType;
import com.nttdata.transaction.domain.bean.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * CUSTOMEROPERATIONSIMPL.
 * Implementa las operaciones (CRUD) de la afiliación
 *                         de Cuentas Bancarias de un cliente
 */
@Service
public class AccountAffiliationOperationsImpl
        implements  AccountAffiliationOperations {

    /**
     * Repositorio de los registros (Afiliación) de cuentas bancarias.
     */
    private final AccountAffiliationRepository repository;

    /**
     * Constructor.
     * @param accountAffiliationRepository repositorio.
     */
    public AccountAffiliationOperationsImpl(
            final AccountAffiliationRepository accountAffiliationRepository) {
        this.repository = accountAffiliationRepository;
    }

    /**
     * Registro (Afiliación) de un cliente con cuenta bancaria.
     * @param accountAffiliation afiliación de cuenta.
     * @return Mono<AccountAffiliation>
     */
    @Override
    public Mono<AccountAffiliation> create(
            final AccountAffiliation accountAffiliation) {
        return repository.create(accountAffiliation);

    }

    /**
     * Actualización de un cliente con cuenta bancaria.
     * @param id codigo.
     * @param accountAffiliation afiliación de cuenta.
     * @return Mono<AccountAffiliation>
     */
    @Override
    public Mono<AccountAffiliation> update(
            final String id, final AccountAffiliation accountAffiliation) {
        return repository.update(id, accountAffiliation);
    }

    /**
     * Eliminación de un cliente con cuenta bancaria.
     * @param id codigo.
     * @return Mono<AccountAffiliationDao>
     */
    @Override
    public Mono<Void> delete(final String id) {
        return repository.delete(id);
    }

    /**
     * Busqueda de un cliente con cuenta bancaria por Id.
     * @param id codigo.
     * @return Mono<AccountAffiliation>
     */
    @Override
    public Mono<AccountAffiliation> findById(final String id) {
        return repository.findById(id);
    }

    /**
     * Busqueda de todos los clientes con cuenta bancaria.
     * @return Flux<AccountAffiliation>
     */
    @Override
    public Flux<AccountAffiliation> findAll() {
        return repository.findAll();
    }

}
