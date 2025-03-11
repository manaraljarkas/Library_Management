package com.librarymanagement.library_management.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.librarymanagement.library_management.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    List<Book> findByIsDeletedFalse();

    Optional<Book> findByIdAndIsDeletedFalse(int id);

    boolean existsByIsbnAndIsDeletedFalse(String isbn);
}
