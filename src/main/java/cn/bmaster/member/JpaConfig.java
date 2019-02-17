package cn.bmaster.member;

import com.renaissance.core.jpa.ExtenedRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = ExtenedRepositoryImpl.class)
public class JpaConfig {

}