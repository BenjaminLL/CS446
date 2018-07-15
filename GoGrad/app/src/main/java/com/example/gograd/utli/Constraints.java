package com.example.gograd.utli;

import android.util.Pair;

import java.util.ArrayList;

public class Constraints {

    private Pair<EachConstraints, ArrayList<EachConstraints>> relation;
    private EachConstraints parent;
    private ArrayList<EachConstraints> children;

    public Constraints(String whichPlan, String parentName, String childName) {

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
        if (parent.getIscheck()) {
            for (EachConstraints child : children) {
                child.setIscheck(true);
            }
        } else {
            if (parent.getName().contains("One of")) {
                int i = 0;
                for (EachConstraints child : children) {
                    if (child.getIscheck()) {
                        parent.setIscheck(true);
                        i++;
                    }
                    if (i == 1) {
                        break;
                    }
                }
            } else if (parent.getName().contains("Two of")) {
                int i = 0;
                for (EachConstraints child : children) {
                    if (child.getIscheck()) {
                        parent.setIscheck(true);
                        i++;
                    }
                    if (i == 2) {
                        break;
                    }
                }
            } else if (parent.getName().contains("Three of")) {
                int i = 0;
                for (EachConstraints child : children) {
                    if (child.getIscheck()) {
                        parent.setIscheck(true);
                        i++;
                    }
                    if (i == 3) {
                        break;
                    }
                }
            } else if (parent.getName().contains("Four of")) {
                int i = 0;
                for (EachConstraints child : children) {
                    if (child.getIscheck()) {
                        parent.setIscheck(true);
                        i++;
                    }
                    if (i == 4) {
                        break;
                    }
                }
            } else if (parent.getName().contains("Five of")) {
                int i = 0;
                for (EachConstraints child : children) {
                    if (child.getIscheck()) {
                        parent.setIscheck(true);
                        i++;
                    }
                    if (i == 5) {
                        break;
                    }
                }
            } else if (parent.getName().contains("Six of")) {
                int i = 0;
                for (EachConstraints child : children) {
                    if (child.getIscheck()) {
                        parent.setIscheck(true);
                        i++;
                    }
                    if (i == 6) {
                        break;
                    }
                }
            } else if (parent.getName().contains("Seven of")) {
                int i = 0;
                for (EachConstraints child : children) {
                    if (child.getIscheck()) {
                        parent.setIscheck(true);
                        i++;
                    }
                    if (i == 7) {
                        break;
                    }
                }
            } else if (parent.getName().contains("Eight of")) {
                int i = 0;
                for (EachConstraints child : children) {
                    if (child.getIscheck()) {
                        parent.setIscheck(true);
                        i++;
                    }
                    if (i == 8) {
                        break;
                    }
                }
            } else if (parent.getName().contains("Nine of")) {
                int i = 0;
                for (EachConstraints child : children) {
                    if (child.getIscheck()) {
                        parent.setIscheck(true);
                        i++;
                    }
                    if (i == 9) {
                        break;
                    }
                }
            } else if (parent.getName().contains("All of")) {
                int i = 0;
                for (EachConstraints child : children) {
                    if (child.getIscheck()) {
                        parent.setIscheck(true);
                        i++;
                    }
                    if (i == children.size()) {
                        break;
                    }
                }
            }
        }
    }

}
