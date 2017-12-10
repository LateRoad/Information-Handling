package com.lateroad.informationhandling.collection;

import java.util.HashMap;
import java.util.Map;

public class VariableMap {
    private static final VariableMap instance = new VariableMap();
    private Map<String, Integer> map;


    private VariableMap() {
        map = new HashMap<>();
    }

    public static VariableMap getInstance() {
        return instance;
    }

    public void add(String variable, int value) {
        map.put(variable, value);
    }

    public Integer get(String variable) {
        return map.get(variable);
    }

    public void put(String variable, Integer value) {
        map.put(variable, value);
    }

    public boolean remove(String variable) {
        if (map.containsKey(variable)) {
            map.remove(variable);
            return true;
        }
        return false;
    }
}
