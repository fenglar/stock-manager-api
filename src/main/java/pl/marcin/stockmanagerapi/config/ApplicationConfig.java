package pl.marcin.stockmanagerapi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(
        basePackages = "pl.marcin.myanotherpackage",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "pl.marcin.stockmanagerapi.repository"
        ))
public class ApplicationConfig {


    @Profile("internal")
    @Component
    @ComponentScan({
            "pl.marcin.stockmanagerapi.web.internal"
    })
    public class MyInternal {
    }

    @Profile("external")
    @Component
    @ComponentScan({
            "pl.marcin.stockmanagerapi.web.external"
    })
    public class MyExternal {
    }
}
