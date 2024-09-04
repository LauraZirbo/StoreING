package app.controller;

import app.dto.CustomerOrderDTO;
import app.mapper.CustomerOrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import app.model.CustomerOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import app.service.CustomerOrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "server/customerOrders")
@Tag(name = "CustomerOrder", description = "API for customerOrder management.")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;
    private final CustomerOrderMapper customerOrderMapper;
    public CustomerOrderController(CustomerOrderService customerOrderService, CustomerOrderMapper customerOrderMapper) {
        this.customerOrderService = customerOrderService;
        this.customerOrderMapper = customerOrderMapper;
    }

    @Operation(summary = "Get all the customerOrders.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CustomerOrders returned.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerOrder.class))}),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "CustomerOrder not found.", content = @Content)})
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<CustomerOrderDTO> getAllCustomerOrders() {
        return customerOrderService.getAll().stream().map(customerOrderMapper::toDTO).collect(Collectors.toList());
    }

    @Operation(
            summary = "Get a customerOrder by its id",
            description = "The operation searches through the repository of customerOrders and, if found, returns the customerOrder by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CustomerOrder found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerOrder.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "CustomerOrder not found.", content = @Content)})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CustomerOrderDTO> findById(
            @Parameter(description = "id of customerOrder to be searched")
            @PathVariable Long id) {
        Optional<CustomerOrder> customerOrder = customerOrderService.findById(id);
        return customerOrder.map(value -> new ResponseEntity<>(customerOrderMapper.toDTO(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Deletion of a customerOrder by its id",
            description = "The operation searches through the repository of customerOrders and, if found, deletes it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CustomerOrder deleted.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerOrder.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content),
            @ApiResponse(responseCode = "404", description = "CustomerOrder not found.", content = @Content)})

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteById(
            @Parameter(description = "id of customerOrder to be deleted")
            @PathVariable Long id) {
        customerOrderService.deleteById(id);
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Creation of a new customerOrder",
            description = "The operation creates a customerOrder and returns its final value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CustomerOrder created.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerOrder.class))}),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content
            )})
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<CustomerOrderDTO> newCustomerOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New customerOrder data")
            @RequestBody CustomerOrderDTO customerOrder) {
        CustomerOrder savedCustomerOrder = customerOrderService.addCustomerOrder(customerOrderMapper.toEntity(customerOrder));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerOrderMapper.toDTO(savedCustomerOrder));
    }

    @Operation(
            summary = "Update of the customerOrder",
            description = "The operation updates all the information of the customerOrder with selected id and returns its final value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CustomerOrder updated.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerOrder.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "customerOrder not found.", content = @Content)})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CustomerOrderDTO> updateCustomerOrder(
            @Parameter(description = "id of customerOrder to be updated")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "CustomerOrder data to be updated")
            @RequestBody CustomerOrderDTO customerOrder) {
        CustomerOrder updatedCustomerOrder = customerOrderService.updateCustomerOrder(id, customerOrderMapper.toEntity(customerOrder));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerOrderMapper.toDTO(updatedCustomerOrder));
    }
}
