package com.lateroad.informationhandling.handler.impl;

import com.lateroad.informationhandling.handler.IHandler;
import com.lateroad.informationhandling.composite.Component;
import com.lateroad.informationhandling.composite.Text;

public class ParagraphHandler implements IHandler {
    private static final String REGEX_SENTENCE_DELIMETER = "(?<=[.!?])\\s";


    private SentenceHandler handler;

    public ParagraphHandler(SentenceHandler handler) {
        this.handler = handler;
    }

    @Override
    public Component handleRequest(String text) {
        Component component = new Text();
        String[] samples = text.split(REGEX_SENTENCE_DELIMETER);
        for (String sample : samples) {
            if (!sample.isEmpty()) {
                component.add(handler.handleRequest(sample));
            }
        }
        return component;
    }
}
