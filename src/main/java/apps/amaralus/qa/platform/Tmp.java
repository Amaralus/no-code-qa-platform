package apps.amaralus.qa.platform;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class Tmp {

    public static void main(String[] args) {
        var str = "123.123";

        var number = str.contains(".")
                ? Double.parseDouble(str)
                : Long.parseLong(str);

        System.out.println(number);
    }

    private static void copyName() {
        var str = "abc (1)";
        var pattern = Pattern.compile("^(.*?)(\\(\\d+\\))$");
        var matcher = pattern.matcher(str);

        String name = str.trim();
        int version = 0;

        if (matcher.matches()) {
            name = matcher.group(1).trim();
            var versionGroup = matcher.group(2);
            version = Integer.parseInt(versionGroup.substring(1, versionGroup.length() - 1));
        }

        var result = name + " (" + ++version + ")";

        System.out.println(result);
    }

    @SuppressWarnings("java:S127")
    private static void insertBefore() {
        var list = new LinkedList<>(List.of("a", "b", "a", "b", "a"));

        for (int i = 0; i < list.size(); i++) {
            var val = list.get(i);
            if (val.equals("b")) {
                list.add(i, "x");
                i++;
            }
        }

        System.out.println(list);
    }

    private static void clustering() {
        var dependencies = new LinkedMultiValueMap<String, String>();
        dependencies.addAll("A", List.of());
        dependencies.add("B", "A");
        dependencies.add("C", "A");
        dependencies.addAll("D", List.of("B", "C"));

        var buckets = new LinkedMultiValueMap<>();
        dependencies.forEach((k, vList) -> vList.forEach(v -> buckets.add(v, k)));

        System.out.println(buckets);
    }
}
