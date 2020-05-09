package servlets;

import model.User;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(urlPatterns = "/add", name = "AddServlet")
public class AddServlet extends HttpServlet {

    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/addUser.jsp");
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

