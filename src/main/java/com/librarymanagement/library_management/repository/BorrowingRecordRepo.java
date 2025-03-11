package com.librarymanagement.library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.librarymanagement.library_management.model.BorrowingRecord;
import java.util.Optional;

@Repository
public interface BorrowingRecordRepo extends JpaRepository<BorrowingRecord, Integer> {
    Optional<BorrowingRecord> findByBookIdAndPatronIdAndIsReturnedFalse(int bookId, int patronId);
}
