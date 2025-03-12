package com.librarymanagement.library_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.librarymanagement.library_management.exception.DuplicateResourceException;
import com.librarymanagement.library_management.exception.InvalidDataException;
import com.librarymanagement.library_management.exception.ResourceNotFoundException;
import com.librarymanagement.library_management.model.Book;
import com.librarymanagement.library_management.repository.BookRepo;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookService bookService;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        book1 = new Book();
        book1.setId(1);
        book1.setTitle("The Great Gatsby");
        book1.setAuthor("F. Scott Fitzgerald");
        book1.setIsbn("123456789");
        book1.setIsDeleted(false);

        book2 = new Book();
        book2.setId(2);
        book2.setTitle("1984");
        book2.setAuthor("George Orwell");
        book2.setIsbn("987654321");
        book2.setIsDeleted(false);
    }

    @Test
    void testGetAllBooks_Success() {
        when(bookRepo.findByIsDeletedFalse()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        assertEquals("The Great Gatsby", books.get(0).getTitle());
    }

    @Test
    void testGetAllBooks_ThrowsException() {
        when(bookRepo.findByIsDeletedFalse()).thenReturn(Arrays.asList());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> bookService.getAllBooks());
        assertEquals("No books found in the database.", exception.getMessage());
    }

    @Test
    void testGetBookById_Success() {
        when(bookRepo.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(book1));

        Book book = bookService.getBookById(1);

        assertNotNull(book);
        assertEquals("The Great Gatsby", book.getTitle());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepo.findByIdAndIsDeletedFalse(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(99));
        assertEquals("Book with ID 99 not found or deleted.", exception.getMessage());
    }

    @Test
    void testAddBook_Success() {
        when(bookRepo.existsByIsbnAndIsDeletedFalse(book1.getIsbn())).thenReturn(false);
        when(bookRepo.save(any(Book.class))).thenReturn(book1);

        Book savedBook = bookService.addBook(book1);

        assertNotNull(savedBook);
        assertEquals("The Great Gatsby", savedBook.getTitle());
    }

    @Test
    void testAddBook_ThrowsInvalidDataException() {
        Book invalidBook = new Book();

        Exception exception = assertThrows(InvalidDataException.class, () -> bookService.addBook(invalidBook));
        assertEquals("Book title is required.", exception.getMessage());
    }

    @Test
    void testAddBook_ThrowsDuplicateResourceException() {
        when(bookRepo.existsByIsbnAndIsDeletedFalse(book1.getIsbn())).thenReturn(true);

        Exception exception = assertThrows(DuplicateResourceException.class, () -> bookService.addBook(book1));
        assertEquals("A book with ISBN 123456789 already exists.", exception.getMessage());
    }

    @Test
    void testUpdateBook_Success() {
        when(bookRepo.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(book1));
        when(bookRepo.save(any(Book.class))).thenReturn(book1);

        Book updatedInfo = new Book();
        updatedInfo.setTitle("The Great Gatsby - Revised");
        updatedInfo.setAuthor("Fitzgerald");

        Book updatedBook = bookService.updateBook(1, updatedInfo);

        assertNotNull(updatedBook);
        assertEquals("The Great Gatsby - Revised", updatedBook.getTitle());
        assertEquals("Fitzgerald", updatedBook.getAuthor());
    }

    @Test
    void testDeleteBook_Success() {
        when(bookRepo.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(book1));

        bookService.deleteBook(1);

        assertTrue(book1.getIsDeleted());
        verify(bookRepo, times(1)).save(book1);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepo.findByIdAndIsDeletedFalse(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(99));
        assertEquals("Book with ID 99 not found or already deleted.", exception.getMessage());
    }
}
