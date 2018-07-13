package com.example.gograd.utli;
import com.example.gograd.ChecklistOpenHelper;

public class EachConstraints {

    private String whichPlan;
    private String name;
    private Boolean ischeck;
    private ChecklistOpenHelper checklistOpenHelper;

    public EachConstraints(String whichPlan, String name, Boolean ischeck) {
        this.whichPlan = whichPlan;
        this.name = name;
        this.ischeck = ischeck;
    }

    public String getName() {
        return name;
    }

    public Boolean getIscheck() {
        return ischeck;
    }

    public Boolean setIscheck() {
        ischeck = !ischeck;
        checklistOpenHelper.updateUserTable_Status(whichPlan, name, ischeck);
        return ischeck;
    }



}
