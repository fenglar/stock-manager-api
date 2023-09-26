package pl.marcin.stockmanagerapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcin.myanotherpackage.MyBeanOutSideMainPackage;

//@Service
@RequiredArgsConstructor
public class MyServiceInsideMainPackage {

    private final MyBeanOutSideMainPackage myBeanOutSideMainPackage;

    public void execute() {
        myBeanOutSideMainPackage.execute();
    }

}
