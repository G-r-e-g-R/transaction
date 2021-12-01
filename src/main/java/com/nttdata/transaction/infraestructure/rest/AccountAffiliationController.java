package com.nttdata.transaction.infraestructure.rest;

import com.nttdata.transaction.application.AccountAffiliationOperations;
import com.nttdata.transaction.domain.AccountAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.AccountAffiliationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ACCOUNTAFFILIATIONCONTROLLER: Realiza las afiliaciones de cuentas bancarias con clientes
 */
@Slf4j
@RestController
@RequestMapping("/transactions/accountAffiliation")
@RequiredArgsConstructor
public class AccountAffiliationController {
    private final AccountAffiliationOperations accountAffiliationOperations;

    @GetMapping
    public Flux<AccountAffiliation> getAll(){
        return accountAffiliationOperations.findAll();
    }
    @GetMapping("/{id}")
    public Mono<AccountAffiliation> getById(@PathVariable String id){
        return accountAffiliationOperations.findById(id);
    }
    @PostMapping
    public Mono<AccountAffiliation> post(@RequestBody AccountAffiliation accountAffiliation){
        return accountAffiliationOperations.create(accountAffiliation);
    }
    @PutMapping("/{id}")
    public Mono<AccountAffiliation> put(@PathVariable String id, @RequestBody AccountAffiliation accountAffiliation){
        return accountAffiliationOperations.update(id,accountAffiliation);
    }
    @DeleteMapping("/{id}")
    public  Mono<AccountAffiliationDao> delete(@PathVariable String id){
        return accountAffiliationOperations.delete(id);
    }
}
