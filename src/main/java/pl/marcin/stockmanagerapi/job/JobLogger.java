package pl.marcin.stockmanagerapi.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


@Slf4j
@Service
public class JobLogger implements JobExecutionListener {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";



    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("############ START [{}] #########",
        new SimpleDateFormat(DATE_TIME_FORMAT).format(jobExecution.getStartTime()));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Instant endTime = Objects.requireNonNull(jobExecution.getEndTime()).toInstant();
        Instant startTime = Objects.requireNonNull(jobExecution.getStartTime()).toInstant();
        long runTimeInSeconds = ChronoUnit.SECONDS.between(startTime, endTime);

        log.info(String.format("####### Job finished [%s] status [%s]",
                new SimpleDateFormat(DATE_TIME_FORMAT).format(jobExecution.getEndTime()),
                jobExecution.getExitStatus()));
        log.info("###### jobs runs for [{}] seconds", runTimeInSeconds);

    }
}
