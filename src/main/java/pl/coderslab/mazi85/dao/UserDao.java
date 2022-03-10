package pl.coderslab.mazi85.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.mazi85.entity.User;
import pl.coderslab.mazi85.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users (email, username, password)  VALUES (?,?,?)";

    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id=?";

    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET email=?, username=? WHERE id=?";

    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id=?";

    private static final String FIND_ALL_USER_QUERY =
            "SELECT * FROM users";

    private static final String UPDATE_USER_PASSWORD_QUERY =
            "UPDATE users SET password=? WHERE id=?";


    public User create(User user) throws SQLException {
        user.setPassword(hashPassword(user.getPassword()));
        try (Connection connect = DbUtil.getConnection()) {
            PreparedStatement psCreate = connect.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            psCreate.setString(1, user.getEmail());
            psCreate.setString(2, user.getUserName());
            psCreate.setString(3, user.getPassword());
            psCreate.executeUpdate();
            ResultSet rsKeys = psCreate.getGeneratedKeys();
            if (rsKeys.next()) {
                user.setId(rsKeys.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            throw new SQLException();
        }

    }

    public User read(int userId) throws SQLException {
        try (Connection connect = DbUtil.getConnection()) {
            PreparedStatement ps = connect.prepareStatement(READ_USER_QUERY);
            ps.setInt(1, userId);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setEmail(rs.getString(2));
                user.setUserName(rs.getString(3));
                user.setPassword(rs.getString(4));
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void update(User user) throws SQLException {

        try (Connection connect = DbUtil.getConnection()) {
            PreparedStatement ps = connect.prepareStatement(UPDATE_USER_QUERY);
            ps.setString(1,user.getEmail());
            ps.setString(2,user.getUserName());
            ps.setInt(3,user.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void delete(int userId) throws SQLException {
        try (Connection connect = DbUtil.getConnection()) {
            PreparedStatement ps = connect.prepareStatement(DELETE_USER_QUERY);
            ps.setInt(1,userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> users= new ArrayList<>();
        try (Connection connect = DbUtil.getConnection()) {
            ResultSet rs = connect.createStatement().executeQuery(FIND_ALL_USER_QUERY);
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setEmail(rs.getString(2));
                user.setUserName(rs.getString(3));
                user.setPassword(rs.getString(4));
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void updatePassword(User user, String password) throws SQLException {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_PASSWORD_QUERY);
            statement.setString(1, hashPassword(password));
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }



    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());

    }

}
