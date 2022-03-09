package pl.coderslab.mazi85.controllers;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.mazi85.dao.UserDao;
import pl.coderslab.mazi85.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/users/editPass")
public class UserPasswordEditServlet extends HttpServlet {

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
        getServletContext().getRequestDispatcher("/users/editPass.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String oldPass = req.getParameter("oldPass");
        String newPass = req.getParameter("newPass");

        try {
            User user = userDao.read(Integer.parseInt(id));
            if (BCrypt.checkpw(oldPass,user.getPassword())){
            userDao.updatePassword(user,newPass);
            req.setAttribute("updatePassOk","true");
                req.setAttribute("user",user);}
            else {
                req.setAttribute("updatePassOk","false");
                req.setAttribute("user",user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getServletContext().getRequestDispatcher("/users/editPass.jsp")
                .forward(req, resp);
    }
}


