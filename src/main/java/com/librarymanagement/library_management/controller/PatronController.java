package com.librarymanagement.library_management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.librarymanagement.library_management.model.Patron;
import com.librarymanagement.library_management.service.PatronService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PatronController {

    @Autowired
    private PatronService service;

    @GetMapping("/patrons")
    public ResponseEntity<List<Patron>> getAllPatrons() {
        return new ResponseEntity<>(service.getAllPatrons(), HttpStatus.OK);
    }

    @GetMapping("/patrons/{id}")
    public ResponseEntity<Patron> getPatron(@PathVariable int id) {
        return new ResponseEntity<>(service.getPatronById(id), HttpStatus.OK);
    }

    @PostMapping("/patrons")
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron) {
        return new ResponseEntity<>(service.addPatron(patron), HttpStatus.CREATED);
    }

    @PutMapping("/patrons/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable int id, @RequestBody Patron patron) {
        return new ResponseEntity<>(service.updatePatron(id, patron), HttpStatus.OK);
    }

    @DeleteMapping("/patrons/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable int id) {
        service.deletePatron(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}