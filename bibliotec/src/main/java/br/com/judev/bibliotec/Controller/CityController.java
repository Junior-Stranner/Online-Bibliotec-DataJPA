package br.com.judev.bibliotec.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/api/city")
@AllArgsConstructor
public class CityController {
    
     private final CityService cityService;

    @Operation(summary = "Criar uma nova City", description = "Endpoint para criar uma nova City",
            responses = {
                    @ApiResponse(responseCode = "201", description = "City criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = City.class))),
                    @ApiResponse(responseCode = "409", description = "City com informações duplicadas",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = City.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = City.class)))
            })
    @PostMapping(value = "/add",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // Define que o endpoint consome e produz JSON
    public ResponseEntity<City> addCity(@RequestBody City city) {
        City newCity = cityService.addCity(city);
        return new ResponseEntity<>(newCity, HttpStatus.OK);
    }

    @Operation(summary = "Buscar um endereço pelo ID", description = "Endpoint para buscar um endereço pelo ID",
    responses = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = City.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = City.class))),
    })
    @GetMapping(value = "/get/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // Define que o endpoint consome e produz JSON)
    public ResponseEntity<City> getCityById(@PathVariable  Long id) {
        City city = cityService.getCity(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }


      @Operation(summary = "Listar todos os endereços cadastrados", description = "Lista todos os endereços cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de todos os endereços cadastrados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = City.class))))
            })
    @GetMapping(value = "/getall", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = cityService.getCities();
        return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<City> deleteCity(@PathVariable  Long id) {
        City city = cityService.deleteCity(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @PutMapping("/edit/id")
    public ResponseEntity<City> editCity(@RequestBody  City city,
                                         @PathVariable  Long id) {
        City editCity = cityService.editCity(id, city);
        return new ResponseEntity<>(editCity, HttpStatus.OK);
    }
}
