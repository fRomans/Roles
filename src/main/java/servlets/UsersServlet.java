package servlets;

import model.User;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/admin", name = "MyServlet")
public class UsersServlet extends HttpServlet {

    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // final String login = req.getParameter("name");
//        final String password = req.getParameter("password");
//        final String role = service.getRoleByLoginPassword(login, password);
//        User user = new User(login, password, role);
//        req.getSession().setAttribute("user", user);
        // req.getSession().setAttribute("login", login);


        //   req.getSession().setAttribute("role", role);

        List<User> users = null;
        try {

            users = service.getAllUsers();
            //  throw new SQLException("ffffff") ;
            req.setAttribute("users", users);

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
        final HttpSession session = req.getSession();
        final String login = req.getParameter("name");
        final String password = req.getParameter("password");
        try {
            final String role = service.getRoleByLoginPassword(login, password);
            User user = new User(login, password, role);
            session.setAttribute("user", user);
            resp.sendRedirect("/admin");
        } catch (NullPointerException e) {

            System.out.println("Без роли нельзя  - (UserServlet)" + e);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/noaccess");
            dispatcher.forward(req, resp);
        }

//            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin");
//            dispatcher.forward(req, resp);


    }
}


