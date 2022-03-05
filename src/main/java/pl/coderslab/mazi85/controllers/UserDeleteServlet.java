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



    @WebServlet("/users/delete")
    public class UserDeleteServlet extends HttpServlet {
        UserDao userDao = new UserDao();

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


            String id = request.getParameter("id");
            try {
                User user = userDao.read(Integer.parseInt(id));
                request.setAttribute("user",user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            getServletContext().getRequestDispatcher("/users/delete.jsp")
                    .forward(request, response);
        }
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String id = req.getParameter("id");
            try {
                userDao.delete(Integer.parseInt(id));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resp.sendRedirect(getServletContext().getContextPath() + "/users/list");

        }
    }