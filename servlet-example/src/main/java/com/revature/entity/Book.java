package com.revature.entity;

// Book is an entity, it represents a "physical" object
public class Book {
    private int id;
    private String name;
    private String author;
    private String description;
    private int year;

    // NOTE: I added this empty constructor so Jackson could properly parse the JSON request:
    public Book() {

    }

    public Book(int id, String name, String author, String description, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.year = year;
    }

    // we need a constructor that doesn't take an id, because when we're creating a book, we won't know what the id is:
    public Book(String name, String author, String description, int year) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                '}';
    }
}
