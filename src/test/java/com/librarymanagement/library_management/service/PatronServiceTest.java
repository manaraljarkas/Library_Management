package com.librarymanagement.library_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.librarymanagement.library_management.model.Patron;
import com.librarymanagement.library_management.repository.PatronRepo;

@ExtendWith(MockitoExtension.class)
class PatronServiceTest {

    @Mock
    private PatronRepo patronRepo;

    @InjectMocks
    private PatronService patronService;

    private Patron mockPatron1;
    private Patron mockPatron2;

    @BeforeEach
    void setUp() {
        mockPatron1 = new Patron();
        mockPatron1.setId(1);
        mockPatron1.setFullname("Manar Aljarkas");
        mockPatron1.setEmail("manar@example.com");
        mockPatron1.setIsDeleted(false);

        mockPatron2 = new Patron();
        mockPatron2.setId(2);
        mockPatron2.setFullname("Samar Zain");
        mockPatron2.setEmail("samar@example.com");
        mockPatron2.setIsDeleted(false);
    }

    @Test
    void testGetAllPatrons() {
        when(patronRepo.findByIsDeletedFalse()).thenReturn(Arrays.asList(mockPatron1, mockPatron2));

        List<Patron> patrons = patronService.getAllPatrons();

        assertEquals(2, patrons.size());
        assertEquals("Manar Aljarkas", patrons.get(0).getFullname());
    }

    @Test
    void testGetPatronById_Success() {
        when(patronRepo.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(mockPatron1));

        Patron patron = patronService.getPatronById(1);

        assertNotNull(patron);
        assertEquals("Manar Aljarkas", patron.getFullname());
    }

    @Test
    void testGetPatronById_NotFound() {
        when(patronRepo.findByIdAndIsDeletedFalse(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> patronService.getPatronById(99));
        assertEquals("Patron not found.", exception.getMessage());
    }

    @Test
    void testAddPatron() {
        when(patronRepo.save(any(Patron.class))).thenReturn(mockPatron1);

        Patron savedPatron = patronService.addPatron(mockPatron1);

        assertNotNull(savedPatron);
        assertFalse(savedPatron.getIsDeleted());
        assertEquals("Manar Aljarkas", savedPatron.getFullname());
    }

    @Test
    void testUpdatePatron_Success() {
        when(patronRepo.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(mockPatron1));
        when(patronRepo.save(any(Patron.class))).thenReturn(mockPatron1);

        Patron updatedInfo = new Patron();
        updatedInfo.setFullname("John Smith");
        updatedInfo.setEmail("johnsmith@example.com");

        Patron updatedPatron = patronService.updatePatron(1, updatedInfo);

        assertNotNull(updatedPatron);
        assertEquals("John Smith", updatedPatron.getFullname());
        assertEquals("johnsmith@example.com", updatedPatron.getEmail());
    }

    @Test
    void testDeletePatron() {
        when(patronRepo.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(mockPatron1));

        patronService.deletePatron(1);

        assertTrue(mockPatron1.getIsDeleted());
        verify(patronRepo, times(1)).save(mockPatron1);
    }
}
