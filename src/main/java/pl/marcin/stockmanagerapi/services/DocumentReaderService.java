package pl.marcin.stockmanagerapi.services;

import com.google.common.base.Stopwatch;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DocumentReaderService {

    private final FileProcessorService fileProcessorService;


    public DocumentReaderService(FileProcessorService fileProcessorService) {
        this.fileProcessorService = fileProcessorService;
    }


    @Transactional
    public void readCSV(InputStream file) {

        try (CSVReader reader = new CSVReader(new InputStreamReader(file))) {

            List<String[]> rows = reader.readAll();

            Stopwatch stopwatch = Stopwatch.createStarted();

            for (String[] row : rows) {
                long productId = Long.parseLong(row[0]);
                long quantity = Long.parseLong(row[1]);

                fileProcessorService.processCSVLine(productId, quantity);
            }
            stopwatch.stop();
            long elapsedMillis = stopwatch.elapsed(TimeUnit.MILLISECONDS);

            log.info("######################################### " + elapsedMillis);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}