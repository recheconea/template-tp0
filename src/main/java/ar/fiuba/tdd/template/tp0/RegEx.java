package ar.fiuba.tdd.template.tp0;

/**
 * Created by rodrigo on 19/03/16.
 */
public class RegEx {
    private String regEx;
    private int iterator;

    public RegEx(String regEx) {
        this.regEx = regEx;
        this.iterator = 0;
    }

    public char getNext() {
        char nextChar = regEx.charAt(iterator);
        iterator++;
        return nextChar;
    }

    public int getIteratorPosition() {
        return iterator;
    }

    public String getGroup() {
        for (int index = iterator + 1; index < regEx.length(); index++) {
            if (regEx.charAt(index) == ']') {
                String group = regEx.substring(iterator, index);
                iterator = index + 1;
                return group;
            }
        }
        return regEx;
    }

/*    public boolean isNextModifier() {
        return (regEx.charAt(iterator) == '*' || regEx.charAt(iterator) == '+' || regEx.charAt(iterator) == '?');
    }*/

    public int getRepetitionsNumber() {
        if (regEx.length() == iterator) {
            return 1;
        } else if (regEx.charAt(iterator) == '*') {
            return new RandomGenerator().getRandomLength(10);
        } else if (regEx.charAt(iterator) == '+') {
            return (new RandomGenerator().getRandomLength(10) + 1);
        } else if (regEx.charAt(iterator) == '?') {
            return new RandomGenerator().getRandomLength(1);
        }
        return 1;
    }
}
