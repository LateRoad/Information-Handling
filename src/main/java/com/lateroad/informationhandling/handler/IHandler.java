package com.lateroad.informationhandling.handler;

import com.lateroad.informationhandling.composite.Component;
import org.apache.log4j.Logger;

public interface IHandler {
    Component handleRequest(String text);
}
