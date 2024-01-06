package bo.custom;

import dto.CustomerDto;
import dto.ItemDto;
import entity.Customer;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo {
    List<Item> getAllItem() throws SQLException, ClassNotFoundException;
    boolean saveItem(ItemDto dto);
    boolean updateItem(ItemDto dto);
    void deleteItem(String code);
    int getQty(String itemCode);
    boolean updateQty(String code,int qty);
}
