package bo.custom.Impl;

import bo.custom.ItemBo;
import dao.Custom.IMPL.ItemDaoImpl;
import dao.Custom.ItemDao;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    ItemDao itemDao=new ItemDaoImpl();
    List<Item> list=new ArrayList<>();
    @Override
    public List<Item> getAllItem() throws SQLException, ClassNotFoundException {
        return itemDao.getAll();

    }

    @Override
    public boolean saveItem(ItemDto dto) {
        return itemDao.save(new Item(dto.getCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQty()));
    }

    @Override
    public boolean updateItem(ItemDto dto) {
        return itemDao.update(new Item(dto.getCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQty()));
    }

    @Override
    public void deleteItem(String code) {
        itemDao.delete(code);
    }

    @Override
    public int getQty(String itemCode) {

        try {
            list=itemDao.getAll();
            for (Item x1:list) {
                if (x1.getCode().equals(itemCode)) {
                    return x1.getQtyOnHand();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public boolean updateQty(String code,int qty) {

        try {
            list=itemDao.getAll();
            for (Item item:list) {
                int x;
                if (item.getCode().equals(code)){
                    x=item.getQtyOnHand()-qty;
                    System.out.println(x+"--"+item.getCode());
                    itemDao.updateItemQty(code,x);

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
