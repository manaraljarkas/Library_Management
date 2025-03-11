package com.librarymanagement.library_management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarymanagement.library_management.model.Book;
import com.librarymanagement.library_management.model.BorrowingRecord;
import com.librarymanagement.library_management.model.Patron;
import com.librarymanagement.library_management.repository.BookRepo;
import com.librarymanagement.library_management.repository.BorrowingRecordRepo;
import com.librarymanagement.library_management.repository.PatronRepo;

import java.time.LocalDate;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepo repo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private PatronRepo patronRepo;

    public BorrowingRecord borrowBook(int bookId, int patronId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Patron patron = patronRepo.findById(patronId)
                .orElseThrow(() -> new RuntimeException("Patron not found"));
    
        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowDate(LocalDate.now());
        record.setReturnDate(LocalDate.now().plusDays(book.getLoanPeriod()));
        record.setIsReturned(false);
    
        return repo.save(record);
    }

    public BorrowingRecord returnBook(int bookId, int patronId) {
        BorrowingRecord record = repo.findByBookIdAndPatronIdAndIsReturnedFalse(bookId, patronId)
                .orElseThrow(() -> new RuntimeException("No active borrowing record found"));
    
        record.setReturnDate(LocalDate.now());
        record.setIsReturned(true);
    
        return repo.save(record);
    }

    public List<BorrowingRecord> getAllBorrowings() {
        return repo.findAll();
    }
}