package pl.coderslab.mazi85.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.mazi85.dao.UserDao;
import pl.coderslab.mazi85.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users/list")
public class UserListServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDao userDao = new UserDao();
        try {
            List<User> allUsers = userDao.findAll();
            request.setAttribute("allUsers",allUsers);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("błąd bazy SQL, nie udało się odczytać użytkowników");
        }
        getServletContext().getRequestDispatcher("/users/list.jsp")
                .forward(request, response);
    }
}

