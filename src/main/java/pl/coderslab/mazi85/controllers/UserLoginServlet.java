package pl.coderslab.mazi85.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.mazi85.dao.UserDao;
import pl.coderslab.mazi85.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserLoginServlet.class.getName());
    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/login/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String pass = req.getParameter("pass");

        try {
            User user = userDao.readByEmial(email);
            if (user == null) {
                req.setAttribute("userOk", "false");
                getServletContext().getRequestDispatcher("/login/login.jsp")
                        .forward(req, resp);
                logger.info("{}: {}","Niepoprawny login",email);
                return;
            }
            if (BCrypt.checkpw(pass, user.getPassword())) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                logger.info("{}: {}", "Zalogowano", user);
                resp.sendRedirect(getServletContext().getContextPath()+"/users/list");
                return;
            }
            req.setAttribute("userOk", "false");
            getServletContext().getRequestDispatcher("/login/login.jsp")
                    .forward(req, resp);
            logger.info("{}: {}","Niepoprawne hasło",pass);

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("błąd bazy SQL, nie udało się odczytać użytkowników");
        }

    }
}
