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


@WebServlet("/users/add")
public class UserAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/users/add.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDao userDao = new UserDao();
        String userName = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            userDao.create(new User(email, userName, password));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(getServletContext().getContextPath() + "/users/list");
    }


}


