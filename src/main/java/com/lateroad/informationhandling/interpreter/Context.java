package com.lateroad.informationhandling.interpreter;

import java.util.ArrayDeque;

public class Context {
    private ArrayDeque<Double> values = new ArrayDeque<>();

    public double pop() {
        return values.pop();
    }

    public void push(double value) {
        values.push(value);
    }

    @Override
    public String toString() {
        return "Context{" +
                "values=" + values +
                '}';
    }
}
