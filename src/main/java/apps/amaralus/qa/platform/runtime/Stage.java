package apps.amaralus.qa.platform.runtime;

import org.jetbrains.annotations.NotNull;

public interface Stage {

    void execute();

    void taskFinishCallback();

    void addInput(@NotNull Stage stage);

    void addOutput(@NotNull Stage stage);
}
