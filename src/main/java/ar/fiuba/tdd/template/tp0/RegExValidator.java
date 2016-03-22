package ar.fiuba.tdd.template.tp0;

/**
 * Created by rodrigo on 21/03/16.
 */
public class RegExValidator {

    private boolean validateQuantifiers(RegEx regEx) throws Exception {
        char currentChar = regEx.getNext();
        if (isQuantifier(currentChar)) {
            regEx.restartIterator();
            throw new Exception("Invalid regex. Quantifier can not be the first char on it");
        }
        char previousChar;
        while (!regEx.isLastChar()) {
            previousChar = currentChar;
            currentChar = regEx.getNext();
            if (isQuantifier(currentChar) && isQuantifier(previousChar)) {
                regEx.restartIterator();
                throw new Exception("Invalid regex. Quantifiers should not be preceeded by other quantifiers");
            }
        }
        regEx.restartIterator();
        return true;
    }

    private boolean validateStartAndEnd(RegEx regEx) throws Exception {
        if (isStartOrEnd(regEx.seekChar(0))) {
            throw  new Exception("Invalid Regex. Regex can not start with $ or ^");
        }
        char previousChar; //= regEx.getNext();
        char currentChar = regEx.getNext();
        while (!regEx.isLastChar()) {
            previousChar = currentChar;
            currentChar = regEx.getNext();
            validateEscapedStartEnd(currentChar, previousChar, regEx);
        }
        regEx.restartIterator();
        return true;
    }

    private boolean validateEscapedStartEnd(char currentChar, char previousChar, RegEx regEx) throws Exception {
        if (isStartOrEnd(currentChar) && previousChar != '\\') {
            regEx.restartIterator();
            throw new Exception("Invalid regex. ^ and $ must be preceeded by \\");
        }
        return true;
    }

    private boolean validateSquareBrackets(RegEx regEx) throws Exception {
        char currentChar = regEx.getNext();
        if (currentChar == ']') {
            throw new Exception("Invalid regex. Regex should not start with ]");
        }
        char previousChar;// = currentChar;
        while (!regEx.isLastChar()) {
            previousChar = currentChar;
            currentChar = regEx.getNext();
            validateEscapedCloseBracket(currentChar, previousChar,regEx);
            if (currentChar == '[' && previousChar != '\\') {
                validateEscapedCharsInBrackets(regEx);
                currentChar = ']';
            }
        }
        regEx.restartIterator();
        return true;
    }

    private boolean validateEscapedCloseBracket(char currentChar, char previousChar, RegEx regEx) throws Exception {
        if (currentChar == ']' && previousChar != '\\') {
            regEx.restartIterator();
            throw new Exception("Invalid Regex. ] must be preceeded by \\ or [");
        }
        return true;
    }

    private boolean validateEscapedCharsInBrackets(RegEx regEx) throws Exception {
        char currentChar = regEx.getNext();
        char previousChar = '\0';
        while (!regEx.isLastChar()) {
            if (currentChar == ']' && previousChar != '\\') {
                return true;
            } else if ((currentChar == '[' || currentChar == '{' || currentChar == '}' || currentChar == '-') && previousChar != '\\') {
                throw new Exception("Invalid regex. Special characters inside groups must be escaped");
            }
        }
        throw new Exception("Invalid regex. All opened [ must be closed.");
    }

    private boolean isQuantifier(char currentChar) {
        return currentChar == '*' || currentChar == '+' || currentChar == '?';
    }

    private boolean isStartOrEnd(char currentChar) {
        return currentChar == '^' || currentChar == '$';
    }

    private int getQuantifierMultiplicity(char currentChar, char lastChar, RegEx regEx) {
        if (currentChar == '+' && lastChar != '\\') {
            regEx.setPlusAmmount(regEx.getPlusAmmount() + 1);
            return 1;
        } else if (currentChar == '*' && lastChar != '\\') {
            regEx.setAsteriskAmmount(regEx.getAsteriskAmmount() + 1);
        }
        return 0;
    }

    private void findCloseBracket(RegEx regEx) {
        char currentChar = regEx.getNext();
        char lastChar = '[';
        while (!regEx.isLastChar()) {
            if (currentChar == ']' && lastChar != '\\') {
                return;
            }
            lastChar = currentChar;
            currentChar = regEx.getNext();
        }
    }

    public int validateMaxSize(RegEx regEx, int maxSize) throws Exception {
        int minSize = 1;
        char currentChar = regEx.getNext();
        char lastChar = '\0';
        while (!regEx.isLastChar()) {
            if (currentChar == '[') {
                findCloseBracket(regEx);
                lastChar = ']';
                currentChar = regEx.getNext();
            } else if (isQuantifier(currentChar)) {
                minSize += getQuantifierMultiplicity(currentChar, lastChar, regEx);
                lastChar = currentChar;
                currentChar = regEx.getNext();
            } else if (lastChar != '\0') {
                minSize += 1;
                lastChar = currentChar;
                currentChar = regEx.getNext();
            }
        }
        return validateSizesValues(minSize, maxSize);
    }

    private int validateSizesValues(int minSize, int maxSize) throws Exception {
        if (minSize > maxSize) {
            throw new Exception("Max Size cant be lower than the minSize required by regex.");
        }
        return minSize;
    }

    public boolean validate(RegEx regEx) throws Exception {
        validateQuantifiers(regEx);
        validateStartAndEnd(regEx);
        validateSquareBrackets(regEx);
        return true;
    }
}
