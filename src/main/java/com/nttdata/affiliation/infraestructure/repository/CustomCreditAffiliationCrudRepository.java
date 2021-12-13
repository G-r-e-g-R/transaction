package com.nttdata.affiliation.infraestructure.repository;

import com.nttdata.affiliation.domain.CreditMovement;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

/**
 * CUSTOMCREDITAFFILIATIONCRUDREPOSITORY.
 * Implementa las operaciones personalizadas
 * de la afiliación de Creditos.
 */
public class CustomCreditAffiliationCrudRepository
        implements ICustomCreditAffiliationCrudRepository {
    /**
     * Template.
     */
    private final ReactiveMongoTemplate mongoTemplate;

    /**
     * Constructor.
     * @param reactiveMongoTemplate Template.
     */
    public CustomCreditAffiliationCrudRepository(
            final ReactiveMongoTemplate reactiveMongoTemplate) {
        this.mongoTemplate = reactiveMongoTemplate;

    }
    /**
     * Obtiene los movimientos de la afiliacion.
     * @param idAffiliation Codigo de afiliacion.
     * @return Flux<CreditMovement>
     */

    @Override
    public Flux<CreditMovement> findByIdAffiliation(String idAffiliation) {
        Query query = new Query(Criteria.where("idAccountAffiliation").is(idAffiliation));
        return mongoTemplate.find(query, CreditMovement.class);
    }

}
