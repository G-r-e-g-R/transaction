package com.nttdata.transaction.infraestructure.rest;

import com.nttdata.transaction.application.CreditAffiliationOperations;
import com.nttdata.transaction.domain.CreditAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.CreditAffiliationDao;
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
 * CREDITAFFILIATIONCONTROLLER.
 * Realiza las afiliaciones de los creditos con clientes
 */
@Slf4j
@RestController
@RequestMapping("/transactions/creditAffiliation")
@RequiredArgsConstructor
public class CreditAffiliationController {
    /**
     * Operaciones de Afiliación de creditos.
     */
    private final CreditAffiliationOperations creditAffiliationOperations;

    /**
     * Busca  los datos de todas las afiliaciones de credito de un cliente.
     * @return Flux<CreditAffiliation>
     */
    @GetMapping
    public Flux<CreditAffiliation> getAll() {
        return creditAffiliationOperations.findAll();
    }

    /**
     * Busca por el Id los datos de la afiliacion de credito de un cliente.
     * @param id
     * @return Mono<CreditAffiliation>
     */
    @GetMapping("/{id}")
    public Mono<CreditAffiliation> getById(@PathVariable final String id) {
        return creditAffiliationOperations.findById(id);
    }

    /**
     *  Regitra las afiliaciones de credito de un cliente.
     * @param credittAffiliation
     * @return Mono<CreditAffiliation>
     */
    @PostMapping
    public Mono<CreditAffiliation> post(
            @RequestBody final CreditAffiliation credittAffiliation) {
        return creditAffiliationOperations.create(credittAffiliation);
    }

    /**
     * Actualiza las afiliaciones de credito de un cliente.
     * @param id
     * @param credittAffiliation
     * @return Mono<CreditAffiliation>
     */
    @PutMapping("/{id}")
    public Mono<CreditAffiliation> put(
            @PathVariable final String id,
            @RequestBody final CreditAffiliation credittAffiliation) {
        return creditAffiliationOperations.update(id, credittAffiliation);
    }

    /**
     * Elimina los datos de la afiliacion de Credito de un cliente.
     * @param id
     * @return Mono<CreditAffiliationDao>
     */
    @DeleteMapping("/{id}")
    public  Mono<CreditAffiliationDao> delete(@PathVariable final String id) {
        return creditAffiliationOperations.delete(id);
    }
}
