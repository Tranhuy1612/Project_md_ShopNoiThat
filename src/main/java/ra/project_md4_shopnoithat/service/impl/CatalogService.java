package ra.project_md4_shopnoithat.service.impl;


import org.springframework.stereotype.Service;
import ra.project_md4_shopnoithat.model.Catalog;
import ra.project_md4_shopnoithat.service.IGenericService;
import ra.project_md4_shopnoithat.untils.ConnectDB;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService implements IGenericService<Catalog, Integer> {
    private final String FIND_ALL = "SELECT * FROM CATALOG";
    private final String FIND_BY_ID = "SELECT * FROM CATALOG WHERE id_catalog = ?";
    private final String INSERT = "INSERT INTO  CATALOG( name, status) values(?,?)";
    private final String UPDATE = "UPDATE CATALOG SET name= ?,status=? where id_catalog = ?";
//    private final String SEARCH = "SELECT *  FROM CATALOG WHERE name like ?";

    @Override
    public List<Catalog> findAll() {
        List<Catalog> list = new ArrayList<>();
        Connection conn = null;
        conn = ConnectDB.getConnection();
        CallableStatement callSt = null;
        try {
            callSt = conn.prepareCall(FIND_ALL);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Catalog catalog = new Catalog();
                catalog.setId(rs.getInt("id"));
                catalog.setName(rs.getString("name"));
                catalog.setStatus(rs.getBoolean("status"));
                list.add(catalog);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return list;
    }

    @Override
    public Catalog findById(Integer id) {
        Connection conn = null ;
        Catalog catalog = null ;
        conn  = ConnectDB.getConnection();
        try {
            CallableStatement callSt = conn.prepareCall(FIND_BY_ID);
            callSt.setInt(1,id);
            ResultSet rs = callSt.executeQuery();
          if (rs.next()){
              catalog = new Catalog();
              catalog.setId(rs.getInt("id"));
              catalog.setName(rs.getString("name"));
              catalog.setStatus(rs.getBoolean("status"));
          }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            ConnectDB.closeConnection(conn);
        }
        return catalog;
    }

    @Override
    public void save(Catalog catalog) {
        Connection conn = null;
        conn = ConnectDB.getConnection();
        try {
            if (catalog.getId() == 0) {
                CallableStatement callSt = conn.prepareCall(INSERT);
                callSt.setString(1, catalog.getName());
                callSt.setBoolean(2, catalog.isStatus());
                callSt.executeUpdate();
            }else{
                CallableStatement updCallSt = conn.prepareCall(UPDATE);
                updCallSt.setString(1, catalog.getName());
                updCallSt.setBoolean(2, catalog.isStatus());
                updCallSt.setInt(3,catalog.getId());
                updCallSt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }


    @Override
    public void delete(Integer id) {

    }


//
//    public List<Catalog> searchByKeyword(String keyword) {
//        List<Catalog> result = new ArrayList<>();
//        Connection conn = null;
//        conn = ConnectDB.getConnection();
//        CallableStatement callSt = null;
//        try {
//            // Sử dụng câu lệnh SQL SELECT với điều kiện LIKE để tìm kiếm theo từ khóa
//             callSt = conn.prepareCall(SEARCH);
//            // Thêm dấu % vào đầu và cuối từ khóa để thực hiện tìm kiếm fuzzy
//            callSt.setString(1, "%" + keyword + "%");
//            ResultSet rs = callSt.executeQuery();
//            while (rs.next()) {
//                Catalog catalog = new Catalog();
//                catalog.setId(rs.getInt("id"));
//                catalog.setName(rs.getString("name"));
//                catalog.setStatus(rs.getBoolean("status"));
//                result.add(catalog);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            ConnectDB.closeConnection(conn);
//        }
//        return result;
//    }

}
