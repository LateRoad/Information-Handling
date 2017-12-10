package com.lateroad.informationhandling.composite;

public class Lexeme extends Component {

    private String value = "";

    public Lexeme(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return " " + value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Component getChild(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setChild(int index, Component newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int childrenCount() {
        return 1;
    }
}
