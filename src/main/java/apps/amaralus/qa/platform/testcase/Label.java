package apps.amaralus.qa.platform.testcase;

import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("label")
public record Label(String name, String description) {
}
