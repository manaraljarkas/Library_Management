package com.librarymanagement.library_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.librarymanagement.library_management.model.Book;
import com.librarymanagement.library_management.model.BorrowingRecord;
import com.librarymanagement.library_management.model.Patron;
import com.librarymanagement.library_management.repository.BookRepo;
import com.librarymanagement.library_management.repository.BorrowingRecordRepo;
import com.librarymanagement.library_management.repository.PatronRepo;

@ExtendWith(MockitoExtension.class)
class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepo borrowingRecordRepo;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private PatronRepo patronRepo;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1);
        book.setTitle("The Great Gatsby");
        book.setLoanPeriod(14); 

        patron = new Patron();
        patron.setId(1);
        patron.setFullname("John Doe");

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setReturnDate(LocalDate.now().plusDays(14));
        borrowingRecord.setIsReturned(false);
    }

    @Test
    void testBorrowBook_Success() {
        when(bookRepo.findById(1)).thenReturn(Optional.of(book));
        when(patronRepo.findById(1)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepo.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord record = borrowingRecordService.borrowBook(1, 1);

        assertNotNull(record);
        assertEquals("The Great Gatsby", record.getBook().getTitle());
        assertEquals("John Doe", record.getPatron().getFullname());
        assertFalse(record.getIsReturned());
    }

    @Test
    void testBorrowBook_BookNotFound() {
        when(bookRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> borrowingRecordService.borrowBook(1, 1));
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void testBorrowBook_PatronNotFound() {
        when(bookRepo.findById(1)).thenReturn(Optional.of(book));
        when(patronRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> borrowingRecordService.borrowBook(1, 1));
        assertEquals("Patron not found", exception.getMessage());
    }

    @Test
    void testReturnBook_Success() {
        when(borrowingRecordRepo.findByBookIdAndPatronIdAndIsReturnedFalse(1, 1))
                .thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordRepo.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord returnedRecord = borrowingRecordService.returnBook(1, 1);

        assertNotNull(returnedRecord);
        assertTrue(returnedRecord.getIsReturned());
    }

    @Test
    void testReturnBook_NoActiveRecordFound() {
        when(borrowingRecordRepo.findByBookIdAndPatronIdAndIsReturnedFalse(1, 1))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> borrowingRecordService.returnBook(1, 1));
        assertEquals("No active borrowing record found", exception.getMessage());
    }

    @Test
    void testGetAllBorrowings() {
        when(borrowingRecordRepo.findAll()).thenReturn(Arrays.asList(borrowingRecord));

        List<BorrowingRecord> records = borrowingRecordService.getAllBorrowings();

        assertEquals(1, records.size());
        assertEquals("The Great Gatsby", records.get(0).getBook().getTitle());
    }
}
