package app.mapper;

import app.dto.CustomerOrderDTO;
import app.model.CustomerOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper {
    CustomerOrderDTO toDTO(CustomerOrder customerOrder);
    CustomerOrder toEntity(CustomerOrderDTO customerOrderDTO);
}
