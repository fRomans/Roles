package filters;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(
        urlPatterns = "/admin/update",
        filterName = "UpdateFilter"
)
public class UpdateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession(false);
        User user = (User) req.getSession().getAttribute("user");
        if (user==null){
            req.setAttribute("nodata", "ошибка доступа к обновлению");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/noaccess");
            dispatcher.forward(req, resp);
        }
        final String login = user.getName();
        final String password = user.getPassword();
        final String role = user.getRole();



        if (session == null || login == null || password == null
                || !role.equals("admin")) {
            req.setAttribute("nodata", "ошибка доступа к обновлению");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/noaccess");
            dispatcher.forward(req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/update");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
