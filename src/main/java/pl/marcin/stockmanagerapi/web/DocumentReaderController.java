package pl.marcin.stockmanagerapi.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.stockmanagerapi.services.DocumentReaderService;

import java.io.IOException;


@RestController
@RequestMapping("/docs")
public class DocumentReaderController {

    private final DocumentReaderService documentReaderService;

    DocumentReaderController(DocumentReaderService documentReaderService) {
        this.documentReaderService = documentReaderService;
    }

    @PostMapping("/csv")
    public ResponseEntity<String> readCSV(@RequestParam("file") MultipartFile file) throws IOException {
    documentReaderService.readCSVIsolationRepeatableRead(file.getInputStream());
        return ResponseEntity.ok("Successfully processed file");
    }
}
