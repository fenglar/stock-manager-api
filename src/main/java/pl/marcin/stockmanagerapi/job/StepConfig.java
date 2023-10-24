package pl.marcin.stockmanagerapi.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class StepConfig {
    static final String PROCESS_STOCK_UPDATES_STEP = "process-stock-updates-step";

    @Autowired
    private StockUpdateReader stockUpdateReader;
    @Autowired
    private StockUpdateProcessor stockUpdateProcessor;
    @Autowired
    private StockUpdateWriter stockUpdateWriter;

    @Autowired
    private JdbcCursorItemReader<StockUpdate> productItemReader;

    @Bean
    @Qualifier(PROCESS_STOCK_UPDATES_STEP)
    public Step processStockUpdatesStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("processStockUpdatesStep")
                .<StockUpdate, StockUpdate>chunk(10)
                .reader(productItemReader)
                .processor(stockUpdateProcessor)
                .writer(stockUpdateWriter)
                .build();
    }
}
