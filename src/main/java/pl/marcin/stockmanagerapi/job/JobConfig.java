package pl.marcin.stockmanagerapi.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static pl.marcin.stockmanagerapi.job.StepConfig.PROCESS_STOCK_UPDATES_STEP;

@Configuration
@EnableBatchProcessing
public class JobConfig {

    @Bean
    public Job stockSyncJob(JobBuilderFactory jobBuilderFactory,
                            @Qualifier(PROCESS_STOCK_UPDATES_STEP) Step processStockUpdatesStep,
                            JobLogger jobLogger) {
        return jobBuilderFactory.get("stock-sync-job")
                .start(processStockUpdatesStep)
                .listener(jobLogger)
                .build();
    }
}
