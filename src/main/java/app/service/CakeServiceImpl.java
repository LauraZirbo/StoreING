package app.service;

import app.model.Cake;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import app.repository.CakeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CakeServiceImpl implements CakeService{

    private final CakeRepository cakeRepository;

    public CakeServiceImpl(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }


    @Override
    public Cake addCake(Cake cake) {
        return cakeRepository.save(cake);
    }

    @Override
    public Cake updateCake(Long id, Cake cake) {
        Optional<Cake> optionalCake = cakeRepository.findById(id);
        if (optionalCake.isPresent()) {
            Cake c = optionalCake.get();
            c.setName(cake.getName());
            c.setDescription(cake.getDescription());
            return cakeRepository.save(c);
        }
        return cakeRepository.save(cake);


    }

    @Override
    public Optional<Cake> findById(Long id) {
        return cakeRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        cakeRepository.deleteById(id);
    }

    @Override
    public List<Cake> getAll() {
        return cakeRepository.findAll();
    }
}
