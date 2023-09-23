package pl.marcin.stockmanagerapi.services;

import com.google.common.base.Stopwatch;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

@Slf4j
@Service
public class DocumentReaderService {

    private final FileProcessorService fileProcessorService;


    public DocumentReaderService(FileProcessorService fileProcessorService) {
        this.fileProcessorService = fileProcessorService;
    }

    public void readCSVIsolationRepeatableRead(InputStream file) {
        readFile(file, fileProcessorService::processCSVLineRepeatableRead);
    }

    public void readCSVPessimisticWriterLock(InputStream file) {
        readFile(file, fileProcessorService::processCSVLinePessimisticWriterLock);
    }

    public void readCSVOptimisticLock(InputStream file) {
        readFile(file, fileProcessorService::processCSVLineOptimisticLock);
    }

    private void readFile(InputStream file, BiConsumer<Long, Long> processCSVLine) {

        try (CSVReader reader = new CSVReader(new InputStreamReader(file))) {
            ExecutorService executors = Executors.newFixedThreadPool(10);

            List<String[]> rows = reader.readAll();

            Stopwatch stopwatch = Stopwatch.createStarted();

            for (String[] row : rows) {
                long productId = Long.parseLong(row[0]);
                long quantity = Long.parseLong(row[1]);

                executors.execute(() -> processCSVLine.accept(productId, quantity));
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