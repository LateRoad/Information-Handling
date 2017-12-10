package com.lateroad.informationhandling.interpreter;

import org.apache.log4j.Logger;

public class Calculator {
    private static final Logger LOGGER = Logger.getLogger(Calculator.class);

    public static final String REGEX_SPACES = "\\p{Blank}+";


    public int calculate(String expression) {
        LOGGER.info("Starting calculation polish notation expression.");
        Context context = new Context();
        for (String item : expression.split(REGEX_SPACES)) {
            switch (item) {
                case "+":
                    interpret(context, i ->
                            context.push(context.pop() + context.pop()));
                    break;
                case "-":
                    interpret(context, i -> {
                        double value2 = context.pop();
                        double value1 = context.pop();
                        context.push(value1 - value2);
                    });
                    break;
                case "*":
                    interpret(context, i ->
                            context.push(context.pop() * context.pop()));
                    break;
                case "/":
                    interpret(context, i -> {
                        double value2 = context.pop();
                        double value1 = context.pop();
                        context.push(value1 / value2);
                    });
                    break;
                default:
                    interpret(context, i ->
                            context.push(Double.parseDouble(item)));
                    break;
            }
        }
        return (int)context.pop();
    }

    private static void interpret(Context context, MathInterpreter interpreter) {
        interpreter.doInterpret(context);
    }
}
