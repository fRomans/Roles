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

    public static UserJDBCDao getInstance() {
        if (instance == null) {
            instance = new UserJDBCDao();
        }
        return instance;
    }

    //проверить наличие имени и пароля
    @Override
    public boolean validateClient(String name, String password) {
        boolean availability = true;// проверка НАЛИЧИЯ name/password
        if (name == null || password == null) {
            availability = false;
        }
        return availability;
    }

    @Override
    public User getUserByName(String name) {
        PreparedStatement stmt;
        User bankClient = null;
        try {
            stmt = connection
                    .prepareStatement("select * from user_db where name= ?");
            stmt.setString(1, name);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                bankClient = new User(result.getLong(1), result.getString(2),
                        result.getString(3), result.getLong(4),
                        result.getString(5));
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankClient;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> userslist = new LinkedList<>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            stmt.execute("select * from user_db ");
            ResultSet result = stmt.getResultSet();
            while (result.next()) {
                userslist.add(new User(result.getLong(1), result.getString(2),
                        result.getString(3), result.getLong(4),
                        result.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            stmt.close();
        }
        return userslist;
    }

    @Override
    public User getUserById(long id) throws SQLException {

        PreparedStatement stmt = null;
        User user = null;
        ResultSet result = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection
                    .prepareStatement("select * from user_db where id = ?");
            stmt.setLong(1, id);
            stmt.executeQuery();
            connection.commit();
            result = stmt.getResultSet();

            while (result.next()) {
                user = new User(result.getLong(1), result.getString(2),
                        result.getString(3), result.getLong(4),
                        result.getString(5));
            }

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            result.close();
            stmt.close();
        }
        return user;
    }

    @Override
    public void deleteUser(Long id) throws SQLException {
        PreparedStatement stmt = null;

        try {
            connection.setAutoCommit(false);
            stmt = connection
                    .prepareStatement("delete  from user_db where id = ?");
            stmt.setLong(1, id);
            stmt.execute();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }

    @Override
    public String getRole(final String nameUs, final String passwordUs) throws SQLException {

        String role = null;
        Statement stmt = null;
        ResultSet result = null;
        String sql = "select * from user_db  where name = " + nameUs + " and password =" + passwordUs;
        try {
            stmt = connection.createStatement();
            stmt.execute(sql);
            result = stmt.getResultSet();
            while (result.next()) {
                role = result.getString("role");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            result.close();
            stmt.close();
        }
        return role;
    }

    @Override
    public boolean getUserByNamePass(String nameUs, String passwordUs) throws SQLException {
        User user = null;
        Statement stmt = null;
        ResultSet result = null;
        boolean exist = false;
        String sql = "select * from user_db  where name = " + nameUs + " and password =" + passwordUs;
        try {
            stmt = connection.createStatement();
            stmt.execute(sql);
            result = stmt.getResultSet();
            while (result.next()) {
                user = new User(result.getLong(1), result.getString(2),
                        result.getString(3), result.getLong(4),
                        result.getString(5));
                break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            result.close();
            stmt.close();
        }
        if (user != null) {
            exist = true;
        }
        return exist;
    }

    @Override
    public void updateUser(User user) throws SQLException {

        PreparedStatement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection
                    .prepareStatement("UPDATE  `user_db` SET name = ?, password = ?, money = ?, role = ? WHERE id = " + user.getId());

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setLong(3, user.getMoney());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }

    @Override
    public void addUser(User user) throws SQLException {
//проверить наличие имени и пароля
        if (!validateClient(user.getName(), user.getPassword())) {
            System.out.println("!!! Не прошло валидацию!!!");
            return;
        }
        PreparedStatement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection
                    .prepareStatement("insert into  `user_db`(name, password, money,role) values(?,?,?,?)");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setLong(3, user.getMoney());
            stmt.setString(4, user.getRole().toString());
            stmt.executeUpdate();
            connection.commit();

        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }


    public void createTable() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            stmt.execute("create table if not exists user_db (id bigint auto_increment," +
                    " name varchar(256), password varchar(256), money bigint,role varchar(45), primary key (id))");
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