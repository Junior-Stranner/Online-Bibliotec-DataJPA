package br.com.judev.bibliotec.Controller.Documentation;

import br.com.judev.bibliotec.dtos.requestDto.AuthorRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.AuthorResponseDto;
import br.com.judev.bibliotec.entity.Author;
import br.com.judev.bibliotec.entity.Zipcode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public interface AuthorDocumentationController {

    @Operation(summary = "Adds a new Author",
            description = "Adds a new Author by passing in a JSON, XML, or YML representation of the Author.",
            tags = {"Author"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content(schema = @Schema(implementation = Author.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    ResponseEntity<AuthorResponseDto> addAuthor(@RequestBody final AuthorRequestDto authorRequestDto);

    @Operation(summary = "Finds an Author",
            description = "Finds an Author by ID.",
            tags = {"Author"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(schema = @Schema(implementation = Author.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable final Long id);

    @Operation(summary = "Finds all Authors",
            description = "Finds all Authors with pagination support.",
            tags = {"Author"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Author.class)))),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    ResponseEntity<List<AuthorResponseDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Deletes an Author",
            description = "Deletes an Author by ID.",
            tags = {"Author"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Deleted",
                            content = @Content(schema = @Schema(implementation = Author.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    ResponseEntity<AuthorResponseDto> deleteAuthor(@PathVariable final Long id);

    @Operation(summary = "Updates an Author",
            description = "Updates an existing Author.",
            tags = {"Author"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated",
                            content = @Content(schema = @Schema(implementation = Author.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    ResponseEntity<AuthorResponseDto> editAuthor(@PathVariable final Long id,
                                                 @RequestBody final AuthorRequestDto authorRequestDto);

    @Operation(summary = "Adds a Zipcode to an Author",
            description = "Associates a Zipcode to an Author.",
            tags = {"Author"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated",
                            content = @Content(schema = @Schema(implementation = Author.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    ResponseEntity<AuthorResponseDto> addZipcode(@PathVariable final Long zipcodeId,
                                                 @PathVariable final Long authorId);

    @Operation(summary = "Removes a Zipcode from an Author",
            description = "Removes the Zipcode associated with an Author.",
            tags = {"Author"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated",
                            content = @Content(schema = @Schema(implementation = Zipcode.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    ResponseEntity<AuthorResponseDto> removeZipcode(@PathVariable final Long authorId);
}
