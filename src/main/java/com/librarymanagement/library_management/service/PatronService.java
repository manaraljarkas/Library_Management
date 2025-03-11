package com.librarymanagement.library_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.librarymanagement.library_management.model.Patron;
import com.librarymanagement.library_management.repository.PatronRepo;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class PatronService {

    @Autowired
    private PatronRepo repo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Patron> getAllPatrons() {
        return repo.findByIsDeletedFalse();
    }

    public Patron getPatronById(int id) {
        return repo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Patron not found."));
    }

    public Patron addPatron(Patron patron) {
        patron.setPassword(passwordEncoder.encode(patron.getPassword()));
        patron.setIsDeleted(false);
        return repo.save(patron);
    }

    @Transactional
    public Patron updatePatron(int id, Patron updatedPatron) {
        Patron existingPatron = getPatronById(id);
        if (updatedPatron.getFullname() != null) {
            existingPatron.setFullname(updatedPatron.getFullname());
        }
        if (updatedPatron.getPhoneNumber() != null) {
            existingPatron.setPhoneNumber(updatedPatron.getPhoneNumber());
        }
        if (updatedPatron.getEmail() != null) {
            existingPatron.setEmail(updatedPatron.getEmail());
        }
        if (updatedPatron.getPassword() != null) {
            existingPatron.setPassword(passwordEncoder.encode(updatedPatron.getPassword()));
        }
        return repo.save(existingPatron);
    }

    @Transactional
    public void deletePatron(int id) {
        Patron patron = getPatronById(id);
        patron.setIsDeleted(true);
        repo.save(patron);
    }
}
