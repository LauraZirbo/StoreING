package app.controller;

import app.dto.CakeDTO;
import app.mapper.CakeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import app.model.Cake;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;
import app.service.CakeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "server/cakes")
@Tag(name = "Cake", description = "API for cake management.")
public class CakeController {

    private final CakeService cakeService;
    private final CakeMapper cakeMapper;


    public CakeController(CakeService cakeService, CakeMapper cakeMapper) {
        this.cakeService = cakeService;
        this.cakeMapper = cakeMapper;
    }

    @Operation(summary = "Get all the cakes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cakes returned.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class))}),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cake not found.", content = @Content)})
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<CakeDTO> getAllCakes() {
        return cakeService.getAll().stream().map(cakeMapper::toDTO).collect(Collectors.toList());
    }

    @Operation(
            summary = "Get a cake by its id",
            description = "The operation searches through the repository of cakes and, if found, returns the cake by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cake found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cake not found.", content = @Content)})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CakeDTO> findById(
            @Parameter(description = "id of cake to be searched")
            @PathVariable Long id) {
        Optional<Cake> cake = cakeService.findById(id);
        return cake.map(value -> new ResponseEntity<>(cakeMapper.toDTO(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Deletion of a cake by its id",
            description = "The operation searches through the repository of cakes and, if found, deletes it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cake deleted.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cake not found.", content = @Content)})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteById(
            @Parameter(description = "id of cake to be deleted")
            @PathVariable Long id) {
        cakeService.deleteById(id);
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Creation of a new cake",
            description = "The operation creates a cake and returns its final value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cake created.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class))}),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content)})
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<CakeDTO> newCake(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New cake data")
            @RequestBody CakeDTO cake) {
        Cake savedCake = cakeService.addCake(cakeMapper.toEntity(cake));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cakeMapper.toDTO(savedCake));
    }

    @Operation(
            summary = "Update of the cake",
            description = "The operation updates all the information of the cake with selected id and returns its final value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cake updated.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Request is not authenticated.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request is not authorized.", content = @Content),
            @ApiResponse(responseCode = "404", description = "cake not found.", content = @Content)})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CakeDTO> updateCake(
            @Parameter(description = "id of cake to be updated")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cake data to be updated")
            @RequestBody CakeDTO cake) {
        Cake updatedCake = cakeService.updateCake(id, cakeMapper.toEntity(cake));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cakeMapper.toDTO(updatedCake));
    }
}
