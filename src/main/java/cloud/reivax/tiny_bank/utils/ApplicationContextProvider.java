package cloud.reivax.tiny_bank.utils;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationContextProvider implements ApplicationContextAware {

    public static ApplicationContextProvider INSTANCE;
    private ApplicationContext applicationContext;

    public ApplicationContextProvider() {
        INSTANCE = this;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
