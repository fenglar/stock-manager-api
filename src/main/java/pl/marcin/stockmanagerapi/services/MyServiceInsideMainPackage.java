package pl.marcin.stockmanagerapi.services;

import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;
import pl.marcin.myanotherpackage.MyBeanOutSideMainPackage;

@Service
@RequiredArgsConstructor
public class MyServiceInsideMainPackage {

    private final MyBeanOutSideMainPackage myBeanOutSideMainPackage;

    private final ProducerTemplate producerTemplate;

    public void execute() {
        myBeanOutSideMainPackage.execute();
    }

}
