package com.lateroad.informationhandling.action;

import org.apache.log4j.Logger;

import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPN {
    private static final Logger LOGGER = Logger.getLogger(RPN.class);

    private static final String REGEX_NUMERIC_DEFINER = "-?\\d+(\\.\\d+)?";
    private static final String REGEX_CHECK_OPEN_BRACKET_BEFORE = "(?<=\\()-";
    private static final String REGEX_CHECK_NUMBER_BEFORE = "(?<!(\\d)|([\\W^(]))-";
    private static final String REGEX_CHECK_SPACE_BEFORE = "(?<!\\p{Blank})\\W";
    private static final String REGEX_ALL_SPACES_AND_TABS = "\\p{Blank}+";
    private static final String ZERO_MINUS = "0-";
    private static final String PLUS_ZERO_MINUS = "+0-";
    private static final String SPACE_SYMBOL = " ";

    public String createExpression(String line) {
        LOGGER.info("Creating of polish notation expression...");
        String normalizedLine = toRPNFormat(line);
        StringBuilder output = new StringBuilder();
        ArrayDeque<String> operationStack = new ArrayDeque<>();

        for (String character : normalizedLine.split(REGEX_ALL_SPACES_AND_TABS)) {
            if (isNumeric(character)) {
                output.append(character).append(SPACE_SYMBOL);
            } else {
                if ("(".equals(character))
                    operationStack.push(character);
                else if (")".equals(character)) {
                    String operation = operationStack.pop();

                    while (!"(".equals(operation)) {
                        output.append(operation).append(SPACE_SYMBOL);
                        operation = operationStack.pop();
                    }
                } else {
                    if (!operationStack.isEmpty() && getPriority(character) <= getPriority(operationStack.peek())) {
                        output.append(operationStack.pop()).append(SPACE_SYMBOL);
                    }
                    operationStack.push(character);
                }
            }
        }
        while (!operationStack.isEmpty()) {
            output.append(operationStack.pop()).append(SPACE_SYMBOL);
        }
        return output.toString();
    }


    private byte getPriority(String s) {
        switch (s) {
            case "(":
                return 0;
            case ")":
                return 1;
            case "+":
                return 2;
            case "-":
                return 2;
            case "*":
                return 4;
            case "/":
                return 4;
            case "^":
                return 5;
            default:
                return 6;
        }
    }

    private String toRPNFormat(String rightLine) {
        String resultLine = rightLine.replaceAll(REGEX_CHECK_OPEN_BRACKET_BEFORE, ZERO_MINUS);
        resultLine = resultLine.replaceAll(REGEX_CHECK_NUMBER_BEFORE, PLUS_ZERO_MINUS);
        Pattern pattern = Pattern.compile(REGEX_CHECK_SPACE_BEFORE);
        Matcher matcher = pattern.matcher(resultLine);
        while (matcher.find()) {
            String symbol = matcher.group();
            resultLine = resultLine.replace(symbol, SPACE_SYMBOL + symbol + SPACE_SYMBOL);
        }
        return resultLine;
    }

    private boolean isNumeric(String str) {
        return str.matches(REGEX_NUMERIC_DEFINER);
    }
}
