package com.nttdata.transaction.infraestructure.rest;

import com.nttdata.transaction.application.AccountAffiliationOperations;
import com.nttdata.transaction.domain.AccountAffiliation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public Mono<ResponseEntity<Flux<AccountAffiliation>>> getAll() {
        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.OK)
                        .body(accountAffiliationOperations.findAll())
        );
    }

    /**
     * Busca por Id los datos de la afiliacion de cuentas bancarias.
     * @param id codigo.
     * @return Mono<AccountAffiliation>
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountAffiliation>> getById(
            @PathVariable final String id) {
        return accountAffiliationOperations.findById(id)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Regitra las afiliaciones de cuentas bancarias de un cliente.
     * @param accountAffiliation afiliación de cuenta.
     * @return Mono<AccountAffiliation>
     */
    @PostMapping
    public Mono<ResponseEntity<AccountAffiliation>> post(
            @RequestBody final AccountAffiliation accountAffiliation) {
        return accountAffiliationOperations.create(accountAffiliation)
                .filter(accountAffiliationResponse -> accountAffiliationResponse.getId() != null)
                .map(accountAffiliationResponse ->ResponseEntity
                        .ok()
                        .body(accountAffiliationResponse)
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Actualiza las afiliaciones de cuentas bancarias de un cliente.
     * @param id codigo.
     * @param accountAffiliation afiliación de cuenta.
     * @return Mono<AccountAffiliation>
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<AccountAffiliation>> put(
            @PathVariable final String id,
            @RequestBody final AccountAffiliation accountAffiliation) {
        return accountAffiliationOperations.update(id, accountAffiliation)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Elimina los datos de la afiliacion de cuentas bancarias de un cliente.
     * @param id codigo.
     * @return Mono<AccountAffiliationDao>
     */
    @DeleteMapping("/{id}")
    public  Mono<ResponseEntity<Void>> delete(@PathVariable final String id) {
        return accountAffiliationOperations.delete(id)
                .map(c -> ResponseEntity
                        .noContent()
                        .<Void>build())
                .defaultIfEmpty(ResponseEntity
                        .notFound()
                        .build());
    }
}
