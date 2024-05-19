package apps.amaralus.qa.platform.placeholder;

import apps.amaralus.qa.platform.placeholder.accessor.JsonAccessor;
import org.junit.jupiter.api.Test;

public class JsonAccessorTest {

    @Test
    void jsonPathTest() {
        JsonAccessor jsonAccessor = new JsonAccessor();

        String json = """
                                {
                    "some1" : "Hi",
                    "some2" : [
                        {
                            "hello" : "world"
                        },
                        {
                            "hello" : "world1"
                        }
                    ],
                    "some3" : ["1", "2", "3"]
                }
                """;

        System.out.println(jsonAccessor.read(json, "some1"));
        System.out.println(jsonAccessor.read(json, "some2"));
        System.out.println(jsonAccessor.read(json, "some2[0]"));
        System.out.println(jsonAccessor.read(json, "some2[0].hello"));
        System.out.println(jsonAccessor.read(json, "some3"));
        System.out.println(jsonAccessor.read(json, "some3[0]"));
        System.out.println(jsonAccessor.read(json, "some4"));
    }
}
