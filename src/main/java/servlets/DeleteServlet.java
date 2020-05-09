package servlets;

import model.User;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/users/delete", name = "DeleteServlet")
public class DeleteServlet extends HttpServlet {

    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = service.getUserById(new Long(req.getParameter("id")));
        req.setAttribute("user", user);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/deleteUser.jsp");
        dispatcher.forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            service.deleteUser(service.getUserById(id));
            //throw new SQLException("eeee");

        } catch (SQLException | NullPointerException e) {
            req.setAttribute("DeleteUserException", "Ошибка SQL/NullPointer при удалении user");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/deleteUser.jsp");
            dispatcher.forward(req, resp);
            e.printStackTrace();

        }
        resp.sendRedirect("/users");

    }

}


