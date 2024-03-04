package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.placeholder.Placeholder;
import apps.amaralus.qa.platform.placeholder.generate.GeneratedPlaceholderType;
import apps.amaralus.qa.platform.placeholder.generate.PlaceholderGeneratorsProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceholderResolver {

    //    private final ResolvingContext resolvingContext;
    private final PlaceholderGeneratorsProvider generatorsProvider;

    public Object resolve(Placeholder placeholder) {
        var locationType = placeholder.getPlaceholderType();
        if (locationType instanceof GeneratedPlaceholderType generatedType)
            return generatorsProvider.getGenerator(generatedType).generateValue();

        return null;
    }
}
