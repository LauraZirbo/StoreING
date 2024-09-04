package app.mapper;
import app.dto.CakeDTO;
import app.model.Cake;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CakeMapper {
    CakeDTO toDTO(Cake cake);
    Cake toEntity(CakeDTO cakeDTO);
}
