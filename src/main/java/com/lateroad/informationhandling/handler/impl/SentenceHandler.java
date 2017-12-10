package com.lateroad.informationhandling.handler.impl;

import com.lateroad.informationhandling.handler.IHandler;
import com.lateroad.informationhandling.composite.Component;
import com.lateroad.informationhandling.composite.Text;

public class SentenceHandler implements IHandler {
    private static final String REGEX_LEXEME_DELIMETER = "\\p{Blank}+";


    private LexemeHandler handler;

    public SentenceHandler(LexemeHandler handler) {
        this.handler = handler;
    }

    @Override
    public Component handleRequest(String text) {
        Component component = new Text();
        String[] samples = text.split(REGEX_LEXEME_DELIMETER);
        for (String sample : samples) {
            if (!sample.isEmpty()) {
                component.add(handler.handleRequest(sample));
            }
        }
        return component;
    }
}
