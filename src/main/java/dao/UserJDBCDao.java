package dao;

import model.User;
import util.DBHelper;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserJDBCDao implements UserDAO {
    private Connection connection;
private static UserJDBCDao instance;

    private UserJDBCDao() {
     connection = DBHelper.getConnection();
    }

    public static UserJDBCDao getInstance(){
        if (instance==null){
            instance = new UserJDBCDao();
        }
        return instance;
    }

    //проверить наличие имени и пароля
    @Override
    public boolean validateClient(String name, String password) {
        boolean yes = true;
        if (name == null || password == null) {
            yes = false;
        }
        return yes;
    }

    @Override
    public User getClientByName(String name) {
        Statement stmt = null;
        User bankClient = null;
        try {
            stmt = connection.createStatement();

            ResultSet result = stmt.executeQuery("select * from user_db where name='" + name + "'");

            while (result.next()) {
                bankClient = new User(result.getLong(1), result.getString(2),
                        result.getString(3), result.getLong(4));
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankClient;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userslist = new LinkedList<>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            stmt.execute("select * from user_db ");
            ResultSet result = stmt.getResultSet();
            while (result.next()) {
                userslist.add(new User(result.getLong(1), result.getString(2),
                        result.getString(3), result.getLong(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userslist;
    }

    @Override
    public User getClientById(long id) {

        Statement stmt = null;
        User user = null;
        try {
            stmt = connection.createStatement();
            stmt.executeQuery("select * from user_db where id=" + id);
            ResultSet result = stmt.getResultSet();

            while (result.next()) {
                user = new User(result.getLong(1), result.getString(2),
                        result.getString(3), result.getLong(4));
            }
            result.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void deleteUser(Long id)  {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

        stmt.execute("delete  from user_db where id ='" + id + "'");
        stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {

        PreparedStatement stmt = null;
        try {
            stmt = connection
                    .prepareStatement("UPDATE  `user_db` SET name = ?, password = ?, money = ? WHERE id = " + user.getId());

        stmt.setString(1, user.getName());
        stmt.setString(2, user.getPassword());
        stmt.setLong(3, user.getMoney());
        stmt.executeUpdate();
        stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user)  {
//проверить наличие имени и пароля
        if (!validateClient(user.getName(), user.getPassword())) {
            System.out.println("!!! Не прошло валидацию!!!");
            return;
        }
       try {

        PreparedStatement stmt = connection
                .prepareStatement("insert into  `user_db`(name, password, money) values(?,?,?)");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setLong(3, user.getMoney());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createTable()  {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

        stmt.execute("create table if not exists user_db (id bigint auto_increment," +
                " name varchar(256), password varchar(256), money bigint, primary key (id))");
        stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void dropTable() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

        stmt.executeUpdate("DROP TABLE IF EXISTS user_db");
        stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}