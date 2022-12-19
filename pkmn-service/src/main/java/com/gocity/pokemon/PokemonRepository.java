package com.gocity.pokemon;

import city.pkmn.proto.pokemon.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class PokemonRepository {

    private final MongoTemplate template;

    PokemonRepository(MongoTemplate template) {
        this.template = template;
    }

    private Optional<PokemonDAO> find(Query query) {
        return Optional.ofNullable(
            template.findOne(query, PokemonDAO.class)
        );
    }

    private Optional<PokemonDAO> findRandom() {
        var aggregation = Aggregation.newAggregation(
            Aggregation.sample(1L)
        );

        var result = template.aggregate(aggregation, "pokemon", PokemonDAO.class);

        return Optional.ofNullable(
            result.getUniqueMappedResult()
        );
    }

    public Optional<PokemonDAO> findBy(Pokemon.PokemonRequest request) {
        var query = constructFindQuery(request);
        log.debug("Constructed Query: {}", query);

        return query.map(this::find)
            .orElse(findRandom());
    }

    private Optional<Query> constructFindQuery(Pokemon.PokemonRequest request) {
        var query = new Query();

        if (!request.hasName() && !request.hasId()) {
            return Optional.empty();
        }

        if (request.hasName()) {
            query.addCriteria(Criteria.where("name").is(request.getName()));
        }

        if (request.hasId()) {
            query.addCriteria(Criteria.where("_id").is(request.getId()));
        }

        return Optional.of(query);
    }
}
