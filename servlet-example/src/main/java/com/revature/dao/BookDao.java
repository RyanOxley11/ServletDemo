package com.revature.dao;

import com.revature.entity.Book;
import com.revature.entity.Ticket;

import java.util.List;

// this interface will contain the methods that we use to access the book database
public interface BookDao {
    // Methods that interact with the database (CRUD - Create, Read, Update, Delete)
    public void insert(Book book);
    public void insertStoredProcedure(Book book);
    public Book getBookById(int id);
    public List<Book> getAllBooks();
    public void update(Book book);
    public boolean delete(int id);
    public void initTables();
    public void fillTables();
    // tickets:
    public void insertTicket(Ticket ticket);
}
