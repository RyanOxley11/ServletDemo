package com.revature.dao;

// this class doesn't seem super useful because we only have one Dao, but with multiple daos, its use becomes apparent
// multiple methods to return different daos:
// we only need 1 dao factory for the entire application, different method to return different daos
public class DaoFactory {
    private static BookDao bookDao;

    // private constructor, intentionally disallow instantiation of this class:
    private DaoFactory() {

    }

    // check if null, return the dao
    public static BookDao getBookDao() {
        if (bookDao == null) {
            // we only ever call this once, we're reusing the same instance:
            bookDao = new BookDaoImpl();
        }
        return bookDao;
    }


}
