//package ra.project_md4_shopnoithat.service.impl;
//
//import org.springframework.stereotype.Service;
//import ra.project_md4_shopnoithat.model.Order;
//import ra.project_md4_shopnoithat.service.IGenericService;
//import ra.project_md4_shopnoithat.untils.ConnectDB;
//
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Service
//public class OrderService  implements IGenericService<Order,Integer> {
//    private final String FINDALL = "SELECT * FROM ORDERS";
//    private final String INSERT = "INSERT INTO ORDERS(id_user, phoneNumber, address,totalPrice, status, dateBuy) VALUES (?, ?, ?, ?, ?, ?)";
//    private final String FIND_BY_USER_ID = "SELECT * FROM ORDERS WHERE id_user = ?";
//    public List<Order> findByUserId(int userId) {
//        List<Order> list = new ArrayList<>();
//        Connection conn = null;
//
//        try {
//            conn = ConnectDB.getConnection();
//            CallableStatement callSt = conn.prepareCall(FIND_BY_USER_ID);
//            callSt.setInt(1, userId);
//            ResultSet rs = callSt.executeQuery();
//            while (rs.next()) {
//                Order order = new Order();
//                order.setId(rs.getLong("id"));
//                order.setUserId(rs.getInt("userId"));
//                order.setPhoneNumber(rs.getString("phoneNumber"));
//                order.setAddress(rs.getString("address"));
//                order.setTotalPrice(rs.getInt("totalPrice"));
//                order.setStatus(rs.getBoolean("status"));
//                order.setBuyDate(rs.getDate("dateBuy"));
//                // Add the order to the list
//                list.add(order);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            ConnectDB.closeConnection(conn);
//        }
//
//        return list;
//    }
//
//    @Override
//    public List<Order> findAll() {
//        List<Order> list = new ArrayList<>();
//        Connection conn = null;
//
//        try {
//            conn = ConnectDB.getConnection();
//            CallableStatement callSt = conn.prepareCall(FINDALL);
//            ResultSet rs = callSt.executeQuery();
//
//            while (rs.next()) {
//                Order order = new Order();
//                order.setId(rs.getLong("id"));
//                order.setUser_id(rs.getInt(" id_user"));
//                order.setReceiver(rs.getString("receiver"));
//                order.setPhoneNumber(rs.getString("phoneNumber"));
//                order.setAddress(rs.getString("address"));
//                order.setTotal_price(rs.getInt("totalPrice"));
//                order.setStatus(rs.getBoolean("status"));
//                order.setBuyDate(rs.getDate("dateBuy"));
//                // Add the product to the list
//                list.add(order);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            ConnectDB.closeConnection(conn);
//        }
//
//        return list;
//    }
//
//
//    @Override
//    public Order findById(Integer id) {
//        return null;
//    }
//
//    @Override
//    public long save(Order order) {
//        Connection conn = null;
//        conn = ConnectDB.getConnection();
//        try {
//            CallableStatement callSt = conn.prepareCall(INSERT);
//
//            callSt.setInt(1, order.getUser_id());
//            callSt.setString(2, order.getPhoneNumber());
//            callSt.setString(3, order.getAddress());
//            callSt.setDouble(4, order.getTotal_price());
//            callSt.setBoolean(5, order.isStatus());
//            callSt.setDate(6, new Date(order.getBuyDate().getTime()));
//            callSt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }finally {
//            ConnectDB.closeConnection(conn);
//        }
//        return order.getUser_id();
//    }
//
//    @Override
//    public void delete(Integer id) {
//
//    }
//
//
//}
