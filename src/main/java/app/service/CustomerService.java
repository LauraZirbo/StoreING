package app.service;

import app.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer addCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    Optional<Customer> findById(Long id);

    void deleteById(Long id);

    List<Customer> getAll();
}
