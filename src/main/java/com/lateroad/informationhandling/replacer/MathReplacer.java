package com.lateroad.informationhandling.replacer;

import com.lateroad.informationhandling.action.RPN;
import com.lateroad.informationhandling.collection.VariableMap;
import com.lateroad.informationhandling.interpreter.Calculator;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathReplacer {
    private static final Logger LOGGER = Logger.getLogger(MathReplacer.class);


    private static final String REGEX_MATH_DEFINER = "\\(?-?(\\d+\\W*)+[^\\s\\[A-z]\\)?";
    private static final String REGEX_ANYCREMENT = "([-+]{2}[A-z]+)|([A-z]+[-+]{2})";
    private static final String REGEX_INCREMENT_PREF = "\\+\\+[A-z]+";
    private static final String REGEX_DECREMENT_PREF = "--[A-z]+";
    private static final String REGEX_INCREMENT_POST = "[A-z]+\\+\\+";
    private static final String REGEX_DECREMENT_POST = "[A-z]+--";
    private static final String REGEX_PLUS = "[+]";
    private static final String REGEX_MINUS = "[-]";


    private VariableMap variableMap = VariableMap.getInstance();

    public String replaceExpressions(String text) {
        LOGGER.info("Starting of replacing math expression.");
        String resultText = replaceVariables(text);
        resultText = calculateAndReplace(resultText);
        return resultText;
    }


    private String replaceVariables(String text) {
        LOGGER.info("Starting of replacing expression variables.");
        String resultText = text;
        Pattern pattern = Pattern.compile(REGEX_ANYCREMENT);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String newMath = "";
            String oldMath = matcher.group();
            if (oldMath.matches(REGEX_INCREMENT_PREF)) {
                newMath = getVariableFromPrefix(oldMath, REGEX_INCREMENT_PREF);
            } else if (oldMath.matches(REGEX_INCREMENT_POST)) {
                newMath = getVariableFromPostfix(oldMath, REGEX_INCREMENT_POST);
            } else if (oldMath.matches(REGEX_DECREMENT_PREF)) {
                newMath = getVariableFromPrefix(oldMath, REGEX_DECREMENT_PREF);
            } else if (oldMath.matches(REGEX_DECREMENT_POST)) {
                newMath = getVariableFromPostfix(oldMath, REGEX_DECREMENT_POST);
            }
            resultText = resultText.replace(oldMath, newMath);
        }
        return resultText;
    }

    private String getVariableFromPrefix(String oldMath, String regex) {
        LOGGER.info("Starting of replacing prefix variable.");
        String newMath = "";
        String variable;
        int value;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(oldMath);
        while (matcher.find()) {
            switch (regex) {
                case REGEX_INCREMENT_PREF:
                    variable = matcher.group().replaceAll(REGEX_PLUS, "");
                    value = variableMap.get(variable) + 1;
                    variableMap.put(variable, value);
                    newMath = oldMath.replaceFirst(regex, String.valueOf(value));
                    break;
                case REGEX_DECREMENT_PREF:
                    variable = matcher.group().replaceAll(REGEX_MINUS, "");
                    value = variableMap.get(variable) - 1;
                    variableMap.put(variable, value);
                    newMath = oldMath.replaceFirst(regex, String.valueOf(value));
                    break;
                default:
                    break;
            }
        }
        return newMath;
    }

    private String getVariableFromPostfix(String oldMath, String regex) {
        LOGGER.info("Starting of replacing postfix variable.");
        String newMath = "";
        String variable;
        int value;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(oldMath);
        if (matcher.find()) {
            switch (regex) {
                case REGEX_INCREMENT_POST:
                    variable = matcher.group().replaceAll(REGEX_PLUS, "");
                    value = variableMap.get(variable);
                    newMath = oldMath.replaceFirst(regex, String.valueOf(value++));
                    variableMap.put(variable, value);
                    break;
                case REGEX_DECREMENT_POST:
                    variable = matcher.group().replaceAll(REGEX_MINUS, "");
                    value = variableMap.get(variable);
                    newMath = oldMath.replaceFirst(regex, String.valueOf(value--));
                    variableMap.put(variable, value);
                    break;
                default:
                    break;
            }
        }
        return newMath;
    }

    private String calculateAndReplace(String text) {
        LOGGER.info("Starting of replacing variables by its values.");
        Calculator calculator = new Calculator();
        RPN rpn = new RPN();
        String resultText = text;
        String rightLine;
        String badLine;
        Pattern pattern = Pattern.compile(REGEX_MATH_DEFINER);
        Matcher matcher = pattern.matcher(resultText);

        while (matcher.find()) {
            if (!matcher.group().isEmpty()) {
                badLine = matcher.group();
                rightLine = rpn.createExpression(badLine);
                rightLine = Integer.toString(calculator.calculate(rightLine));
                resultText = resultText.replace(badLine, rightLine);
            }
        }
        return resultText;
    }
}
