package com.example.reseed.objects;

public class TaskObj {
    private String name, description;
    private Integer type;
    private Long dateFinal, dateCreation;

    public TaskObj(String name, String description, Integer type, Long dateFinal, Long dateCreation){
        this.name = name;
        this.description = description;
        this.type = type;
        this.dateCreation = dateCreation;
        this.dateFinal = dateFinal;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public long getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(long dateFinal) {
        this.dateFinal = dateFinal;
    }

    public long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }
}
