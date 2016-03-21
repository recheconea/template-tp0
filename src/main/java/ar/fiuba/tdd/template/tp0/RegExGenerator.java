package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegExGenerator {
    // TODO: Uncomment this field
    //private int maxLength;
    private RandomGenerator charGenerator;

    public RegExGenerator(/*int maxLength*/) {
        //this.maxLength = maxLength;
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

    public String getGroup(RegEx regExController) {
        String output = "";
        return output;
    }

    // TODO: Uncomment parameters
    public List<String> generate(String regEx, int numberOfResults) {
        ArrayList<String> outputArray = new ArrayList<>();
        RegEx regExController = new RegEx(regEx);
        for (int i = 0; i < numberOfResults; i++) {
           // maxLength += 1;
            String output = "";
            while (regEx.length() > regExController.getIteratorPosition()) {
                char nextChar = regExController.getNext();
                if (nextChar ==  '.') {
                    output = output.concat(getDot(regExController));
                } else if (nextChar == '[') {
                    output = output.concat(getGroup(regExController));
                } else if (nextChar == '\\') {
                    output = output.concat(String.valueOf(regExController.getNext()));
                }
            }
            outputArray.add(output);

        }
        return outputArray;
    }

}