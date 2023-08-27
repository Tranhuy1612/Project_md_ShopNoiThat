package ra.project_md4_shopnoithat.service.impl;

import org.springframework.stereotype.Service;
import ra.project_md4_shopnoithat.model.User;
import ra.project_md4_shopnoithat.service.IGenericService;
import ra.project_md4_shopnoithat.untils.ConnectDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements IGenericService<User,Integer> {
    private final String FINDALL = "SELECT * FROM USERS";
 private final String FINDBYID = "SELECT * FROM USERS WHERE ID = ?";
    private final String UPDATE_STATUS = "UPDATE USERS SET status=? where id = ?";
   private final String SEARCH = "SELECT *  FROM USERS WHERE name like ?";


    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        Connection conn = null;
        conn = ConnectDB.getConnection();
        CallableStatement callSt = null;
        try {
            callSt = conn.prepareCall(FINDALL);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setWallet(rs.getDouble("wallet"));
                user.setStatus(rs.getBoolean("status"));
                user.setRoleId(rs.getLong("role_id"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return list;
    }
    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public long save(User user) {

        return 0;
    }

    @Override
    public void delete(Integer id) {

    }
    public  void  updateStatusAcc(Integer userId, Boolean udStatus){
        Connection conn = null;
        conn = ConnectDB.getConnection();
        CallableStatement callSt =null ;
        try {
            callSt = conn.prepareCall(UPDATE_STATUS);
            callSt.setBoolean(1,udStatus);
            callSt.setInt(2,userId);
            callSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }
}

