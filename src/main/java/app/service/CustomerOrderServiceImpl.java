package app.service;

import app.model.CustomerOrder;
import app.repository.CustomerOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService{

    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }


    @Override
    public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public CustomerOrder updateCustomerOrder(Long id, CustomerOrder customerOrder) {
        Optional<CustomerOrder> optionalCustomerOrder = customerOrderRepository.findById(id);
        if (optionalCustomerOrder.isPresent()) {
            CustomerOrder c = optionalCustomerOrder.get();
            c.setName(customerOrder.getName());
            c.setStatus(customerOrder.getStatus());
            c.setDeliveryDate(customerOrder.getDeliveryDate());
            c.setCakesOrdered(customerOrder.getCakesOrdered());
            return customerOrderRepository.save(c);
        }
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public Optional<CustomerOrder> findById(Long id) {
        return customerOrderRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        customerOrderRepository.deleteById(id);
    }

    @Override
    public List<CustomerOrder> getAll() {
        return customerOrderRepository.findAll();
    }
    
}
