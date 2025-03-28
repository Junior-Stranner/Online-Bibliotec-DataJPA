package br.com.judev.bibliotec.Controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.judev.bibliotec.dtos.requestDto.ZipcodeRequestDto;
import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.entity.Zipcode;
import br.com.judev.bibliotec.service.ZipcodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/zipcode")
@AllArgsConstructor
public class ZipcodeController {
    
        private final ZipcodeService zipcodeService;


        
     @Operation(summary = "Adds a new Zipcode",
     description = "Adds a new Zipcode by passing in a JSON, XML or YML representation of the Zipcode!",
     tags = {"Zipcode"},
     responses = {
         @ApiResponse(description = "Created", responseCode = "201",
             content = @Content(schema = @Schema(implementation = Zipcode.class))),
         @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
         @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
         @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
     }
 )
    @PostMapping(value = "/add" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Zipcode> addZipcode(@RequestBody final ZipcodeRequestDto zipcodeRequestDto) {
        Zipcode zipcode = zipcodeService.addZipcode(zipcodeRequestDto);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }


    @Operation(summary = "Find a Zipcode", description = "Finds a Zipcode",
    tags = {"Zipcode"},
    responses = {
          @ApiResponse(description = "Success", responseCode = "200",
             content = @Content(schema = @Schema(implementation = Zipcode.class))),
          @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      }
  )
    @GetMapping(value = "/get/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Zipcode> getZipcode(@PathVariable final Long id) {
        Zipcode zipcode = zipcodeService.getZipcode(id);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }
    
    @Operation(summary = "Finds all zipcodes", description = "Finds all zipcodes",
    tags = {"zipcodes"},
    responses = {
        @ApiResponse(description = "Success", responseCode = "200",
               content = { @Content(mediaType = "application/json",
               array = @ArraySchema(schema = @Schema(implementation = Zipcode.class)))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
         }
)
     @GetMapping(value = "/getAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Zipcode>> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Zipcode> zipcodePage = zipcodeService.findAll(pageable);
        return ResponseEntity.ok(zipcodePage);
    }

      @Operation(summary = "delete a Zipcode",
		description = "delete a Zipcode by passing in a JSON, XML or YML representation of the Zipcode!",
		tags = {"Zipcode"},
		responses = { 
            @ApiResponse(description = "delete", responseCode = "200",
				content = @Content(schema = @Schema(implementation = Zipcode.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
    @DeleteMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Zipcode> deleteZipcode(@PathVariable final Long id) {
        Zipcode zipcode = zipcodeService.deleteZipcode(id);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }

    @Operation(summary = "Updates a Zipcode",
    description = "Updates a Zipcode by passing in a JSON, XML or YML representation of the Zipcode!",
    tags = {"Zipcode"},
    responses = { 
        @ApiResponse(description = "Updated", responseCode = "200",
            content = @Content(schema = @Schema(implementation = Zipcode.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
    }
)
    @PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Zipcode> editZipcode(@RequestBody final ZipcodeRequestDto zipcodeRequestDto,
                                               @PathVariable final Long id) {
        Zipcode zipcode = zipcodeService.editZipcode(id, zipcodeRequestDto);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }


    @Operation(summary = "Adds a new City on exists Zipcode",
    description = "Adds a new City by passing in a JSON, XML or YML representation of the Zipcode!",
    tags = {"Zipcode"},
    responses = {
        @ApiResponse(description = "Created", responseCode = "201",
            content = @Content(schema = @Schema(implementation = Zipcode.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
    }
)
    @PostMapping(value = "/addCity/{cityId}/to/Zipcode/{zipcodeId}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Zipcode> addCity(@PathVariable final Long cityId,
                                           @PathVariable final Long zipcodeId) {
        Zipcode zipcode = zipcodeService.addCityToZipcode(zipcodeId, cityId);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }


    @Operation(summary = "delete city on  Zipcode",
    description = "delete a city on a Zipcode by passing in a JSON, XML or YML representation of the Zipcode!",
    tags = {"Zipcode"},
    responses = { 
        @ApiResponse(description = "delete", responseCode = "200",
            content = @Content(schema = @Schema(implementation = Zipcode.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
    }
)
    @DeleteMapping(value = "/deleteCity/{zipcodeId}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Zipcode> deleteCity(@PathVariable final Long zipcodeId) {
        Zipcode zipcode = zipcodeService.removeCityFromZipcode(zipcodeId);
        return ResponseEntity.ok().body(zipcode);
    }

}
