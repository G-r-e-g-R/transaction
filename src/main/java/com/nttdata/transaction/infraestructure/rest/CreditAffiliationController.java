package com.nttdata.transaction.infraestructure.rest;

import com.nttdata.transaction.application.CreditAffiliationOperations;
import com.nttdata.transaction.domain.CreditAffiliation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<Flux<CreditAffiliation>>> getAll() {
        return Mono.just(
                ResponseEntity
                        .ok()
                        .body(creditAffiliationOperations.findAll()));
    }

    /**
     * Busca por el Id los datos de la afiliacion de credito de un cliente.
     * @param id codigo.
     * @return Mono<CreditAffiliation>
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<CreditAffiliation>> getById(@PathVariable final String id) {
        return creditAffiliationOperations.findById(id)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     *  Regitra las afiliaciones de credito de un cliente.
     * @param credittAffiliation afiliación de credito.
     * @return Mono<CreditAffiliation>
     */
    @PostMapping
    public Mono<ResponseEntity<CreditAffiliation>> post(
            @RequestBody final CreditAffiliation credittAffiliation) {
        return creditAffiliationOperations.create(credittAffiliation)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza las afiliaciones de credito de un cliente.
     * @param id codigo.
     * @param credittAffiliation afiliación de credito.
     * @return Mono<CreditAffiliation>
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<CreditAffiliation>> put(
            @PathVariable final String id,
            @RequestBody final CreditAffiliation credittAffiliation) {
        return creditAffiliationOperations.update(id, credittAffiliation)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Elimina los datos de la afiliacion de Credito de un cliente.
     * @param id codigo.
     * @return Mono<CreditAffiliationDao>
     */
    @DeleteMapping("/{id}")
    public  Mono<ResponseEntity<Void>> delete(@PathVariable final String id) {
        return creditAffiliationOperations.delete(id)
                .map(c -> ResponseEntity
                        .noContent()
                        .<Void>build())
                .defaultIfEmpty(ResponseEntity
                        .notFound()
                        .build());
    }
}
