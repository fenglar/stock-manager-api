package pl.marcin.stockmanagerapi.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marcin.stockmanagerapi.entity.CSVData;
import pl.marcin.stockmanagerapi.services.DocumentReaderService;

import java.util.List;

@RestController
@RequestMapping("/docs")
public class DocumentReaderController {

    private final DocumentReaderService documentReaderService;

    DocumentReaderController(DocumentReaderService documentReaderService){
        this.documentReaderService = documentReaderService;
    }

    @GetMapping("/csv")
    public ResponseEntity<List<CSVData>> readCSV(){
        String filename = "stockUpdate.csv";
        List<CSVData> data = documentReaderService.readCSV(filename);
        return ResponseEntity.ok(data);
    }
}
