package com.vamshi.databases.dto;

import com.vamshi.databases.enums.Datatype;

public class Column {
    public String name;
    public Datatype dataType;
    public boolean required;
    public Column(String name, Datatype dataType, boolean required){
        this.name = name;
        this.dataType = dataType;
        this.required= required;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Datatype getDataType() {
        return dataType;
    }

    public void setDataType(Datatype dataType) {
        this.dataType = dataType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
