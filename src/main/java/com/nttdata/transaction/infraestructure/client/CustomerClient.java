package com.nttdata.transaction.infraestructure.client;

import com.nttdata.transaction.domain.bean.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;
@ReactiveFeignClient( name = "customer")

public interface CustomerClient {
    @GetMapping("/customers/{code}")
    public Mono<Customer> getById(@PathVariable String code);
}
