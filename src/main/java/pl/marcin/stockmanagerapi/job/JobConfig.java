package pl.marcin.stockmanagerapi.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
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
                            JobLogger jobLogger)
    {
        return jobBuilderFactory.get("stock-sync-job")
                .start(processStockUpdatesStep)
                .listener(jobLogger)
                .incrementer(parameters ->
                        new JobParametersBuilder().addLong("runId", System.currentTimeMillis()).toJobParameters())
                .build();
    }
}
