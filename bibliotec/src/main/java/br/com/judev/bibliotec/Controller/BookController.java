package br.com.judev.bibliotec.Controller;

import java.util.List;

import br.com.judev.bibliotec.entity.Zipcode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.judev.bibliotec.dtos.requestDto.BookRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.BookResponseDto;
import br.com.judev.bibliotec.entity.Book;
import br.com.judev.bibliotec.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/api/book")
@AllArgsConstructor
public class BookController {
    
     private final BookService bookService;


     @Operation(summary = "Book a new Author",
     description = "Adds a new Book by passing in a JSON, XML or YML representation of the Book!",
     tags = {"Book"},
     responses = {
         @ApiResponse(description = "Created", responseCode = "201",
             content = @Content(schema = @Schema(implementation = Book.class))),
         @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
         @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
         @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
     }
 )
    @PostMapping(value = "/add",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> addBook(@RequestBody final BookRequestDto bookRequestDto) {
        BookResponseDto bookResponseDto = bookService.addBook(bookRequestDto);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }


    @Operation(summary = "Finds a Book", description = "Finds a Book",
    tags = {"Book"},
    responses = {
          @ApiResponse(description = "Success", responseCode = "200",
             content = @Content(schema = @Schema(implementation = Book.class))),
          @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      }
  )
    @GetMapping(value = "/get/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> getBook(@PathVariable final Long id) {
        BookResponseDto bookResponseDto = bookService.getBookById(id);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }


    
     @Operation(summary = "Finds all Books", description = "Finds all Books",
    tags = {"Books"},
    responses = {
        @ApiResponse(description = "Success", responseCode = "200",
               content = { @Content(mediaType = "application/json", 
               array = @ArraySchema(schema = @Schema(implementation = Book.class)))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
         }
)
    @GetMapping(value = "/getall", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResponseDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<BookResponseDto> bookPage = bookService.getBooks(pageable);
        return ResponseEntity.ok(bookPage);
    }


    @Operation(summary = "Add a new Category on exists book",
            description = "Adds a new Category by passing in a JSON, XML or YML representation of the book!",
            tags = {"book"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/addCategory/{categoryId}/to/{bookId}")
    public ResponseEntity<BookResponseDto> addCategory(@PathVariable final Long categoryId,
                                                       @PathVariable final Long bookId) {
        BookResponseDto bookResponseDto = bookService.addCategoryToBook(bookId, categoryId);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }


    @Operation(summary = "delete Category from  book",
            description = "delete a Category from  book by passing in a JSON, XML or YML representation of the book!",
            tags = {"book"},
            responses = {
                    @ApiResponse(description = "delete", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Zipcode.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/removeCategory/{categoryId}/from/{bookId}")
    public ResponseEntity<BookResponseDto> removeCategory(@PathVariable final Long categoryId,
                                                          @PathVariable final Long bookId) {
        BookResponseDto bookResponseDto = bookService.removeCategoryFromBook(bookId, categoryId);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }


    @Operation(summary = "Add a new author on exists book",
            description = "Adds a new author by passing in a JSON, XML or YML representation of the book!",
            tags = {"book"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("addAuthor/{authorId}/to/book/{bookId}")
    public ResponseEntity<BookResponseDto> addAuthor(@PathVariable Long authorId , @PathVariable Long bookId) {
     BookResponseDto bookResponseDto = bookService.addAuthorToBook(bookId, authorId);
     return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
     
    }


    @Operation(summary = "delete author from  book",
            description = "delete a author from book by passing in a JSON, XML or YML representation of the book!",
            tags = {"book"},
            responses = {
                    @ApiResponse(description = "delete", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Zipcode.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/removeAuthor/{authorId}/from/{bookId}")
    public ResponseEntity<BookResponseDto> removeAuthor(@PathVariable final Long authorId,
                                                        @PathVariable final Long bookId) {
        BookResponseDto bookResponseDto = bookService.deleteAuthorFromBook(bookId, authorId);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

}
