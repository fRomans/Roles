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

import static java.util.Objects.nonNull;

@WebFilter(
        urlPatterns = "/admin",
        filterName = "AuthFilter"
)

public class AuthFilter implements Filter {
    private UserService service = UserService.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        final HttpSession session = req.getSession(false);
//есть ли сессия

        if (session != null) {
            User user = (User) req.getSession().getAttribute("user");
            if (user == null) {
                req.setAttribute("nodata", "ошибка доступа!!!");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/noaccess");
                dispatcher.forward(req, resp);
                return;
            }
            if (user.getRole().equals("admin")) {

                RequestDispatcher dispatcher = req.getRequestDispatcher("/admin");
                dispatcher.forward(req, resp);
                return;
            } else if (user.getRole().equals("user")) {
                resp.sendRedirect("/user");
                return;

            } else {
                req.setAttribute("nodata", "ошибка доступа!!!");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/noaccess");
                dispatcher.forward(req, resp);
                return;
            }
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}

//
//package filters;
//
//        import dao.UserDAO;
//        import model.User;
//        import service.UserService;
//
//        import javax.servlet.annotation.WebFilter;
//        import javax.servlet.*;
//        import javax.servlet.http.HttpServletRequest;
//        import javax.servlet.http.HttpServletResponse;
//        import javax.servlet.http.HttpSession;
//        import java.io.IOException;
//        import java.sql.SQLException;
//        import java.util.List;
//
//        import static java.util.Objects.nonNull;
//
//@WebFilter(
//        urlPatterns = "/admin",
//        filterName = "AuthFilter"
//)
//
//public class AuthFilter implements Filter {
//    private UserService service = UserService.getInstance();
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(final ServletRequest request,
//                         final ServletResponse response,
//                         final FilterChain chain) throws IOException, ServletException {
//
//        final HttpServletRequest req = (HttpServletRequest) request;
//        final HttpServletResponse resp = (HttpServletResponse) response;
//        final String login = req.getParameter("name");
//        final String password = req.getParameter("password");
//        final HttpSession session = req.getSession(false);
////есть ли сессия
//
//        if (nonNull(session) &&
//                nonNull(session.getAttribute("login")) &&
//                nonNull(session.getAttribute("password"))) {
//            final String role = session.getAttribute("role").toString();
//
//            moveToMenu(req, resp, role);
//
//
//        } else if (service.userIsExist(login, password)) {
//
//            final String role = service.getRoleByLoginPassword(login, password);
//
////            req.getSession().setAttribute("password", password);
////            req.getSession().setAttribute("login", login);
////            req.getSession().setAttribute("role", role);
//
//            moveToMenu(req, resp, role);
//        } else {
//
//            moveToMenu(req, resp, "UNKNOWN");
//        }
//    }
//
//
//    private void moveToMenu(final HttpServletRequest req,
//                            final HttpServletResponse resp,
//                            final String role)
//            throws ServletException, IOException {
//
//        if (role.equals("admin")) {
//
//            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin");
//            dispatcher.forward(req, resp);
//
//        } else if (role.equals("user")) {
//            resp.sendRedirect("/user");
//
//        } else {
//            req.setAttribute("nodata", "ошибка доступа!!!");
//            RequestDispatcher dispatcher = req.getRequestDispatcher("/noaccess");
//            dispatcher.forward(req, resp);
//        }
//    }
//
//
//    @Override
//    public void destroy() {
//    }
//
//}



