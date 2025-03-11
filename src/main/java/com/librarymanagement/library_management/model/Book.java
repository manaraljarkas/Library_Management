package com.librarymanagement.library_management.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String author;

    private int publicationYear;
    @Column(unique = true)
    private String isbn;

    @Column(nullable = false)
    private int loanPeriod = 14;

    private Boolean isDeleted = false;

}
