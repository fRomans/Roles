package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBHelper;

import java.util.List;

public class UserHibernateDAO implements UserDAO {

    private static  SessionFactory sessionFactory;
    private static UserHibernateDAO instance;

    private  UserHibernateDAO() {
        if (sessionFactory == null) {
            this.sessionFactory = DBHelper.getConfiguration();
        }

    }

    public static UserHibernateDAO getInstance(){
        if (instance==null){
            instance = new UserHibernateDAO();
        }
        return instance;
    }


    //проверить наличие имени и пароля

    public boolean validateClient(String name, String password) {
        boolean yes = true;
        if (name == null || password == null) {
            yes = false;
        }
        return yes;
    }


    public User getClientByName(String name) {
        String sql = "select u from User u where u.name= :name";

        User user = (User) sessionFactory
                .openSession().createQuery(sql)
                .setParameter("name", name)
                .uniqueResult();

        return user;

    }


    public List<User> getAllUsers() {
        List<User> users = (List<User>) sessionFactory
                .openSession().createQuery("from User").list();
        return users;
    }


    public User getUserById(long id) {
        return (User) sessionFactory.openSession()
                .get(User.class, id);
    }


    public void deleteUser(Long id) {

        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.delete(getUserById(id));
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) tx1.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }


    public void updateUser(User user) {

        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.update(user);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) tx1.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public void addUser(User user) {
//проверить наличие имени и пароля
        if (!validateClient(user.getName(), user.getPassword())) {
            System.out.println("!!! Не прошло валидацию!!!");
            return;
        }
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.save(user);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) tx1.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
