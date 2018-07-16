package com.example.gograd.utli.constraints;

import android.util.Pair;

import com.example.gograd.ChecklistOpenHelper;

import java.util.ArrayList;

public class Constraints {

    private Pair<EachConstraintsParent, ArrayList<EachConstraintsChild>> relation;

    public Constraints(String whichPlan, String parentName, boolean isChecked, int limitation, int current,
                       ArrayList<Pair<String, Boolean>> children, ChecklistOpenHelper checklistOpenHelper) {

        EachConstraintsParent eachConstraintsParent = new EachConstraintsParent(whichPlan, parentName, isChecked, limitation, current, checklistOpenHelper);
        ArrayList<EachConstraintsChild> eachConstraintsChildren = new ArrayList<>();

        for (Pair<String, Boolean> nameCheck : children) {
            EachConstraintsChild eachConstraintsChild = new EachConstraintsChild(whichPlan, nameCheck.first, nameCheck.second, checklistOpenHelper);
            eachConstraintsParent.addObserver(eachConstraintsChild);
            eachConstraintsChild.addObserver(eachConstraintsParent);
            eachConstraintsChildren.add(eachConstraintsChild);
        }
        relation = new Pair<>(eachConstraintsParent, eachConstraintsChildren);
    }

    public Pair<EachConstraintsParent, ArrayList<EachConstraintsChild>> getRelation() {
        return relation;
    }

    public EachConstraints findConstraint(String name) {
        if (relation.first.getName().equals(name)) {
            return relation.first;
        } else {
            for (EachConstraintsChild eachConstraintsChild : relation.second) {
                if (eachConstraintsChild.getName().equals(name)) {
                    return eachConstraintsChild;
                }
            }
        }
        return null;
    }

}
