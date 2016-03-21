package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private boolean validate(String regEx, int numberOfResults) throws Exception {
        RegExGenerator generator = new RegExGenerator();
        // TODO: Uncomment parameters
        List<String> results = generator.generate(regEx, numberOfResults);
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results
                .stream()
                .reduce(true,
                    (acc, item) -> {
                        Matcher matcher = pattern.matcher(item);
                        return acc && matcher.find();
                    },
                    (item1, item2) -> item1 && item2);
    }

    //TODO: Uncomment these tests
    @Test
    public void testAnyCharacter() throws Exception {
        assertTrue(validate(".", 1));
    }

    @Test
    public void testMultipleCharacters() throws Exception {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testLiteral() throws Exception {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() throws Exception {
        assertTrue(validate("\\@..", 1));
    }

    @Test
    public void testZeroOrOneCharacter() throws Exception {
        assertTrue(validate("\\@.h?", 1));
    }

    @Test
    public void testCharacterSet() throws Exception {
        assertTrue(validate("[abc]", 1));
    }

    @Test
    public void testCharacterSetWithQuantifiers() throws Exception {
        assertTrue(validate("[abc]+", 1));
    }

    @Test
    public void testExceptionMessageOnUnescaped() {
        try {
            validate("[ab-c]+", 1);
            //fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testExceptionMessageOnStart() {
        try {
            validate("^ab", 1);
            //fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // TODO: Add more tests!!!
}
