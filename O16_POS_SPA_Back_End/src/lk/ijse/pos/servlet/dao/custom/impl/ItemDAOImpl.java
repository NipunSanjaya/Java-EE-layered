package lk.ijse.pos.servlet.dao.custom.impl;

import lk.ijse.pos.servlet.dao.custom.ItemDAO;
import lk.ijse.pos.servlet.entity.Item;
import lk.ijse.pos.servlet.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item to) throws SQLException {
        return CrudUtil.setQuery("INSERT INTO item VALUES (?,?,?,?)", to.getCode(), to.getName(), to.getPrice(), to.getQty());
    }

    @Override
    public boolean update(Item to) throws SQLException {
        return CrudUtil.setQuery("UPDATE item SET item_name=?,unit_price=?,qty=? WHERE item_code=?", to.getName(), to.getPrice(), to.getQty(), to.getCode());
    }


    @Override
    public boolean delete(Item to) throws SQLException {
        return CrudUtil.setQuery("DELETE FROM item WHERE item_code=? ", to.getCode());
    }

    @Override
    public ArrayList<Item> getAll() {
        return null;
    }
}
