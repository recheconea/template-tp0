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
            checkDoubleQuantifier(currentChar, previousChar, regEx);
        }
        regEx.restartIterator();
        return true;
    }

    private boolean checkDoubleQuantifier(char currentChar, char previousChar, RegEx regEx) throws Exception {
        if (isQuantifier(currentChar) && isQuantifier(previousChar)) {
            regEx.restartIterator();
            throw new Exception("Invalid regex. Quantifiers should not be preceeded by other quantifiers");
        }
        return true;
    }

    private boolean validateStartAndEnd(RegEx regEx) throws Exception {
        if (isStartOrEnd(regEx.seekChar(0))) {
            throw  new Exception("Invalid Regex. Regex can not start with $ or ^");
        }
        char previousChar = regEx.getNext();
        char currentChar = regEx.getNext();
        while (!regEx.isLastChar()) {
            validateEscapedStartEnd(currentChar, previousChar, regEx);
            previousChar = currentChar;
            currentChar = regEx.getNext();
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
        char previousChar = currentChar;
        currentChar = regEx.getNext();
        while (!regEx.isLastChar()) {
            validateEscapedCloseBracket(currentChar, previousChar,regEx);
            if (currentChar == '[' && previousChar != '\\') {
                validateEscapedCharsInBrackets(regEx);
                previousChar = ']';
                currentChar = regEx.getNext();
            } else {
                previousChar = currentChar;
                currentChar = regEx.getNext();
            }
        }
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

    public boolean validate(RegEx regEx) throws Exception {
        validateQuantifiers(regEx);
        validateStartAndEnd(regEx);
        validateSquareBrackets(regEx);
        return true;
    }
}
