package com.hwatu.configuration;

import com.hwatu.DomainApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by hwatu on 2017. 8. 8..
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = DomainApplication.class)
@EnableTransactionManagement
public class MakeFriendsDomainApplicationContextConfig {
}
