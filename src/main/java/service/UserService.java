package service;

import dao.UserDAO;
import dao.UserDaoFactory;
import dao.UserHibernateDAO;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private static UserService userService;

    private UserDAO dao ;

    private UserService() {

        this.dao = UserDaoFactory.getRealization("hibernateConn.properties");//todo получать dao из фабрики
    }

    private UserService(UserDAO dao) {

        this.dao = dao;//todo получать dao из фабрики
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

    public User getUserById(long id) {
        User user = dao.getClientById(id);
        return user;
    }

    public void addUser(User user) throws SQLException {

        dao.addUser(user);

    }

}
