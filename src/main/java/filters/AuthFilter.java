package filters;

import dao.UserDAO;
import model.User;
import service.UserService;

import javax.servlet.annotation.WebFilter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

@WebFilter(
        urlPatterns = "/",
        filterName = "AuthFilter",
        description = "Filter all admin URLs"
)

public class AuthFilter implements Filter {
    private UserService service = UserService.getInstance();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final String login = req.getParameter("name");
        final String password = req.getParameter("password");


        final HttpSession session = req.getSession();


        //Logged user.
        if (nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"))) {

            final User.ROLE role = (User.ROLE) session.getAttribute("role");

            moveToMenu(req, res, role);


        } else if (service.userIsExist(login, password)) {

            final User.ROLE role = service.getRoleByLoginPassword(login, password);

            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);

            moveToMenu(req, res, role);

        } else {

            moveToMenu(req, res, User.ROLE.UNKNOWN);
        }
    }

    /**
     * Move user to menu.
     * If access 'admin' move to admin menu.
     * If access 'user' move to user menu.
     */
    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final User.ROLE role)
            throws ServletException, IOException {


        if (role.equals(User.ROLE.ADMIN)) {

            req.getRequestDispatcher("/WEB-INF/showUsers.jsp").forward(req, res);

        } else if (role.equals(User.ROLE.USER)) {

            req.getRequestDispatcher("/WEB-INF/userView.jsp").forward(req, res);

        } else {

            req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, res);
        }
    }


    @Override
    public void destroy() {
    }

}

