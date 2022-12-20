package com.gocity.pokemon;

import city.pkmn.proto.pokemon.Pokemon.PokemonRequest;
import city.pkmn.proto.pokemon.Pokemon.PokemonResponse;
import city.pkmn.proto.pokemon.PokemonServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PokemonService extends PokemonServiceGrpc.PokemonServiceImplBase {

    private final PokemonRepository repository;

    @Autowired
    PokemonService(PokemonRepository repository) {
        this.repository = repository;
    }

    @Override
    public void find(PokemonRequest request, StreamObserver<PokemonResponse> responseObserver) {
        var result = repository.findBy(request);
        
        if (result.isEmpty()) {
            var NOT_FOUND = Status.NOT_FOUND
                .withDescription("Cannot find requested Pokémon")
                .asRuntimeException();

            responseObserver.onError(NOT_FOUND);
        }

        result.ifPresent(pokemon -> {
            responseObserver.onNext(pokemon.toResponse());
            responseObserver.onCompleted();
        });
    }
}
