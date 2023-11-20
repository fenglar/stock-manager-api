package pl.marcin.stockmanagerapi.services;

import com.google.common.base.Stopwatch;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.stockmanagerapi.services.singletonprototype.MyPrototypeBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentReaderService {

    private final FileProcessorService fileProcessorService;
    private final MyPrototypeBean myPrototypeBean;

    public long getNumberOfExecution() {
        return myPrototypeBean.execute();
    }

    @Transactional
    public void readCSV(InputStream file) {
        myPrototypeBean.execute();

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