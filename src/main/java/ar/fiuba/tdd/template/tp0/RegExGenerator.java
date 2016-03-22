package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegExGenerator {
    // TODO: Uncomment this field
    private int maxLength;
    private RandomGenerator charGenerator;

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
        charGenerator = new RandomGenerator();
    }

    public String getDot(RegEx regExController) {
        String output = "";
        int repetitions = regExController.getRepetitionsNumber();
        for (int i = 0; i < repetitions; i++) {
            output = output.concat(String.valueOf(charGenerator.generateRandomChar()));
        }
        return output;
    }

    public String getLiteral(RegEx regExController, char literal) {
        String output = "";
        int repetitions = regExController.getRepetitionsNumber();
        for (int i = 0; i < repetitions; i++) {
            output = output.concat(String.valueOf(literal));
        }
        return output;
    }

    public String getGroup(RegEx regExController) {
        String output = "";
        String group = regExController.getGroup();
        int repetitions = regExController.getRepetitionsNumber();
        for (int i = 0; i < repetitions; i++) {
            output = output.concat(String.valueOf(charGenerator.generateRandomChar(group)));
        }
        return output;
    }

    public String getNextCharValues(char nextChar, RegEx regExController) {
        if (nextChar ==  '.') {
            return getDot(regExController);
        } else if (nextChar == '[') {
            return getGroup(regExController);
        } else if (nextChar == '\\') {
            return String.valueOf(regExController.getNext());
        } else {
            return getLiteral(regExController, nextChar);
        }
    }

    // TODO: Uncomment parameters
    public List<String> generate(String regEx, int numberOfResults) throws Exception {
        RegExValidator validator = new RegExValidator();
        ArrayList<String> outputArray = new ArrayList<>();
        RegEx regExController = new RegEx(regEx);
        validator.validate(regExController);
        regExController.setMaxLength(validator.validateMaxSize(regExController, maxLength), maxLength);
        regExController.restartIterator();
        for (int i = 0; i < numberOfResults; i++) {
            String output = "";
            regExController.restartIterator();
            while (regEx.length() > regExController.getIteratorPosition()) {
                char nextChar = regExController.getNext();
                output = output.concat(getNextCharValues(nextChar, regExController));
            }
            outputArray.add(output);
        }
        return outputArray;
    }

}