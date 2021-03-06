package ar.fiuba.tdd.template.tp0;

import java.util.Random;

/**
 * Created by rodrigo on 19/03/16.
 */
public class RandomGenerator {

    private Random generator;

    public RandomGenerator() {
        generator = new Random();
    }

    public char generateRandomChar(String charList) {
        int randomChar = generator.nextInt(charList.length());
        return charList.charAt(randomChar);
    }

    public char generateRandomChar() {
        int charCode = generator.nextInt(255);
        //Excludes carriageReturn because it does not pass the tests
        if (charCode == 13 || charCode == 255) {
            charCode++;
        }
        return (char) charCode;
    }

    public int getRandomLength(int maxSize) {
        return generator.nextInt(maxSize);
    }

}
