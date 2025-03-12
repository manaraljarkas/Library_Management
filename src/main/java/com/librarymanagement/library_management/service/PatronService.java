package com.librarymanagement.library_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.librarymanagement.library_management.model.Patron;
import com.librarymanagement.library_management.repository.PatronRepo;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class PatronService {

    @Autowired
    private PatronRepo repo;

    @Cacheable("patrons")
    public List<Patron> getAllPatrons() {
        return repo.findByIsDeletedFalse();
    }

    @Cacheable(value = "patron", key = "#id")
    public Patron getPatronById(int id) {
        return repo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Patron not found."));
    }

    @CacheEvict(value = {"patrons", "patron"}, allEntries = true)
    public Patron addPatron(Patron patron) {
        patron.setIsDeleted(false);
        return repo.save(patron);
    }

    @Transactional
    @CacheEvict(value = {"patrons", "patron"}, allEntries = true)
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
        return repo.save(existingPatron);
    }

    @Transactional
    @CacheEvict(value = {"patrons", "patron"}, allEntries = true)
    public void deletePatron(int id) {
        Patron patron = getPatronById(id);
        patron.setIsDeleted(true);
        repo.save(patron);
    }
}
