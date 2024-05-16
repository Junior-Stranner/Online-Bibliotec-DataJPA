package br.com.judev.bibliotec.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.entity.Zipcode;
import br.com.judev.bibliotec.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/api/city")
@AllArgsConstructor
public class CityController {
    
     private final CityService cityService;
     

     @Operation(summary = "Adds a new City",
     description = "Adds a new City by passing in a JSON, XML or YML representation of the City!",
     tags = {"City"},
     responses = {
         @ApiResponse(description = "Created", responseCode = "201",
             content = @Content(schema = @Schema(implementation = City.class))),
         @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
         @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
         @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
     }
 )
    @PostMapping(value = "/add" , produces = MediaType.APPLICATION_JSON_VALUE) // Define que o endpoint consome e produz JSON
    public ResponseEntity<City> createCity(@RequestBody City city) {
        City newCity = cityService.addCity(city);
        return new ResponseEntity<>(newCity, HttpStatus.CREATED);
    }

    @Operation(summary = "Finds a City", description = "Finds a City",
    tags = {"City"},
    responses = {
          @ApiResponse(description = "Success", responseCode = "200",
             content = @Content(schema = @Schema(implementation = City.class))),
          @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      }
  )
    @GetMapping(value = "/get/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // Define que o endpoint consome e produz JSON)
    public ResponseEntity<City> getCityById(@PathVariable  Long id) {
        City city = cityService.getCity(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }


    @Operation(summary = "Finds all City", description = "Finds all City",
    tags = {"City"},
    responses = {
        @ApiResponse(description = "Success", responseCode = "200",
               content = { @Content(mediaType = "application/json", 
               array = @ArraySchema(schema = @Schema(implementation = City.class)))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
         }
)
    @GetMapping(value = "/getall", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<City>> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<City> cityPage = cityService.findAll(pageable);
        return ResponseEntity.ok(cityPage);
    }


    @Operation(summary = "Updates a City",
		description = "Updates a City by passing in a JSON, XML or YML representation of the City!",
		tags = {"City"},
		responses = { 
            @ApiResponse(description = "Updated", responseCode = "200",
				content = @Content(schema = @Schema(implementation = City.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
    @PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> editCity(@RequestBody City city, @PathVariable Long id) {
        City editCity = cityService.editCity(id, city);
        return new ResponseEntity<>(editCity, HttpStatus.OK);
    }


    @Operation(summary = "delete a city",
		description = "delete a city by passing in a JSON, XML or YML representation of the city!",
		tags = {"city"},
		responses = { 
            @ApiResponse(description = "delete", responseCode = "200",
				content = @Content(schema = @Schema(implementation = City.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
    @DeleteMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> deleteCity(@PathVariable  Long id) {
        City city = cityService.deleteCity(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

}
