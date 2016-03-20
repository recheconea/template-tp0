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
        for (int i = 0; i < regExController.getRepetitionsNumber(); i++) {
            output = output.concat(String.valueOf(charGenerator.generateRandomChar()));
        }
        return output;
    }

    // TODO: Uncomment parameters
    public List<String> generate(String regEx, int numberOfResults) {
        ArrayList<String> outputArray = new ArrayList<>();
        RegEx regExController = new RegEx(regEx);
        for (int i = 0; i < numberOfResults; i++) {
           // maxLength += 1;
            String output = "";
            int currentPosition = 0;
            while (regEx.length() > currentPosition) {
                char nextChar = regExController.getNext();
                if (nextChar ==  '.') {
                    output = output.concat(getDot(regExController));
                }
            }
            System.out.println("PRINT ME BITCH");
            System.out.println(output);
            outputArray.add(output);

        }
        return outputArray;
        /*return new ArrayList<String>() {
            {
                add("a");
                add("b");
                add("c");
            }
        };*/
    }

}