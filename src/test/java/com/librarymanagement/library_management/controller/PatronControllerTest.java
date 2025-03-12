package com.librarymanagement.library_management.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagement.library_management.model.Patron;
import com.librarymanagement.library_management.service.PatronService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class PatronControllerTest {

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private Patron mockPatron1;
    private Patron mockPatron2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();

        mockPatron1 = new Patron();
        mockPatron1.setId(1);
        mockPatron1.setFullname("Manar Aljarkas");
        mockPatron1.setEmail("manar@example.com");

        mockPatron2 = new Patron();
        mockPatron2.setId(2);
        mockPatron2.setFullname("Omar Tinawi");
        mockPatron2.setEmail("omar@example.com");
    }

    @Test
    void testGetAllPatrons() throws Exception {
        List<Patron> patrons = Arrays.asList(mockPatron1, mockPatron2);
        when(patronService.getAllPatrons()).thenReturn(patrons);

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].fullname").value("Manar Aljarkas"));
    }

    @Test
    void testGetPatronById() throws Exception {
        when(patronService.getPatronById(1)).thenReturn(mockPatron1);

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullname").value("Manar Aljarkas"));
    }

    @Test
    void testAddPatron() throws Exception {
        when(patronService.addPatron(any(Patron.class))).thenReturn(mockPatron1);

        mockMvc.perform(post("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockPatron1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullname").value("Manar Aljarkas"));
    }

    @Test
    void testUpdatePatron() throws Exception {
        when(patronService.updatePatron(eq(1), any(Patron.class))).thenReturn(mockPatron1);

        mockMvc.perform(put("/api/patrons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockPatron1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullname").value("Manar Aljarkas"));
    }

    @Test
    void testDeletePatron() throws Exception {
        doNothing().when(patronService).deletePatron(1);

        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNoContent());
    }
}
