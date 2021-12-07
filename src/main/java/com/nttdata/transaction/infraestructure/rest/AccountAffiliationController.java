package com.nttdata.transaction.infraestructure.rest;

import com.nttdata.transaction.application.AccountAffiliationOperations;
import com.nttdata.transaction.domain.AccountAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.AccountAffiliationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ACCOUNTAFFILIATIONCONTROLLER.
 * Realiza las afiliaciones de cuentas bancarias con clientes
 */
@Slf4j
@RestController
@RequestMapping("/transactions/accountAffiliation")
@RequiredArgsConstructor
public class AccountAffiliationController {
    /**
     * Operaciones de Afiliación de cuenta bancaria.
     */
    private final AccountAffiliationOperations accountAffiliationOperations;

    /**
     * Busca todas las afiliaciones de cuentas bancarias de un cliente.
     * @return Flux<AccountAffiliation>
     */
    @GetMapping
    public Flux<AccountAffiliation> getAll() {
        return accountAffiliationOperations.findAll();
    }

    /**
     * Busca por Id los datos de la afiliacion de cuentas bancarias.
     * @param id
     * @return Mono<AccountAffiliation>
     */
    @GetMapping("/{id}")
    public Mono<AccountAffiliation> getById(@PathVariable final String id) {
        return accountAffiliationOperations.findById(id);
    }

    /**
     * Regitra las afiliaciones de cuentas bancarias de un cliente.
     * @param accountAffiliation
     * @return Mono<AccountAffiliation>
     */
    @PostMapping
    public Mono<AccountAffiliation> post(
            @RequestBody final AccountAffiliation accountAffiliation) {
        return accountAffiliationOperations.create(accountAffiliation);
    }

    /**
     * Actualiza las afiliaciones de cuentas bancarias de un cliente.
     * @param id
     * @param accountAffiliation
     * @return Mono<AccountAffiliation>
     */
    @PutMapping("/{id}")
    public Mono<AccountAffiliation> put(
            @PathVariable final String id,
            @RequestBody final AccountAffiliation accountAffiliation) {
        return accountAffiliationOperations.update(id, accountAffiliation);
    }

    /**
     * Elimina los datos de la afiliacion de cuentas bancarias de un cliente.
     * @param id
     * @return Mono<AccountAffiliationDao>
     */
    @DeleteMapping("/{id}")
    public  Mono<AccountAffiliationDao> delete(@PathVariable final String id) {
        return accountAffiliationOperations.delete(id);
    }
}
