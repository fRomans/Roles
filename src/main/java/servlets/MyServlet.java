package servlets;

import model.User;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/users", name = "MyServlet")
public class MyServlet extends HttpServlet {

    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<User> users = null;
        try {

            users = service.getAllUsers();
            //  throw new SQLException("ffffff") ;

        } catch (SQLException e) {
            req.setAttribute("SQLException", "SQL запрос не выполнен");
            e.printStackTrace();
        }
        req.setAttribute("users", users);
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/showUsers.jsp");
        dispatcher.forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Long money = 0L;
        try {
            money = new Long(req.getParameter("money"));
           // throw new NumberFormatException("rrrr");
        } catch (NumberFormatException e) {
            req.setAttribute("NumberFormatException", "Ошибка формата числа");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/addUser.jsp");
            dispatcher.forward(req, resp);
            System.out.println("Ошибка формата числа -" + e);

        }
        User user = new User(name, password, money);

        try {
            service.addUser(user);
        //  throw new SQLException("eeee");

        } catch (SQLException e) {
            req.setAttribute("AddUserException", "Ошибка SQL при добавлении user");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/addUser.jsp");
            dispatcher.forward(req, resp);
            e.printStackTrace();
        }
        resp.sendRedirect("/users");
    }

}
