package pl.marcin.stockmanagerapi.services.singletonprototype;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Slf4j
@Service
@Scope(SCOPE_PROTOTYPE)
public class MyPrototypeBean {

    private final ZonedDateTime now = ZonedDateTime.now();
    private Long numberOfExecutions = 0L;


    public void execute() {
        log.info("Now: {}", now);
        log.info("numberOfExecutions: {}", ++numberOfExecutions);
    }
}
