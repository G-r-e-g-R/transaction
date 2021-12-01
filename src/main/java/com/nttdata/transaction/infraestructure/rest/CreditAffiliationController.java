package com.nttdata.transaction.infraestructure.rest;

import com.nttdata.transaction.application.CreditAffiliationOperations;
import com.nttdata.transaction.domain.CreditAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.CreditAffiliationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CREDITAFFILIATIONCONTROLLER: Realiza las afiliaciones de los creditos con clientes
 */
@Slf4j
@RestController
@RequestMapping("/transactions/creditAffiliation")
@RequiredArgsConstructor
public class CreditAffiliationController {

    private final CreditAffiliationOperations creditAffiliationOperations;

    @GetMapping
    public Flux<CreditAffiliation> getAll(){
        return creditAffiliationOperations.findAll();
    }
    @GetMapping("/{id}")
    public Mono<CreditAffiliation> getById(@PathVariable String id){
        return creditAffiliationOperations.findById(id);
    }
    @PostMapping
    public Mono<CreditAffiliation> post(@RequestBody CreditAffiliation credittAffiliation){
        return creditAffiliationOperations.create(credittAffiliation);
    }
    @PutMapping("/{id}")
    public Mono<CreditAffiliation> put(@PathVariable String id, @RequestBody CreditAffiliation credittAffiliation){
        return creditAffiliationOperations.update(id,credittAffiliation);
    }
    @DeleteMapping("/{id}")
    public  Mono<CreditAffiliationDao> delete(@PathVariable String id){
        return creditAffiliationOperations.delete(id);
    }
}
