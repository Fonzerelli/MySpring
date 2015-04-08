package com.myspring.ivashyn.db.exception;

/**
 * Created by: Dima Ivashyn
 * Date: 17.03.15
 * Time: 11:26
 */
public class DaoException extends Exception {

    private static final long serialVersionUID = 1L;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}

