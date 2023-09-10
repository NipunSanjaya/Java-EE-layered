package lk.ijse.pos.servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Item {
    private String code;
    private String name;
    private String price;
    private String  qty;

    public Item(String code) {

    }
}

