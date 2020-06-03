package filters;

import model.User;
import service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/user",
        filterName = "UserFilter"
)
public class UserFilter implements Filter {

    private UserService service = UserService.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        User user = (User) req.getSession().getAttribute("user");
        final String login = user.getName();
        final String password = user.getPassword();
        final String role = user.getRole();


        if (service.userIsExist(login, password) & role.equals("user")) {
            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/user");
            dispatcher.forward(req, resp);
            return;
        } else {
            req.setAttribute("nodata", "ошибка доступа к user");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/noaccess");
            dispatcher.forward(req, resp);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
