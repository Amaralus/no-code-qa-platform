package apps.amaralus.qa.platform.label;

import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("label")
public record LabelModel(String name, String description, String project) {
}
