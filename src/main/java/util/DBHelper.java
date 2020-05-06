package util;

import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {


    @SuppressWarnings("UnusedDeclaration")
    public static SessionFactory getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);//Read metadata from the annotations associated with this class.

        Properties properties = PropertyReader.getPropertyValue("hibernateConn.properties");
        configuration.setProperty("hibernate.dialect", properties.getProperty("daotype.hibernate.dialect"));
        configuration.setProperty("hibernate.connection.driver_class", properties.getProperty("daotype.hibernate.connection.driver_class"));
        configuration.setProperty("hibernate.connection.url", properties.getProperty("daotype.hibernate.connection.url"));
        configuration.setProperty("hibernate.connection.username", properties.getProperty("daotype.hibernate.connection.username"));
        configuration.setProperty("hibernate.connection.password", properties.getProperty("daotype.hibernate.connection.password"));
        configuration.setProperty("hibernate.show_sql", properties.getProperty("daotype.hibernate.show_sql"));
        configuration.setProperty("hibernate.hbm2ddl.auto", properties.getProperty("daotype.hibernate.hbm2ddl.auto"));
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        configuration.buildSessionFactory(serviceRegistry);
        return configuration.buildSessionFactory(serviceRegistry);

    }


    public static Connection getConnection() {
        Properties properties = PropertyReader.getPropertyValue("jdbcConn.properties");
        try {
            DriverManager.registerDriver((Driver) Class.forName(properties.getProperty("daotype.drivermanager")).newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append(properties.getProperty("daotype.type")).        //db type
                    append(properties.getProperty("daotype.hostname")).           //host name
                    append(properties.getProperty("daotype.port")).                //port
                    append(properties.getProperty("daotype.dbname")).          //db name
                    append(properties.getProperty("daotype.login")).          //login
                    append(properties.getProperty("daotype.password")).       //password
                    append(properties.getProperty("daotype.setupservertime"));   //setup server time
            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}


