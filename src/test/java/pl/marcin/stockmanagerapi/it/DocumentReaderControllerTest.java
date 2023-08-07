package pl.marcin.stockmanagerapi.it;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.stockmanagerapi.StockManagerApiApplication;
import pl.marcin.stockmanagerapi.config.TestContainerInitializer;
import pl.marcin.stockmanagerapi.services.DocumentReaderService;
import pl.marcin.stockmanagerapi.services.FileProcessorService;
import pl.marcin.stockmanagerapi.web.DocumentReaderController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {StockManagerApiApplication.class}, initializers = {TestContainerInitializer.class}
)
public class DocumentReaderControllerTest {

    @InjectMocks
    private DocumentReaderController documentReaderController;

    @Autowired
    private DocumentReaderService documentReaderService;

    @Mock
    private FileProcessorService fileProcessorService;

    @Value("classpath:csv-mocks/stock-update.csv")
    Resource stateFile;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testReadCSV() throws Exception {
//        MockMultipartFile fileMock = new MockMultipartFile("fileMock", stateFile.);

        documentReaderService.readCSV(stateFile.getInputStream());
//        doNothing().when(documentReaderService).readCSV(any(MultipartFile.class));
        doNothing().when(fileProcessorService).processCSVLine(anyLong(), anyLong());

//        mockMvc.perform(multipart("/docs/csv")
//                        .file(fileMock))
//                .andExpect(status().isOk())
//                .andReturn();

        //assert on db

    }

}
