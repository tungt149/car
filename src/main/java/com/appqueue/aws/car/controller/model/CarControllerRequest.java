package com.appqueue.aws.car.controller.model;

public class CarControllerRequest {
    private String id;
    private String name;
    private String type;
    private String dml_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDml_date() {
        return dml_date;
    }

    public void setDml_date(String dml_date) {
        this.dml_date = dml_date;
    }
}
