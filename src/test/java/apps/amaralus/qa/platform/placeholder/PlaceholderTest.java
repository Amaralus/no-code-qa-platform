package apps.amaralus.qa.platform.placeholder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static apps.amaralus.qa.platform.placeholder.DefaultLocationType.UNKNOWN;
import static org.junit.jupiter.api.Assertions.*;

class PlaceholderTest {

    @Test
    void invalidBraces() {
        assertThrowsExactly(
                InvalidPlaceholderException.class,
                () -> Placeholder.parse("{{}"));
    }

    @Test
    void emptyPlaceholder() {
        assertThrowsExactly(
                InvalidPlaceholderException.class,
                () -> Placeholder.parse("{{}}"));
    }

    @Test
    void locationParsed() {
        var placeholder = Placeholder.parse("{{abc}}");
        assertEquals("abc", placeholder.getLocation());
        assertEquals(UNKNOWN, placeholder.getLocationType());
        assertNull(placeholder.getId());
        assertNull(placeholder.getVariable());
    }

    @Test
    void invalidLocationWithIdDelimiter() {
        Assertions.assertThrowsExactly(
                InvalidPlaceholderException.class,
                () -> Placeholder.parse("{{abc#}}"));
    }

    @Test
    void invalidLocationWithId() {
        Assertions.assertThrowsExactly(
                InvalidPlaceholderException.class,
                () -> Placeholder.parse("{{abc#123}}"));
    }

    @Test
    void invalidLocationWithVariableDelimiter() {
        Assertions.assertThrowsExactly(
                InvalidPlaceholderException.class,
                () -> Placeholder.parse("{{abc:}}"));
    }

    @Test
    void invalidLocationWithIdAndVariableDelimiters() {
        Assertions.assertThrowsExactly(
                InvalidPlaceholderException.class,
                () -> Placeholder.parse("{{abc#:}}"));
    }

    @Test
    void invalidLocationWithIdAndVariableDelimiter() {
        Assertions.assertThrowsExactly(
                InvalidPlaceholderException.class,
                () -> Placeholder.parse("{{abc#123:}}"));
    }

    @Test
    void locationAndVariableParsed() {
        var placeholder = Placeholder.parse("{{abc:def}}");
        assertEquals("abc", placeholder.getLocation());
        assertEquals(UNKNOWN, placeholder.getLocationType());
        assertNull(placeholder.getId());
        assertEquals("def", placeholder.getVariable());
    }

    @Test
    void locationAndIdAndVariableParsed() {
        var placeholder = Placeholder.parse("{{abc#123:def}}");
        assertEquals("abc", placeholder.getLocation());
        assertEquals(UNKNOWN, placeholder.getLocationType());
        assertEquals(123, placeholder.getId());
        assertEquals("def", placeholder.getVariable());
    }

    @Test
    void invalidLocationWithoutIdWithLocation() {
        Assertions.assertThrowsExactly(
                InvalidPlaceholderException.class,
                () -> Placeholder.parse("{{abc#:def}}"));
    }
}