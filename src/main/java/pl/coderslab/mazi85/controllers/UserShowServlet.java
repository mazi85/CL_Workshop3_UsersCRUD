package pl.coderslab.mazi85.controllers;


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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDao userDao = new UserDao();
        String id = request.getParameter("id");
        try {
            User user = userDao.read(Integer.parseInt(id));
            request.setAttribute("user",user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/users/show.jsp")
                .forward(request, response);
    }
}