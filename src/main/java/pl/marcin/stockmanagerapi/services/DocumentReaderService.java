package pl.marcin.stockmanagerapi.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class DocumentReaderService {

    private final FileProcessorService fileProcessorService;


    public DocumentReaderService(FileProcessorService fileProcessorService) {
        this.fileProcessorService = fileProcessorService;
    }

    @Transactional
    public void readCSV(MultipartFile file) {

        try (CSVReader reader = new CSVReader(new FileReader((File) file))) {

            List<String[]> rows = reader.readAll();

            for (String[] row : rows) {
                long productId = Long.parseLong(row[0]);
                long quantity = Long.parseLong(row[1]);

                fileProcessorService.processCSVLine(productId, quantity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}