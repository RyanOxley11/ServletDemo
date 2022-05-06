package com.revature.dao;

import com.revature.entity.Book;
import com.revature.entity.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// implementation of the book dao methods
public class BookDaoImpl implements BookDao{

    // connection to the database:
    Connection connection;

    public BookDaoImpl() {
        // when we instantiate this class, we get the connection
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void insert(Book book) {
        // question marks are placeholders for the real values:
        String sql = "insert into book (name, author, description, year) values (?, ?, ?, ?);";

        try {
            // if anything goes wrong here, we will catch the exception:

            // we use our connection to prepare a statement to send to the database, pass in the string that we made, as well as a flag
            // that returns the generated keys (id)
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            // fill in the values with the data from our book object:
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4, book.getYear());
            // now that our statement is prepared, we can execute it:
            // count is how many rows are affected (optimally we would have 1, we are inserting a single book)
            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                System.out.println("Book added successfully!");
                // first, we get the result set
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                // increment to the first element of the result set
                resultSet.next();
                // extract the id from the result set
                int id = resultSet.getInt(1);
                // set the id of the book, update the object:
                book.setId(id);
                System.out.println("Generated id is: " + id);
            }
            else {
                System.out.println("Something went wrong when adding the book!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // stored procedure insert
    @Override
    public void insertStoredProcedure(Book book) {
        // question marks are placeholders for the real values:
        String sql = "call insert_book(?::varchar, ?::varchar, ?::varchar, ?::integer, null);";

        try {
            // if anything goes wrong here, we will catch the exception:

            // we use our connection to prepare a statement to send to the database, pass in the string that we made, as well as a flag
            // that returns the generated keys (id)
            PreparedStatement preparedStatement = connection.prepareStatement(sql );
            // fill in the values with the data from our book object:
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4, book.getYear());
            // now that our statement is prepared, we can execute it:
            // return 0 if success, 1 otherwise:
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                System.out.println("Book added successfully!");
                // extract the id from the result set
                int id = resultSet.getInt(1);
                // set the id of the book, update the object:
                book.setId(id);
                System.out.println("Generated id is: " + id);
            }
            else {
                System.out.println("Something went wrong when adding the book!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book getBookById(int id) {
        String sql = "select * from book where id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // set the id using the id that we passed in:
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            // checking, do we have a book from this query
            if (resultSet.next()) {
                // extract out the data
                Book book = getBook(resultSet);
                // probably don't need this conditional:
//                if(idData != id) {
//                    System.out.println("Something went wrong here. Id's don't match!");
//                    return null;
//                }
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        // create a list of books to store our results:
        List<Book> books = new ArrayList<>();
        String sql = "select * from book;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // we don't need to set any parameters because we're getting all books:
            ResultSet resultSet = preparedStatement.executeQuery();
            // we use a while loop because there are multiple results:
            while(resultSet.next()) {
                Book book = getBook(resultSet);
                // add book to list of books
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // get book from a result set:
    public Book getBook(ResultSet resultSet) {
        try {
            int idData = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            String description = resultSet.getString("description");
            int year = resultSet.getInt("year");
            return new Book(idData, name, author, description, year);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Book book) {
        String sql = "update book set name = ?, author = ?, description = ?, year = ? where id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4, book.getYear());
            preparedStatement.setInt(5, book.getId());
            int count = preparedStatement.executeUpdate();
            if(count == 1) System.out.println("Update successful!");
            else System.out.println("Something went wrong with the update!");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // changed this method to return a boolean of whether the deletion is successful:
    @Override
    public boolean delete(int id){
        String sql = "delete from book where id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                System.out.println("Deletion successful!");
                return true;
            }
            else {
                System.out.println("Something went wrong with the deletion!");
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;



    }

    // we need this method if we're using an h2 database, keep in mind that our database gets "reset" every time, we run the program
    @Override
    public void initTables() {
        // we don't see any ? placeholders because this statement will be the same every time
        String sql = "DROP TABLE book IF EXISTS; CREATE TABLE book(id SERIAL PRIMARY KEY, name VARCHAR(50), author VARCHAR(50), description VARCHAR(250), year INTEGER);";

        // we could add a procedure as well as so we can test it with h2
        try {
            // creating a statement instead of preparinf it
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fillTables() {
        String sql = "insert into book(id, name, author, description, year) values (default, 'book 1', 'author 1', 'description 1', 1);\n";
        sql += "insert into book(id, name, author, description, year) values (default, 'book 2', 'author 2', 'description 2', 2);\n";
        sql += "insert into book(id, name, author, description, year) values (default, 'book 3', 'author 3', 'description 3', 3);";

        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // UNRELATED TO BOOKS:
    @Override
    public void insertTicket(Ticket ticket) {
        String sql = "insert into ticket (name, submitted_date) values (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ticket.getName());
            preparedStatement.setTimestamp(2, ticket.getSubmitted_date());
            int count = preparedStatement.executeUpdate();
            if(count != 1) {
                System.out.println("Something went wrong when trying to insert the ticket!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
