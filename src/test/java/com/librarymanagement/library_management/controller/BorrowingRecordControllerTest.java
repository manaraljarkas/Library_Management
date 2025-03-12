package com.librarymanagement.library_management.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;

import com.librarymanagement.library_management.model.Book;
import com.librarymanagement.library_management.model.BorrowingRecord;
import com.librarymanagement.library_management.model.Patron;
import com.librarymanagement.library_management.service.BorrowingRecordService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class BorrowingRecordControllerTest {

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    private MockMvc mockMvc;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingRecordController).build();

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
    void testBorrowBook() throws Exception {
        when(borrowingRecordService.borrowBook(1, 1)).thenReturn(borrowingRecord);

        mockMvc.perform(post("/api/borrow/1/patron/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.book.title").value("The Great Gatsby"))
                .andExpect(jsonPath("$.patron.fullname").value("John Doe"))
                .andExpect(jsonPath("$.isReturned").value(false));
    }

    @Test
    void testReturnBook() throws Exception {
        borrowingRecord.setIsReturned(true);
        when(borrowingRecordService.returnBook(1, 1)).thenReturn(borrowingRecord);

        mockMvc.perform(put("/api/return/1/patron/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isReturned").value(true));
    }

    @Test
    void testGetAllBorrowings() throws Exception {
        List<BorrowingRecord> records = Arrays.asList(borrowingRecord);
        when(borrowingRecordService.getAllBorrowings()).thenReturn(records);

        mockMvc.perform(get("/api/borrowings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].book.title").value("The Great Gatsby"));
    }
}
