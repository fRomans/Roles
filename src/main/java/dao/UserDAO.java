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

     User getUserByName(String name);

     List<User> getAllUsers() throws SQLException;

     User getUserById(long id)throws SQLException;

     void deleteUser(Long id) throws SQLException;

     void updateUser(User user) throws SQLException;

     void addUser(User user) throws SQLException;

      String getRole(final String name, final String password)throws SQLException;

      boolean getUserByNamePass(final String name, final String password)throws SQLException;



}
