package com.example.gograd.util.constraints;

import android.util.Pair;

import com.example.gograd.database.ChecklistOpenHelper;

import java.util.ArrayList;

public class Constraints {

    private Boolean parOrChild = true;

    private Pair<EachConstraintsParent, ArrayList<EachConstraintsChild>> relation;

    public Constraints(String whichPlan, String parentName, Boolean isChecked, int limitation, int current,
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

    public Boolean getParOrChild() {
        return parOrChild;
    }

    public EachConstraints findConstraint(String name) {
        if (relation.first.getName().equals(name)) {
            parOrChild = true;
            return relation.first;
        } else {
            for (EachConstraintsChild eachConstraintsChild : relation.second) {
                if (eachConstraintsChild.getName().equals(name)) {
                    parOrChild = false;
                    return eachConstraintsChild;
                }
            }
        }
        return null;
    }

}
