package com.librarymanagement.library_management.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.librarymanagement.library_management.model.Patron;

@Repository
public interface PatronRepo extends JpaRepository<Patron, Integer> {
    List<Patron> findByIsDeletedFalse();
    Optional<Patron> findByIdAndIsDeletedFalse(int id);
}
