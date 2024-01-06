package dao.Custom;

import dao.CrudDao;
import dto.ItemDto;
import dto.tm.ItemTB;
import entity.Item;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ItemDao extends CrudDao<Item> {
    ItemDto searchItem(String code);
    void updateItemQty(String code,int qty);




}
