package com.example.gograd.utli;
import com.example.gograd.ChecklistOpenHelper;

public class EachConstraints {

    private String whichPlan;
    private String name;
    private Boolean ischeck;
    private Boolean parOrChild;
    private ChecklistOpenHelper checklistOpenHelper;

    public EachConstraints(String whichPlan, String name, Boolean ischeck, Boolean parOrChild) {
        this.whichPlan = whichPlan;
        this.name = name;
        this.ischeck = ischeck;
        this.parOrChild = parOrChild;
    }

    public String getName() {
        return name;
    }

    public Boolean getIscheck() {
        return ischeck;
    }

    public Boolean changeIscheck() {
        if (parOrChild) {
            //if ()
        }
        ischeck = !ischeck;
        checklistOpenHelper.updateUserTable_Status(whichPlan, name, ischeck);
        return ischeck;
    }

    public void setIscheck(Boolean ischeck) {
        this.ischeck = ischeck;
    }
}
