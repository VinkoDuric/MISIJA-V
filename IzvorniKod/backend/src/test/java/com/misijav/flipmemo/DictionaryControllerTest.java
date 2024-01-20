package com.misijav.flipmemo;

import com.misijav.flipmemo.rest.dict.DictionaryController;
import com.misijav.flipmemo.rest.dict.DictionaryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class DictionaryControllerTest {

    @Mock
    private DictionaryService dictionaryService;

    private DictionaryController dictionaryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dictionaryController = new DictionaryController(dictionaryService);
    }

    @Test
    public void testGetAllDicts_EmptyList() {
        // Arrange
        when(dictionaryService.getAllDicts()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Dictionary>> response = dictionaryController.GET();

        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(Collections.emptyList(), response.getBody());

        verify(dictionaryService, times(1)).getAllDicts();
        verifyNoMoreInteractions(dictionaryService);
    }

    @Test
    public void testGetAllDicts_NonEmptyList() {
        // Arrange
        List<Dictionary> dictionaries = List.of(new Dictionary(), new Dictionary());
        when(dictionaryService.getAllDicts()).thenReturn(dictionaries);

        // Act
        ResponseEntity<List<Dictionary>> response = dictionaryController.GET();

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(dictionaries, response.getBody());

        verify(dictionaryService, times(1)).getAllDicts();
        verifyNoMoreInteractions(dictionaryService);
    }

}