package dao;

import util.PropertyReader;

public class UserDaoFactory {

    public static UserDAO getRealization() {
        UserDAO userDAO;
        if (PropertyReader.getPropertyValue("factory.properties").getProperty("daotype").equals("hibernate")) {
            userDAO = UserHibernateDAO.getInstance();
        } else  {
            userDAO = UserJDBCDao.getInstance();
        }
        return userDAO ;
    }
}
