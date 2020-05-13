package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public interface UserDAO {

     boolean validateClient(String name, String password);

     User getClientByName(String name);

     List<User> getAllUsers() ;

     User getUserById(long id);

     void deleteUser(Long id) throws SQLException;

     void updateUser(User user) throws SQLException;

     void addUser(User user) throws SQLException;



}
