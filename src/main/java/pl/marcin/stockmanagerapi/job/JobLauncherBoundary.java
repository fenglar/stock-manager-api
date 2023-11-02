package pl.marcin.stockmanagerapi.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobLauncherBoundary {

    private final JobLauncher jobLauncher;
    private final Job stockSyncJob;
    private final JdbcTemplate jdbcTemplate;


    @Scheduled(cron = "0 */2 * * * ?")
    public void runJob() {
        try {
            log.info("##### START ############");
            jobLauncher.run(stockSyncJob, new JobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
