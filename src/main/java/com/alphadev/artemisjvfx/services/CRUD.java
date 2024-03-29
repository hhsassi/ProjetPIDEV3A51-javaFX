package com.alphadev.artemisjvfx.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    void add(T t) throws SQLException;
    void delete(T t) throws SQLException;
    void update(T t) throws SQLException;
   ResultSet selectAll() throws SQLException;
}
