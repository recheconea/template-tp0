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

   /* public char generateRandomChar(char[] charList) {
        int randomChar = generator.nextInt(charList.length);
        return charList[randomChar];
    }*/

    public char generateRandomChar() {
        return (char) generator.nextInt(255);
    }

//    public int getRandomSize() {
//        return generator.nextInt(10);
//    }

    public int getRandomLength(int maxSize) {
        return generator.nextInt(maxSize);
    }

}
