package lk.ijse.pos.servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CustomerDTO {
    private String id;
    private String name;
    private String address;
    private String salary;

}
