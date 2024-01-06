package dao;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> extends  SuperDao{
     boolean save(T entity);
     List<T> getAll() throws SQLException, ClassNotFoundException;
     boolean update(T entity);
     boolean delete(String data);
}
