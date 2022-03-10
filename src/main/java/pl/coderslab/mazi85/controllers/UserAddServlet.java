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



@WebServlet("/users/add")
public class UserAddServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserAddServlet.class.getName());

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

        if(parametersValid(userName,email,password)){
            try {
                User user = userDao.create(new User(email, userName, password));
                logger.info("dodano użytkownika: {}",user);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("błąd bazy SQL, nie dodano użytkownika");
            }
            resp.sendRedirect(getServletContext().getContextPath() + "/users/list");
        }
        else {
            req.setAttribute("dataOk","false");
            logger.info("wprowadzono nie poprawne dane dla nowego użytkownika");
            getServletContext().getRequestDispatcher("/users/add.jsp")
                    .forward(req, resp);
        }


    }

    private boolean parametersValid(String userName, String email, String password) {
        if (userName == null || email==null || password==null
        || email.equals("") || password.equals("")){
            return false;
        }
        return true;
    }


}


