package pl.marcin.myanotherpackage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyBeanOutSideMainPackage {

    public void execute() {
        log.info("On my bean outside the package");
    }
}
