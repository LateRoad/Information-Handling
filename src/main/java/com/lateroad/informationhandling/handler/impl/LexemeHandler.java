package com.lateroad.informationhandling.handler.impl;

import com.lateroad.informationhandling.handler.IHandler;
import com.lateroad.informationhandling.composite.Component;
import com.lateroad.informationhandling.composite.Lexeme;

public class LexemeHandler implements IHandler {

    @Override
    public Component handleRequest(String text) {
        return new Lexeme(text);
    }
}
