package com.nttdata.transaction.infraestructure.client;

import com.nttdata.transaction.domain.bean.Account;
import com.nttdata.transaction.domain.bean.Credit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ReactiveFeignClient( name = "product")
public interface ProductCreditClient {
    @GetMapping("/products/credit{id}")
    public Mono<Credit> getById(@PathVariable String id);

}
