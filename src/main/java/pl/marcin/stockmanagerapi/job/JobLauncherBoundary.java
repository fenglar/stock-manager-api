package pl.marcin.stockmanagerapi.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobLauncherBoundary {

    private final JobLauncher jobLauncher;
    private final Job stockSyncJob;
    private final JobExplorer jobExplorer;


    //    @Scheduled(cron = "0 */2 * * * ?")
    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void runJob() {
        try {
            log.info("##### START ############");

            JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
                    .getNextJobParameters(stockSyncJob)
                    .toJobParameters();

            jobLauncher.run(stockSyncJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}