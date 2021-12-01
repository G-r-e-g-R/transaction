package com.nttdata.transaction.infraestructure.client;

import com.nttdata.transaction.domain.bean.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ReactiveFeignClient( name = "product")
public interface ProductAccountClient {
    @GetMapping("/products/account/{id}")
    public Mono<Account> getById(@PathVariable String id);

    @GetMapping
    public Flux<Account> getAll();
}
