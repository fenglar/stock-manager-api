package pl.marcin.stockmanagerapi.config;

import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Component;

@Component
@EnableRetry
public class RetryConfig {
}
