package pl.marcin.stockmanagerapi.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.stockmanagerapi.services.DocumentReaderService;
import pl.marcin.stockmanagerapi.services.FileProcessorService;
import pl.marcin.stockmanagerapi.web.DocumentReaderController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class DocumentReaderControllerTest {

    @InjectMocks
    private DocumentReaderController documentReaderController;

    @Mock
    private DocumentReaderService documentReaderService;

    @Mock
    private FileProcessorService fileProcessorService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testReadCSV() throws Exception {
        MockMultipartFile fileMock = new MockMultipartFile("fileMock", "stock-update.csv".getBytes());

        doNothing().when(documentReaderService).readCSV(any(MultipartFile.class));
        doNothing().when(fileProcessorService).processCSVLine(anyLong(), anyLong());

        mockMvc.perform(get("/docs/csv")
                        .content(fileMock.getBytes())
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isOk())
                .andReturn();

    }

}
