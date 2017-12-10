package com.lateroad.informationhandling.composite;

import java.util.ArrayList;

public class Text extends Component {
    private ArrayList<Component> components = new ArrayList<>();


    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        for (Component element : components) {
            line.append(element.toString());
        }
        return line.toString();
    }


    @Override
    public String getValue() {
        StringBuilder value = new StringBuilder();
        for (Component component : components) {
            value.append(component.getValue());
        }
        return value.toString();
    }

    @Override
    public void add(Component component) {
        components.add(component);
    }

    @Override
    public void remove(Component component) {
        components.remove(component);
    }

    @Override
    public Component getChild(int index) {
        return components.get(index);
    }

    @Override
    public void setChild(int index, Component newValue) {
        components.set(index, newValue);
    }

    @Override
    public int childrenCount() {
        return components.size();
    }
}
