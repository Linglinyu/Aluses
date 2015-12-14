package cn.ruihe.aluses.common;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class BeanDefinitionRegistryPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer
        implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // Nothing, just to run before other {@link PropertySourcesPlaceholderConfigurer}
        // (non-{@link BeanDefinitionRegistryPlaceholderConfigurer}) are instantiated.
    }

}
