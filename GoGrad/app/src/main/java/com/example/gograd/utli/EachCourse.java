package com.example.gograd.utli;

public class EachCourse {

    private String name;
    private Boolean ischeck;

    public EachCourse(String name, boolean ischeck) {
        this.name = name;
        this.ischeck = ischeck;
    }

    public String getName() {
        return name;
    }

    public Boolean getIscheck() {
        return ischeck;
    }

    public void setIscheck(Boolean ischeck) {
        this.ischeck = ischeck;
    }

}
