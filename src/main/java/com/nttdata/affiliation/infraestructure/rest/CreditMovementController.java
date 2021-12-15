package com.nttdata.affiliation.infraestructure.rest;

import com.nttdata.affiliation.application.CreditMovementOperations;
import com.nttdata.affiliation.domain.AccountMovement;
import com.nttdata.affiliation.domain.CreditMovement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CREDITMOVEMENTCONTROLLER.
 * Realiza los movimientos de los creditos de los clientes.
 */
@Slf4j
@RestController
@RequestMapping("/transactions/credits")
@RequiredArgsConstructor
public class CreditMovementController {
    /**
     * Operaciones de los movimientos de los creditos.
     */
    private final CreditMovementOperations creditMovementOperations;

    /**
     * Busca  los datos de todas los movimientos de credito.
     * de un cliente.
     * @return Flux<CreditMovement>
     */
    @GetMapping
    public Mono<ResponseEntity<Flux<CreditMovement>>>
    getAll() {
        return Mono.just(
                ResponseEntity
                        .ok()
                        .body(creditMovementOperations.findAll()));
    }

    /**
     * Busca por el Id los datos del movimiento de credito.
     * @param id codigo.
     * @return Mono<CreditMovement>
     */
    @GetMapping("/{id}")
    public
    Mono<ResponseEntity<CreditMovement>>
    getById(@PathVariable final String id) {
        return creditMovementOperations.findById(id)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * Busca por el Id de la afiliacion los datos del movimiento de la cuenta bancaria.
     * de un cliente.
     * @param idAffiliation codigo de afiliacion.
     * @return Mono<CreditMovement>
     */
    @GetMapping("/affiliation/{id}")
    public
    Flux<ResponseEntity<CreditMovement>>
    getByIdAffiliation(@PathVariable final String idAffiliation) {
        return creditMovementOperations.findByIdAffiliation(idAffiliation)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     *  Regitra el movimiento de un credito de un cliente.
     * @param creditMovement movimiento de credito.
     * @return Mono<CreditMovement>
     */
    @PostMapping
    public
    Mono<ResponseEntity<CreditMovement>>
    post(@RequestBody final CreditMovement creditMovement) {
        return creditMovementOperations.create(creditMovement)
                .filter(
                        creditMovementResponse
                                -> creditMovementResponse.getId() != null
                )
                .map(
                        creditMovementResponse
                                -> ResponseEntity
                                .ok()
                                .body(creditMovementResponse))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza los movimientos de un credito de un cliente.
     * @param id codigo.
     * @param creditMovement movimiento de credito.
     * @return Mono<CreditMovement>
     */
    @PutMapping("/{id}")
    public
    Mono<ResponseEntity<CreditMovement>>
    put(@PathVariable final String id,
        @RequestBody final CreditMovement creditMovement) {
        return creditMovementOperations.update(id, creditMovement)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Elimina el movimiento de un credito de un cliente.
     * @param id codigo.
     * @return Mono<Void>
     */
    @DeleteMapping("/{id}")
    public
    Mono<ResponseEntity<Void>>
    delete(@PathVariable final String id) {
        return creditMovementOperations.delete(id)
                .map(c -> ResponseEntity
                        .noContent()
                        .<Void>build())
                .defaultIfEmpty(ResponseEntity
                        .notFound()
                        .build());
    }
}
