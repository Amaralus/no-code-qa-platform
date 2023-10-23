package apps.amaralus.qa.platform.label;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("label")
public record LabelModel(@Id String name, String description, String project) {
}
