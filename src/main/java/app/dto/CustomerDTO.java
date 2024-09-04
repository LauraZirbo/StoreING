package app.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String deliveryAddress;

    private List<CustomerOrderDTO> ordersList;

}
