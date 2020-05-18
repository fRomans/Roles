package service;

import dao.UserDAO;
import dao.UserDaoFactory;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private static UserService userService;

    private UserDAO dao ;

    private UserService() {

        this.dao = UserDaoFactory.getRealization();
    }

    private UserService(UserDAO dao) {
        this.dao = dao;
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }



    public List<User> getAllUsers() throws SQLException {
        return dao.getAllUsers();
    }

    public void deleteUser(User user) throws SQLException {
        dao.deleteUser(user.getId());

    }

    public void updateUser(User user) throws SQLException {
        dao.updateUser(user);
    }

    public User getUserById(long id)  {
        User user = null;
        try {
             user = dao.getUserById(id);        } catch (SQLException e) {
            System.out.println("UserService, метод getUserById" + e );
        }

        return user;
    }

    public void addUser(User user) throws SQLException {

        dao.addUser(user);

    }

    public String getRoleByLoginPassword(final String name, final String password)  {
        String role = null;
        try {
            role = dao.getRole(name,password);
        } catch (SQLException e) {
            System.out.println("UserService, метод getRoleByLoginPassword" + e );
        }
        return role;
    }

    public boolean userIsExist(final String name, final String password)  {
        boolean exist = false;
        try {
            exist = dao.getUserByNamePass(name,password);
        } catch (SQLException e) {
            System.out.println("UserService, метод userIsExist" + e );
        }
        return exist;
    }


}
