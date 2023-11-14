package apps.amaralus.qa.platform.runtime;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

public interface Stage extends Executable, Cancelable {

    void taskFinishCallback();

    void addInput(@NotNull Stage stage);

    void addOutput(@NotNull Stage stage);

    int inputsCount();

    int outputsCount();

    static void link(@NotNull Stage first, @NotNull Stage second) {
        Assert.notNull(first, "Stage must not be null!");
        Assert.notNull(second, "Stage must not be null!");

        first.addOutput(second);
        second.addInput(first);
    }
}
