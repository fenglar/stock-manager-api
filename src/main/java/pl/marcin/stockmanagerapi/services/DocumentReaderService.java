package pl.marcin.stockmanagerapi.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.stockmanagerapi.entity.CSVData;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.repository.DocumentDataRepository;
import pl.marcin.stockmanagerapi.repository.ProductRepository;

import javax.persistence.LockModeType;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class DocumentReaderService {

    private final DocumentDataRepository documentDataRepository;

    private final ProductRepository productRepository;


    public DocumentReaderService(DocumentDataRepository documentDataRepository, ProductRepository productRepository){
        this.documentDataRepository = documentDataRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public List<CSVData> readCSV(String filename) {
        List<CSVData> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filename))) {

            int maxThreads = 10;
            ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

            String[] row;
            while ((row = reader.readNext()) != null) {
                String[] finalRow = row;
                executor.execute(() -> {
                    long productId = Long.parseLong(finalRow[0]);
                    long quantity = Long.parseLong(finalRow[1]);

                    CSVData csvData = new CSVData(productId, quantity);

//                    synchronized (data) {
//                        data.add(csvData);
//                    }

                    csvData = documentDataRepository.save(csvData);
                    data.add(csvData);
                });
            }

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return data;
    }


    public List<CSVData> readCSVasStream(String filename) {
        List<CSVData> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filename))) {

            List<String[]> rows = reader.readAll();

            rows.parallelStream().forEach(row -> {
                Long productId = Long.parseLong(row[0]);
                Long quantity = Long.parseLong(row[1]);

                CSVData csvData = new CSVData(productId, quantity);

                synchronized (data) {
                    data.add(csvData);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    @Transactional
    public List<CSVData> readCSVfinalVersion(String filename) {
        List<CSVData> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filename))) {

            List<String[]> rows = reader.readAll();

            for (String[] row : rows) {
                long productId = Long.parseLong(row[0]);
                long quantity = Long.parseLong(row[1]);

                processCSVLine(productId, quantity, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    @Transactional
    protected void processCSVLine(Long productId, Long quantity, List<CSVData> data) {
        CSVData csvData = DocumentDataRepository.findByProductId(productId, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

        csvData.setQuantity(csvData.getQuantity() + quantity);

        csvData = DocumentDataRepository.save(csvData);

        data.add(csvData);
    }

    @Transactional
    protected void processCSVLine2(Long productId, Long quantity) {
        Product product = productRepository.findByProductId(productId, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

        product.getStock().forEach(stock -> stock.setCurrentQuantity(stock.getCurrentQuantity() + quantity));

         productRepository.save(product);
    }


}