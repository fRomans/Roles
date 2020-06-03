package filters;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/admin/delete",
        filterName = "DeleteFilter"
)
public class DeleteFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession(false);
        User user = (User) req.getSession().getAttribute("user");
        final String login = user.getName();
        final String password = user.getPassword();
        final String role = user.getRole();


        if (session==null || login==null || password==null || !role.equals("admin")) {
            req.setAttribute("nodata", "ошибка доступа к удалению");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/noaccess");
            dispatcher.forward(req, resp);
        }else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/delete");
            dispatcher.forward(req, resp);        }
    }

    @Override
    public void destroy() {

    }
}

