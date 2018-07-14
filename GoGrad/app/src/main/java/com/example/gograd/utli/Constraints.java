package com.example.gograd.utli;

import android.util.Pair;

import java.util.ArrayList;

public class Constraints {

    private Pair<EachConstraints, ArrayList<EachConstraints>> relation;
    private EachConstraints parent;
    private ArrayList<EachConstraints> children;

    public Constraints(EachConstraints parent, ArrayList<EachConstraints> children) {

        this.parent = parent;
        this.children = children;
        relation = new Pair<>(this.parent, this.children);
    }

    public EachConstraints getParent() {
        return parent;
    }

    public ArrayList<EachConstraints> getChildren() {
        return children;
    }

    public void changeState() {
        
    }

}
