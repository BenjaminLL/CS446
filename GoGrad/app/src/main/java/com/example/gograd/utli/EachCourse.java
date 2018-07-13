package com.example.gograd.utli;

import com.example.gograd.ChecklistOpenHelper;

public class EachCourse {

    private String name;
    private Boolean ischeck;
    private String whichPlan;
    private ChecklistOpenHelper checklistOpenHelper;

    public EachCourse(String whichPlan, String name, boolean ischeck) {
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

    public Boolean changeIscheck() {
        ischeck = !ischeck;
        checklistOpenHelper.updateUserTable_Status(whichPlan, name, ischeck);
        return ischeck;
    }

}
