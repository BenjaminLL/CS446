package com.example.gograd.utli;

import com.example.gograd.ChecklistOpenHelper;

public class EachCourse {

    private String name;
    private Boolean ischeck;
    private String whichPlan;
    private Boolean isOrigin;
    private ChecklistOpenHelper checklistOpenHelper;

    public EachCourse(String whichPlan, String name, Boolean ischeck, Boolean isOrigin, ChecklistOpenHelper cloh) {
        this.whichPlan = whichPlan;
        this.name = name;
        this.ischeck = ischeck;
        this.isOrigin = isOrigin;
        this.checklistOpenHelper = cloh;
    }

    public String getName() {
        return name;
    }

    public Boolean getIscheck() {
        return ischeck;
    }

    public void setIsOrigin(Boolean isOrigin) {
        this.isOrigin = isOrigin;
    }

    public Boolean getIsOrigin() {
        return isOrigin;
    }

    public Boolean changeIscheck() {
        ischeck = !ischeck;
        checklistOpenHelper.updateUserTable_Status(whichPlan, name, ischeck);
        return ischeck;
    }

    public void setIscheck(Boolean ischeck) {
        this.ischeck = ischeck;
        checklistOpenHelper.updateUserTable_Status(whichPlan, name, ischeck);
    }
}
