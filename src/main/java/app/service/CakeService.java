package app.service;

import app.model.Cake;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CakeService {
    Cake addCake(Cake cake);

    Cake updateCake(Long id, Cake cake);

    Optional<Cake> findById(Long id);

    void deleteById(Long id);

    List<Cake> getAll();
}
