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
import pl.marcin.stockmanagerapi.services.DocumentReaderService;
import pl.marcin.stockmanagerapi.web.DocumentReaderController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class DocumentReaderControllerTest {

    @InjectMocks
    private DocumentReaderController documentReaderController;

    @Mock
    private DocumentReaderService documentReaderService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testReadCSV() throws Exception {
        MockMultipartFile fileMock = new MockMultipartFile("fileMock", "stock-update.csv".getBytes());

        mockMvc.perform(multipart("/docs/csv").file(fileMock)
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isOk())
                .andReturn();

    }

}
