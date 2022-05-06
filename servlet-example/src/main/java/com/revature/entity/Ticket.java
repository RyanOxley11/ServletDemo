package com.revature.entity;


import java.sql.Timestamp;

public class Ticket {
    private String name;
    private Timestamp submitted_date;

    public Ticket(String name, Timestamp submitted_date) {
        this.name = name;
        this.submitted_date = submitted_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getSubmitted_date() {
        return submitted_date;
    }

    public void setSubmitted_date(Timestamp submitted_date) {
        this.submitted_date = submitted_date;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "name='" + name + '\'' +
                ", submitted_date=" + submitted_date +
                '}';
    }
}
