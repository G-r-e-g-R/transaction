package com.nttdata.affiliation.infraestructure.repository;

import com.nttdata.affiliation.domain.AccountMovement;
import com.nttdata.affiliation.domain.bean.AccountAffiliation;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
/**
 * CUSTOMACCOUNTAFFILIATIONCRUDREPOSITORY.
 * Implementa las operaciones personalizadas
 * de la afiliación de cuentas bancarias.
 */
public class CustomAccountMovementCrudRepository
        implements ICustomAccountMovementCrudRepository {
    /**
     * Template.
     */
    private final ReactiveMongoTemplate mongoTemplate;

    /**
     * Constructor.
     * @param reactiveMongoTemplate Template.
     */
    public CustomAccountMovementCrudRepository(
            final ReactiveMongoTemplate reactiveMongoTemplate) {
        this.mongoTemplate = reactiveMongoTemplate;

    }

    /**
     * Obtiene los movimientos de la afiliacion.
     * @param idAffiliation Codigo de afiliacion.
     * @return Flux<AccountMovement>
     */

    @Override
    public Flux<AccountMovement> findByIdAccountAffiliation(String idAffiliation) {
        Query query = new Query(Criteria.where("idAccountAffiliation").is(idAffiliation));
        return mongoTemplate.find(query, AccountMovement.class);
    }
}
