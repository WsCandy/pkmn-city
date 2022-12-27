use grpc::Result;
use grpc::ServerBuilder;
use grpc::ServerHandlerContext;
use grpc::ServerRequestSingle;
use grpc::ServerResponseUnarySink;
use rust_pokemon_grpc::PokemonRequest;
use rust_pokemon_grpc::PokemonResponse;
use rust_pokemon_grpc::PokemonService;
use rust_pokemon_grpc::PokemonServiceServer;
use std::thread;

// IDE support for Rust with Bazel is limited at the moment so it's difficult to develop and maintain.
#[derive(Default)]
struct PokemonServer {}

impl PokemonServer {
    pub fn new() -> PokemonServer {
        PokemonServer {}
    }
}

// protoc-gen-grpc_rust_plugin does not currently support proto3 optional syntax to compile remove the optional declaration in the .proto files.
impl PokemonService for PokemonServer {
    fn find(&self, _o: ServerHandlerContext, _req: ServerRequestSingle<PokemonRequest>, resp: ServerResponseUnarySink<PokemonResponse>) -> Result<()> {
        resp.finish(PokemonResponse {
            id: 1,
            name: "Bulbasaur".to_owned(),
            species: "Seed Pokémon".to_owned(),
            description: "Hello from Rust".to_owned(),
            ..Default::default()
        })
    }
}

fn main() {
    let service_def = PokemonServiceServer::new_service_def(PokemonServer::new());
    let port = 8081;
    let mut server_builder = ServerBuilder::new_plain();

    server_builder.add_service(service_def);
    server_builder.http.set_port(port);

    let server = server_builder.build()
        .expect("build");

    println!("Server started on addr {}", server.local_addr());

    loop {
        thread::park();
    }
}
