package ar.fiuba.tdd.template.tp0;

/**
 * Created by rodrigo on 19/03/16.
 */
public class RegEx {
    private String regEx;
    private int iterator;
    private int plusAmmount;
    private int asteriskAmmount;

    public RegEx(String regEx) {
        this.regEx = regEx;
        this.iterator = 0;
        this.plusAmmount = 0;
        this.asteriskAmmount = 0;
    }

    public char getNext() {
        char nextChar = regEx.charAt(iterator);
        iterator++;
        return nextChar;
    }

    public int getPlusAmmount() {
        return this.plusAmmount;
    }

    public int getAsteriskAmmount() {
        return this.asteriskAmmount;
    }


    public void setPlusAmmount(int plusAmmount) {
        this.plusAmmount = plusAmmount;
    }

    public void setAsteriskAmmount(int asteriskAmmount) {
        this.asteriskAmmount = asteriskAmmount;
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

    public char seekChar(int position) {
        if (position <= regEx.length()) {
            return regEx.charAt(position);
        }
        return '\0';
    }

    public void restartIterator() {
        this.iterator = 0;
    }

    public boolean isLastChar() {
        return regEx.length() >= getIteratorPosition();
    }

    public void setMaxLength(int minLenght, int maxLength) {
        this.asteriskAmmount = (maxLength - minLenght) / ((asteriskAmmount + plusAmmount > 0) ? asteriskAmmount + plusAmmount : 1);
        this.plusAmmount = this.asteriskAmmount;
    }

    public int getRepetitionsNumber() {
        if (regEx.length() == iterator) {
            return 1;
        } else if (regEx.charAt(iterator) == '*') {
            iterator++;
            return new RandomGenerator().getRandomLength(asteriskAmmount);
        } else if (regEx.charAt(iterator) == '+') {
            iterator++;
            return (new RandomGenerator().getRandomLength(plusAmmount) + 1);
        } else if (regEx.charAt(iterator) == '?') {
            iterator++;
            return new RandomGenerator().getRandomLength(1);
        }
        return 1;
    }
}
