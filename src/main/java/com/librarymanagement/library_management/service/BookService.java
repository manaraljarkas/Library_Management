package com.librarymanagement.library_management.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarymanagement.library_management.exception.DuplicateResourceException;
import com.librarymanagement.library_management.exception.InvalidDataException;
import com.librarymanagement.library_management.exception.ResourceNotFoundException;
import com.librarymanagement.library_management.model.Book;
import com.librarymanagement.library_management.repository.BookRepo;

import jakarta.transaction.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepo repo;

    public List<Book> getAllBooks() {
        List<Book> books = repo.findByIsDeletedFalse();
        if (books.isEmpty()) {
            throw new ResourceNotFoundException("No books found in the database.");
        }
        return books;
    }

    public Book getBookById(int id) {
        return repo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found or deleted."));
    }

    public Book addBook(Book book) {
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidDataException("Book title is required.");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new InvalidDataException("Book author is required.");
        }
        if (repo.existsByIsbnAndIsDeletedFalse(book.getIsbn())) {
        throw new DuplicateResourceException("A book with ISBN " + book.getIsbn() + " already exists.");
    }
        book.setIsDeleted(false);
        return repo.save(book);
    }

    @Transactional
    public Book updateBook(int id, Book updatedBook) {
        Book existingBook = repo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found or deleted."));

        if (updatedBook.getTitle() != null && !updatedBook.getTitle().trim().isEmpty()) {
            existingBook.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthor() != null && !updatedBook.getAuthor().trim().isEmpty()) {
            existingBook.setAuthor(updatedBook.getAuthor());
        }
        if (updatedBook.getPublicationYear() != 0) {
            existingBook.setPublicationYear(updatedBook.getPublicationYear());
        }
        if (updatedBook.getIsbn() != null && !updatedBook.getIsbn().trim().isEmpty()) {
            existingBook.setIsbn(updatedBook.getIsbn());
        }

        return repo.save(existingBook);
    }

    @Transactional
    public void deleteBook(int id) {
        Book book = repo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found or already deleted."));
        book.setIsDeleted(true);
        repo.save(book);
    }

    

}
