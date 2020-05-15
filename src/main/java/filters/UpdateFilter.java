package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(
        urlPatterns ="/admin/update",
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
        final HttpSession session = req.getSession();


        if (session==null ||
                session.getAttribute("login")==null ||
                session.getAttribute("password")==null
                || !session.getAttribute("role").toString().equals("admin")) {
            req.setAttribute("error", "ошибка доступа к обновлению");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/showUsers.jsp");
            dispatcher.forward(req, resp);
        }else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/update");
            dispatcher.forward(req, resp);        }
    }

    @Override
    public void destroy() {

    }
}
