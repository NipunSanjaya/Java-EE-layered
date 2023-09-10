package lk.ijse.pos.servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

public class Customer {
    @Data
    @AllArgsConstructor
    @ToString
    public class CustomerDTO {
        private String id;
        private String name;
        private String address;
        private String salary;

    }

}
