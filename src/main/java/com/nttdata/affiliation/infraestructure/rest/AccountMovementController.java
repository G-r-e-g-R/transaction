package com.nttdata.affiliation.infraestructure.rest;

import com.nttdata.affiliation.application.AccountMovementOperations;
import com.nttdata.affiliation.domain.AccountMovement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ACCOUNTMOVEMENTCONTROLLER.
 * Realiza los movimientos de cuentas bancarias de los clientes.
 */
@Slf4j
@RestController
@RequestMapping("/movements/accounts")
@RequiredArgsConstructor
public class AccountMovementController {
    /**
     * Operaciones de los movimientos de cuenta bancaria.
     */
    private final AccountMovementOperations accountMovementOperations;

    /**
     * Busca  los datos de todas los movimientos de cuentas bancarias.
     * de un cliente.
     * @return Flux<AccountMovement>
     */
    @GetMapping
    public Mono<ResponseEntity<Flux<AccountMovement>>>
    getAll() {
        return Mono.just(
                ResponseEntity
                        .ok()
                        .body(accountMovementOperations.findAll()));
    }

    /**
     * Busca por el Id los datos del movimiento de la cuenta bacnaria.
     * de un cliente.
     * @param id codigo.
     * @return Mono<AccountMovement>
     */
    @GetMapping("/{id}")
    public
    Mono<ResponseEntity<AccountMovement>>
    getById(@PathVariable final String id) {
        return accountMovementOperations.findById(id)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * Busca por el Id de la afiliacion los datos del movimiento de la cuenta bacaaria.
     * de un cliente.
     * @param idAffiliation codigo de afiliacion.
     * @return Mono<AccountMovement>
     */
    @GetMapping("/affiliation/{id}")
    public
    Flux<ResponseEntity<AccountMovement>>
    getByIdAffiliation(@PathVariable final String idAffiliation) {
        return accountMovementOperations.findByIdAffiliation(idAffiliation)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     *  Regitra el movimiento de una cuenta bancaria de un cliente.
     * @param accountMovement movimiento de cuenta bancaria.
     * @return Mono<AccountMovement>
     */
    @PostMapping
    public
    Mono<ResponseEntity<AccountMovement>>
    post(@RequestBody final AccountMovement accountMovement) {
        return accountMovementOperations.create(accountMovement)
                .filter(
                        accountMovementResponse
                                -> accountMovementResponse.getId() != null
                )
                .map(
                        accountMovementResponse
                                -> ResponseEntity
                                .ok()
                                .body(accountMovementResponse))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza los movimientos de una cuenta bancaria de un cliente.
     * @param id codigo.
     * @param accountMovement movimiento de cuenta bancaria.
     * @return Mono<AccountMovement>
     */
    @PutMapping("/{id}")
    public
    Mono<ResponseEntity<AccountMovement>>
    put(@PathVariable final String id,
        @RequestBody final AccountMovement accountMovement) {
        return accountMovementOperations.update(id, accountMovement)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Elimina el movimiento de una cuenta bancaria de un cliente.
     * @param id codigo.
     * @return Mono<Void>
     */
    @DeleteMapping("/{id}")
    public
    Mono<ResponseEntity<Void>>
    delete(@PathVariable final String id) {
        return accountMovementOperations.delete(id)
                .map(c -> ResponseEntity
                        .noContent()
                        .<Void>build())
                .defaultIfEmpty(ResponseEntity
                        .notFound()
                        .build());
    }
}
