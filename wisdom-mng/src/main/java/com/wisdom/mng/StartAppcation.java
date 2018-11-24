package com.wisdom.mng;

import com.wisdom.mng.support.CustomRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.wisdom.mng"})
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
public class StartAppcation
{
    public static void main( String[] args )
    {
        SpringApplication.run(StartAppcation.class,args);
    }
}
