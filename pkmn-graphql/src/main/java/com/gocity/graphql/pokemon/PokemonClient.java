package com.gocity.graphql.pokemon;

import common.proto.pokemon.Pokemon;
import common.proto.pokemon.Pokemon.PokemonResponse;
import common.proto.pokemon.PokemonServiceGrpc;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
@Service
public class PokemonClient {

    private PokemonServiceGrpc.PokemonServiceBlockingStub stub;

    @Autowired
    public PokemonClient(@Qualifier("pkmn-channel") Channel channel) {
        this.setStub(channel);
    }

    public void setStub(Channel channel) {
        this.stub = PokemonServiceGrpc.newBlockingStub(channel);
    }

    public Future<PokemonResponse> findPokemon(Optional<Integer> id, Optional<String> name) {
        var request = Pokemon.PokemonRequest.newBuilder();

        id.ifPresent(request::setId);
        name.ifPresent(request::setName);

        return CompletableFuture.supplyAsync(() -> stub.find(request.build()));
    }
}
