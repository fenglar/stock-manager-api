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

@RequiredArgsConstructor
@Slf4j
public class JobLauncherBoundary {

    private final JobLauncher jobLauncher;
    @Autowired
    private Job stockSyncJob;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Scheduled(cron = "0 */2 * * * ?")
    public void runJob() {
        try {
            log.info("##### START ############");
            jobLauncher.run(stockSyncJob, new JobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterJob
    public void clearDatabaseTable() {
        jdbcTemplate.update("DELETE FROM stock_updates");
        log.info("##### FINISH ############");

    }
}
