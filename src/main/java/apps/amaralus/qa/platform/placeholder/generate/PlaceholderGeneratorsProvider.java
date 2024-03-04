package apps.amaralus.qa.platform.placeholder.generate;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PlaceholderGeneratorsProvider {

    private final Map<GeneratedPlaceholderType, PlaceholderGenerator> generators;

    public PlaceholderGeneratorsProvider(List<PlaceholderGenerator> generators) {
        this.generators = generators.stream()
                .collect(Collectors.toMap(
                        PlaceholderGenerator::getPlaceholderLocation,
                        Function.identity()
                ));
    }

    public PlaceholderGenerator getGenerator(@NotNull GeneratedPlaceholderType location) {
        Assert.notNull(location, "location must not be null!");
        return generators.get(location);
    }
}
