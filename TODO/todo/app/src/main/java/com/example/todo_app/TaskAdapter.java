package com.example.todo_app;

import java.io.Serializable;

public class TaskAdapter implements Serializable {
    private String description, heading, taskdateot, taskdatedo, whom, author;//тестові змінні для передачі даних з бази даних в потрібний клас
    private Boolean taskdone;//логічна змінна для передачі даних з бази даних в потрібний клас

    public TaskAdapter() {
    }

    public TaskAdapter(String heading, String description, String taskdateot, String taskdatedo, String whom, Boolean taskdone, String author) {
        this.heading = heading;
        this.description = description;
        this.taskdateot = taskdateot;
        this.taskdatedo = taskdatedo;
        this.whom = whom;
        this.taskdone = taskdone;
        this.author = author;
    }
    //Метод для отримання опису
    public String getDescription() {
        return description;
    }
    //Метод для задання опису
    public void setDescription(String description) {
        this.description = description;
    }
    //Метод для отримання заголовку
    public String getHeading() {
        return heading;
    }
    //Метод для задання заголовку
    public void setHeading(String heading) {
        this.heading = heading;
    }
    //Метод для отримання дати створення
    public String getTaskdateot() {
        return taskdateot;
    }
    //Метод для задання дати створення
    public void setTaskdateot(String taskdateot) {
        this.taskdateot = taskdateot;
    }
    //Метод для отримання дати здачі
    public String getTaskdatedo() {return taskdatedo;}
    //Метод для задання дати здачі
    public void setTaskdatedo(String taskdatedo) {this.taskdatedo = taskdatedo;}
    //Метод для отримання кому надсилається завдання
    public String getWhom() {return whom;}
    //Метод для задання кому надсилається завдання
    public void setWhom(String whom) {this.whom = whom;}
    //Метод для отримання стану виконання
    public Boolean getTaskdone() {
        return taskdone;
    }
    //Метод для задання стану виконання
    public void setTaskdone(Boolean taskdone) {
        this.taskdone = taskdone;
    }
    //Метод для отримання автору завдання
    public String getAuthor() {return author;}
    //Метод для задання автору завдання
    public void setAuthor(String author) {this.author = author;}
}
