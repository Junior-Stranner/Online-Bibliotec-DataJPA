package br.com.judev.bibliotec.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.judev.bibliotec.dtos.requestDto.CategoryRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.CategoryResponseDto;
import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/api/category")
@AllArgsConstructor
public class ategoryController {
    

     private final CategoryService categoryService;



     
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
    @PostMapping("/add")
    public ResponseEntity<CategoryResponseDto> addCategory(
            @RequestBody final CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.addCategory(categoryRequestDto);
        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable final Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        List<CategoryResponseDto> categoryResponseDtos = categoryService.getCategories();
        return new ResponseEntity<>(categoryResponseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryResponseDto> deleteCategory(@PathVariable final Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.deleteCategory(id);
        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<CategoryResponseDto> editCategory( @RequestBody final CategoryRequestDto categoryRequestDto,
                                                             @PathVariable final Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.editCategory(id, categoryRequestDto);
        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }

}
    