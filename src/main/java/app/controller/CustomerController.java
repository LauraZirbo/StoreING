package app.controller;

import app.dto.CustomerDTO;
import app.mapper.CustomerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import app.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import app.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "server/customers")
@Tag(name = "Customer", description = "API for customer management.")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @Operation(summary = "Get all the customers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers returned.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found.", content = @Content)})
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAll().stream().map(customerMapper::toDTO).collect(Collectors.toList());
    }

    @Operation(
            summary = "Get a customer by its id",
            description = "The operation searches through the repository of customers and, if found, returns the customer by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found.", content = @Content)})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CustomerDTO> findById(
            @Parameter(description = "id of customer to be searched")
            @PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        return customer.map(value -> new ResponseEntity<>(customerMapper.toDTO(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Deletion of a customer by its id",
            description = "The operation searches through the repository of customers and, if found, deletes it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deleted.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found.", content = @Content)})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteById(
            @Parameter(description = "id of customer to be deleted")
            @PathVariable Long id) {
        customerService.deleteById(id);
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Creation of a new customer",
            description = "The operation creates a customer and returns its final value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content)})
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<CustomerDTO> newCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New customer data")
            @RequestBody CustomerDTO customer) {
        Customer savedCustomer = customerService.addCustomer(customerMapper.toEntity(customer));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerMapper.toDTO(savedCustomer));
    }

    @Operation(
            summary = "Update of the customer",
            description = "The operation updates all the information of the customer with selected id and returns its final value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "customer not found.", content = @Content)})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CustomerDTO> updateCustomer(
            @Parameter(description = "id of customer to be updated")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Customer data to be updated")
            @RequestBody CustomerDTO customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerMapper.toEntity(customer));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerMapper.toDTO(updatedCustomer));
    }
}
