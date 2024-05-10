package br.com.judev.bibliotec.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.judev.bibliotec.dtos.requestDto.AuthorRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.AuthorResponseDto;
import br.com.judev.bibliotec.entity.Author;
import br.com.judev.bibliotec.entity.Zipcode;
import br.com.judev.bibliotec.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/api/author")
@AllArgsConstructor
public class AuthorController {
    
      private final AuthorService authorService;


      
     @Operation(summary = "Adds a new Author",
     description = "Adds a new Author by passing in a JSON, XML or YML representation of the Author!",
     tags = {"Author"},
     responses = {
         @ApiResponse(description = "Created", responseCode = "201",
             content = @Content(schema = @Schema(implementation = Author.class))),
         @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
         @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
         @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
     }
 )
    @PostMapping("/addAuthor")
    public ResponseEntity<AuthorResponseDto> addAuthor(
            @RequestBody final AuthorRequestDto authorRequestDto) {
        AuthorResponseDto authorResponseDto = authorService.addAuthor(authorRequestDto);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }


    @Operation(summary = "Finds a Author", description = "Finds a Author",
    tags = {"Author"},
    responses = {
          @ApiResponse(description = "Success", responseCode = "200",
             content = @Content(schema = @Schema(implementation = Author.class))),
          @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      }
  )
    @GetMapping("/get/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable final Long id) {
        AuthorResponseDto authorResponseDto = authorService.getAuthorById(id);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }


    
     @Operation(summary = "Finds all Author", description = "Finds all Author",
    tags = {"Author"},
    responses = {
        @ApiResponse(description = "Success", responseCode = "200",
               content = { @Content(mediaType = "application/json", 
               array = @ArraySchema(schema = @Schema(implementation = Author.class)))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
         }
)
    @GetMapping("/getAll")
    public ResponseEntity<List<AuthorResponseDto>> getAuthors() {
        List<AuthorResponseDto> authorResponseDtos = authorService.getAuthors();
        return new ResponseEntity<>(authorResponseDtos, HttpStatus.OK);
    }

    @Operation(summary = "delete a city",
    description = "delete a Author by passing in a JSON, XML or YML representation of the Author!",
    tags = {"Author"},
    responses = { 
        @ApiResponse(description = "delete", responseCode = "200",
            content = @Content(schema = @Schema(implementation = Author.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
    }
)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AuthorResponseDto> deleteAuthor(@PathVariable final Long id) {
        AuthorResponseDto authorResponseDto = authorService.deleteAuthor(id);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }


    @Operation(summary = "Updates a Author",
    description = "Updates a City by passing in a JSON, XML or YML representation of the Author!",
    tags = {"Author"},
    responses = { 
        @ApiResponse(description = "Updated", responseCode = "200",
            content = @Content(schema = @Schema(implementation = Author.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
    }
)
    @PutMapping("/edit/{id}")
    private ResponseEntity<AuthorResponseDto> editAuthor(@PathVariable final Long id,
                                                         @RequestBody final AuthorRequestDto authorRequestDto) {
        AuthorResponseDto authorResponseDto = authorService.editAuthor(id, authorRequestDto);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }


  @Operation(summary = "Adds a new City on exists Author",
    description = "Adds a new City by passing in a JSON, XML or YML representation of the Author!",
    tags = {"Author"},
    responses = {
        @ApiResponse(description = "Created", responseCode = "201",
            content = @Content(schema = @Schema(implementation = Author.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
    }
)
    @PostMapping("/addZipcode/{zipcodeId}/toAuthor/{authorId}")
    private ResponseEntity<AuthorResponseDto> addZipcode(@PathVariable final Long zipcodeId,
                                                         @PathVariable final Long authorId) {
        AuthorResponseDto authorResponseDto = authorService.addZipcodeToAuthor(authorId, zipcodeId);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "delete city on  Author",
    description = "delete a city on a Zipcode by passing in a JSON, XML or YML representation of the Author!",
    tags = {"Author"},
    responses = { 
        @ApiResponse(description = "delete", responseCode = "200",
            content = @Content(schema = @Schema(implementation = Zipcode.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
    }
)
    @PostMapping("/removeZipcode/{authorId}")
    private ResponseEntity<AuthorResponseDto> removeZipcode(@PathVariable final Long authorId) {
        AuthorResponseDto authorResponseDto = authorService.deleteZipcodeFromAuthor(authorId);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }
}
