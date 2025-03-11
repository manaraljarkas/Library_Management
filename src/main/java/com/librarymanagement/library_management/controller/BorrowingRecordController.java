package com.librarymanagement.library_management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.librarymanagement.library_management.model.BorrowingRecord;
import com.librarymanagement.library_management.service.BorrowingRecordService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService service;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable int bookId, @PathVariable int patronId) {
        return new ResponseEntity<>(service.borrowBook(bookId, patronId), HttpStatus.CREATED);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable int bookId, @PathVariable int patronId) {
        return new ResponseEntity<>(service.returnBook(bookId, patronId), HttpStatus.OK);
    }

    @GetMapping("/borrowings")
    public ResponseEntity<List<BorrowingRecord>> getAllBorrowings() {
        return new ResponseEntity<>(service.getAllBorrowings(), HttpStatus.OK);
    }
}