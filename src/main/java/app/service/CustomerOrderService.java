package app.service;

import app.model.CustomerOrder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderService {
    CustomerOrder addCustomerOrder(CustomerOrder customerOrder);

    CustomerOrder updateCustomerOrder(Long id, CustomerOrder customerOrder);

    Optional<CustomerOrder> findById(Long id);

    void deleteById(Long id);

    List<CustomerOrder> getAll();
}
