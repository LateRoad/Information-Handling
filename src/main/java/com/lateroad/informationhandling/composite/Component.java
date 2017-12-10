package com.lateroad.informationhandling.composite;


public abstract class Component {

    public abstract String getValue();

    public abstract void add(Component component);

    public abstract void remove(Component component);

    public abstract Component getChild(int index);

    public abstract void setChild(int index, Component newValue);

    public abstract int childrenCount();

}
