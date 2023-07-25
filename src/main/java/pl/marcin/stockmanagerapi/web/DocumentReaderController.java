package pl.marcin.stockmanagerapi.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.stockmanagerapi.services.DocumentReaderService;



@RestController
@RequestMapping("/docs")
public class DocumentReaderController {

    private final DocumentReaderService documentReaderService;

    DocumentReaderController(DocumentReaderService documentReaderService) {
        this.documentReaderService = documentReaderService;
    }

    @GetMapping("/csv")
    public ResponseEntity<String> readCSV(@RequestParam("file") MultipartFile file) {
    documentReaderService.readCSV(file);
        return ResponseEntity.ok("Successfully processed file");
    }
}
