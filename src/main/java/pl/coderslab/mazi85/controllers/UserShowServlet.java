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

@WebServlet("/users/show")
public class UserShowServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserShowServlet.class.getName());
    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        try {
            User user = userDao.read(Integer.parseInt(id));
            request.setAttribute("user",user);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("błąd bazy SQL, nie udało się odczytać użytkownika");
        }
        getServletContext().getRequestDispatcher("/users/show.jsp")
                .forward(request, response);
    }
}