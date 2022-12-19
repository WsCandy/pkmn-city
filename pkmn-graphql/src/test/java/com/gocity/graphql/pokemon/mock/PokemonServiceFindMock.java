package com.gocity.graphql.pokemon.mock;

import com.google.protobuf.util.JsonFormat;
import city.pkmn.proto.pokemon.Pokemon;
import city.pkmn.proto.pokemon.PokemonServiceGrpc;
import io.grpc.stub.StreamObserver;

public class PokemonServiceFindMock extends PokemonServiceGrpc.PokemonServiceImplBase {

    private final Pokemon.PokemonResponse response;

    public PokemonServiceFindMock(String jsonResponse) throws Exception {
        var response = Pokemon.PokemonResponse.newBuilder();

        JsonFormat.parser().ignoringUnknownFields()
            .merge(jsonResponse, response);

        this.response = response.build();
    }

    @Override
    public void find(Pokemon.PokemonRequest request, StreamObserver<Pokemon.PokemonResponse> responseObserver) {
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
