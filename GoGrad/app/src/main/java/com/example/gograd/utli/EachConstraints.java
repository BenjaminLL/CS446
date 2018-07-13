package com.example.gograd.utli;

public class EachConstraints {

    private String name;
    private Boolean ischeck;

    public EachConstraints(String name, Boolean ischeck) {
        this.name = name;
        this.ischeck = ischeck;
    }

    public String getName() {
        return name;
    }

    public Boolean getIscheck() {
        return ischeck;
    }

    public void setIscheck() {
        ischeck = !ischeck;

    }



}
