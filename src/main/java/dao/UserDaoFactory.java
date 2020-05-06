package dao;

import util.DBHelper;
import util.PropertyReader;

public class UserDaoFactory {

    public static UserDAO getRealization(String fileName) {
        UserDAO userDAO = null;
        if (PropertyReader.getPropertyValue(fileName).getProperty("daotype").equals("hibernate")) {
            userDAO = UserHibernateDAO.getInstance();
        } else if (PropertyReader.getPropertyValue(fileName).getProperty("daotype").equals("jdbc")) {
            userDAO = UserJDBCDao.getInstance();
        }
        return userDAO ;
    }
}
