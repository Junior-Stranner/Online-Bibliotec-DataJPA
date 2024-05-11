package br.com.judev.bibliotec.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.mapper.AuthorMapper;
import br.com.judev.bibliotec.dtos.mapper.BookMapper;
import br.com.judev.bibliotec.dtos.requestDto.BookRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.BookResponseDto;
import br.com.judev.bibliotec.entity.Author;
import br.com.judev.bibliotec.entity.Book;
import br.com.judev.bibliotec.entity.Category;
import br.com.judev.bibliotec.repository.BookRepository;
import br.com.judev.bibliotec.service.AuthorService;
import br.com.judev.bibliotec.service.BookService;
import br.com.judev.bibliotec.service.CategoryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;


    @Override
    public BookResponseDto addBook(BookRequestDto bookRequestDto) {
        Book book = new Book();
        book.setName(bookRequestDto.getName());

        if (bookRequestDto == null || bookRequestDto.getName() == null || bookRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("Book name cannot be null or blank."); // Mensagem corrigida
        }

        if (bookRequestDto.getAuthorIds().isEmpty()) {
            throw new IllegalArgumentException("you need atleast on author");
        } else {
            List<Author> authors = new ArrayList();
            for (Long authorId: bookRequestDto.getAuthorIds()) {
                Author author = authorService.getAuthor(authorId);

                authors.add(author);
            }
            book.setAuthors(authors);
        }
        if (bookRequestDto.getCategoryId() == null) {
            throw new IllegalArgumentException("book atleast on category");
        }else{

        Category category = categoryService.getCategory(bookRequestDto.getCategoryId());
        book.setCategory(category);

     }

        Book book1 = bookRepository.save(book);
        return BookMapper.ToDto(book1);
    }

    @Override
    public BookResponseDto getBookById(Long bookId) {
     Book book = bookRepository.findById(bookId).orElseThrow(() -> 
          new IllegalArgumentException("could not find Book with id: " + bookId));
     return BookMapper.ToDto(book);
    }

    @Override
    public Book getBook(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() ->
        new IllegalArgumentException("could not find Book with id: " + bookId));

    }

    @Override
    public List<BookResponseDto> getBooks() {
        List<Book> books = StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return BookMapper.toListDto(books);
    }

    @Override
    public BookResponseDto editBook(Long bookId, BookRequestDto bookRequestDto) {
        Book bookToEdit = getBook(bookId);
        bookToEdit.setName(bookRequestDto.getName());

        if (bookRequestDto == null || bookRequestDto.getName() == null || bookRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("Book name cannot be null or blank."); // Mensagem corrigida
        }

        if (!bookRequestDto.getAuthorIds().isEmpty()){
            List<Author> authors = new ArrayList<>();
            for (Long authorId: bookRequestDto.getAuthorIds()) {
                Author author = authorService.getAuthor(authorId);
                authors.add(author);
            }
            bookToEdit.setAuthors(authors);
        }
        if (bookRequestDto.getCategoryId() != null) {
            Category category = categoryService.getCategory(bookRequestDto.getCategoryId());
            bookToEdit.setCategory(category);
        }

        bookRepository.save(bookToEdit);
        return BookMapper.ToDto(bookToEdit);
    }

    @Override
    public BookResponseDto deleteBook(Long bookId) {
        Book book = getBook(bookId);
        bookRepository.delete(book);
        return BookMapper.ToDto(book);
    }

    @Override
     public BookResponseDto addAuthorToBook(Long bookId, Long authorId) {

        // Fetch book and author objects
        Book book = getBook(bookId);
        Author author = authorService.getAuthor(authorId);

        // Check for existing association (improved efficiency)
        if (book.getAuthors().stream().anyMatch(a -> a.getId().equals(authorId))) {
           throw new IllegalArgumentException("This author is already assigned to this book.");
       }
        // Establish bidirectional association
        book.addAuthor(author);
        author.addBook(book);
        // Persist changes using JPA save method
       bookRepository.save(book); // Save the book object
        // Return a DTO representatio
      return BookMapper.ToDto(book);
   }


    @Override
    public BookResponseDto deleteAuthorFromBook(Long bookId, Long authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAuthorFromBook'");
    }

    @Override
    public BookResponseDto addCategoryToBook(Long bookId, Long categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCategoryToBook'");
    }

    @Override
    public BookResponseDto removeCategoryFromBook(Long bookId, Long categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeCategoryFromBook'");
    }
    
}
