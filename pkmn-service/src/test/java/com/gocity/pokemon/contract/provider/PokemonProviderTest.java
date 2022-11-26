package com.gocity.pokemon.contract.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.gocity.pokemon.PokemonApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@PactFolder("pacts")
@ActiveProfiles("test")
@Provider("pkmn-service")
@SpringBootTest(classes = PokemonApplication.class)
public class PokemonProviderTest {

    // Needs plugin to work https://docs.pact.io/implementation_guides/pact_plugins
    // https://github.com/pact-foundation/pact-plugins/tree/main/examples/gRPC/area_calculator/provider-jvm

    @Disabled
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
}
