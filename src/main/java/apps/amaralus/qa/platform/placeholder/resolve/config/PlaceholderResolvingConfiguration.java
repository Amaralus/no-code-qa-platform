package apps.amaralus.qa.platform.placeholder.resolve.config;

import apps.amaralus.qa.platform.placeholder.generate.PlaceholderGeneratorsProvider;
import apps.amaralus.qa.platform.placeholder.resolve.PlaceholderResolver;
import apps.amaralus.qa.platform.placeholder.resolve.ResolvingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaceholderResolvingConfiguration {

    @Bean
    public PlaceholderResolver defaultPlaceholderResolver(ResolvingContext resolvingContext,
                                                          PlaceholderGeneratorsProvider placeholderGeneratorsProvider) {
        return new PlaceholderResolver(resolvingContext, placeholderGeneratorsProvider);
    }
}
