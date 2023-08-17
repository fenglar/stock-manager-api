package pl.marcin.stockmanagerapi.services;

import com.google.common.base.Stopwatch;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DocumentReaderService {

    private final FileProcessorService fileProcessorService;


    public DocumentReaderService(FileProcessorService fileProcessorService) {
        this.fileProcessorService = fileProcessorService;
    }


    public void readCSV(InputStream file) {

        try (CSVReader reader = new CSVReader(new InputStreamReader(file))) {
            ExecutorService executors = Executors.newFixedThreadPool(10);

            List<String[]> rows = reader.readAll();

            Stopwatch stopwatch = Stopwatch.createStarted();

            for (String[] row : rows) {
                long productId = Long.parseLong(row[0]);
                long quantity = Long.parseLong(row[1]);

                executors.execute(() -> fileProcessorService.processCSVLine(productId, quantity));
            }
            executors.shutdown();
            executors.awaitTermination(5, TimeUnit.SECONDS);

            stopwatch.stop();
            log.info("######################################### " + stopwatch);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}