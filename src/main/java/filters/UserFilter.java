package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        urlPatterns ="/user",
        filterName = "UserFilter"
)
public class UserFilter implements Filter {

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
                || !session.getAttribute("role").toString().equals("user")) {
            req.setAttribute("nodata", "ошибка доступа к user");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/noaccess");
            dispatcher.forward(req, resp);
        }else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/user");
            dispatcher.forward(req, resp);        }
    }

    @Override
    public void destroy() {

    }
}
