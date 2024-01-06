package dao.Custom.IMPL;

import com.jfoenix.controls.JFXButton;
import db.DBconnection;
import dto.ItemDto;
import dto.tm.ItemTB;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import dao.Custom.ItemDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public ItemDto searchItem(String code) {
        return null;
    }

    @Override
    public void updateItemQty(String code,int qty) {
        String sql = " UPDATE Item SET qtyOnHand=?  WHERE code=?";
        try {
            PreparedStatement prestm = DBconnection.getInstance().getConnection().prepareStatement(sql);
            prestm.setString(1, String.valueOf(qty));
            prestm.setString(2,code);
            int i = prestm.executeUpdate();

        } catch (SQLException e) {

        } catch (ClassNotFoundException e) {
           
        }
    }


    @Override
    public boolean save(Item entity) {
        if (entity!=null){
            String sql="INSERT INTO item VALUES(?,?,?,?)";
            try {
                PreparedStatement presm= DBconnection.getInstance().getConnection().prepareStatement(sql);
                presm.setString(1,entity.getCode());
                presm.setString(2,entity.getDescription());
                presm.setString(3, String.valueOf(entity.getUnitPrice()));
                presm.setString(4,String.valueOf(entity.getQtyOnHand()));
                int result=presm.executeUpdate();
                boolean b=result>0;
                return  b;
            } catch (SQLException e) {

            } catch (ClassNotFoundException e) {

            }
        }
        return false;
    }

    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException,NullPointerException {
        String sql="SELECT * FROM Item";
        List<Item> tbList=new ArrayList<>();
        PreparedStatement prestm = DBconnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = prestm.executeQuery();
        while (resultSet.next()) {

            Item t1 = new Item(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4));
            tbList.add(t1);

        }

        return tbList;
    }

    @Override
    public boolean update(Item entity) {
        String sql = " UPDATE Item SET description=?,unitPrice=?, qtyOnHand=?  WHERE code=?";
        try {
            PreparedStatement prestm = DBconnection.getInstance().getConnection().prepareStatement(sql);
            prestm.setString(1, entity.getDescription());
            prestm.setDouble(2, entity.getUnitPrice());
            prestm.setInt(3, entity.getQtyOnHand());
            prestm.setString(4, entity.getCode());
            int i = prestm.executeUpdate();
            return i>0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String data) {
        String sql="DELETE FROM item WHERE code=?";
        try {
            PreparedStatement prestm=DBconnection.getInstance().getConnection().prepareStatement(sql);
            prestm.setString(1,data);
            int i= prestm.executeUpdate();
            if (i>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted...").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
