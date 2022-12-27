package main

import (
	"context"
	"google.golang.org/grpc"
	"log"
	"net"
	pkmn "pkmn.city/proto/pokemon"
)

type PokemonServer struct {
	pkmn.UnimplementedPokemonServiceServer
}

func (server *PokemonServer) Find(ctx context.Context, request *pkmn.PokemonRequest) (*pkmn.PokemonResponse, error) {
	log.Printf("Received ID: %v", *request.Id)

	return &pkmn.PokemonResponse{
		Id:          1,
		Name:        "Bulbasaur",
		Species:     "Seed Pokémon",
		Description: "Hello from GoLang",
	}, nil
}

func main() {
	listen, err := net.Listen("tcp", ":8081")

	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	server := grpc.NewServer()

	pkmn.RegisterPokemonServiceServer(server, &PokemonServer{})

	log.Printf("PokemonServer listening at %v", listen.Addr())

	if err := server.Serve(listen); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
