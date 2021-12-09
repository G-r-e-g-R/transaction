package com.nttdata.transaction.infraestructure.repository;

import com.nttdata.transaction.domain.AccountAffiliation;
import reactor.core.publisher.Flux;
/**
 * ICUSTOMACCOUNTAFFILIATIONCRUDREPOSITORY.
 * Define las operaciones personalizadas de la Afiliación
 * de Cuentas Bancarias de un cliente el cual extiende del Reactive CRUD.
 */
public interface ICustomAccountAffiliationCrudRepository {
    /**
     * Listado de Afiliaciones de cuentas por Cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<AccountAffiliation>
     */
    Flux<AccountAffiliation> findByIdCustomer(String idCustomer);
}
