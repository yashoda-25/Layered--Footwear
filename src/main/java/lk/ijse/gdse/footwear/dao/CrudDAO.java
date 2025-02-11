package lk.ijse.gdse.footwear.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,ID> extends SuperDAO {
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    boolean save(T entity) throws SQLException, ClassNotFoundException;
    boolean update(T entity) throws SQLException, ClassNotFoundException;
    boolean delete(ID id) throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException, ClassNotFoundException;
   // boolean exist(ID id) throws SQLException, ClassNotFoundException;
 //   T search(T dto) throws SQLException, ClassNotFoundException;

}
