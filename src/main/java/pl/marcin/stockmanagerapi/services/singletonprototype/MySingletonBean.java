package pl.marcin.stockmanagerapi.services.singletonprototype;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MySingletonBean {

    private final MyPrototypeBean myPrototypeBean;
    private final ObjectProvider<MyPrototypeBean> beanObjectProvider;
    private final ApplicationContext applicationContext;

    public void executeFromDirectInjection() {
        myPrototypeBean.execute();
    }

    public void executeFromObjectProvider() {
        beanObjectProvider.getIfAvailable().execute();
    }

    public void executeFromApplicationContext() {
        applicationContext.getBean(MyPrototypeBean.class).execute();
    }
}
