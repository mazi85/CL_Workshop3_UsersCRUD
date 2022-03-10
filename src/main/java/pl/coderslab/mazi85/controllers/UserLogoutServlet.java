package pl.coderslab.mazi85.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.mazi85.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


    @WebServlet("/logout")
    public class UserLogoutServlet extends HttpServlet {

        private static final Logger logger = LogManager.getLogger(UserLogoutServlet.class.getName());

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            logger.info("{}: {}", "Zalogowano", user);
            session.removeAttribute("user");
            resp.sendRedirect("/login");
        }
    }


