package cn.bmaster.member;

import com.renaissance.core.CoreConst;
import com.renaissance.core.handler.CacheHandler;
import com.renaissance.core.utils.EnumUtils;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;

@Configuration
public class CacheConfig {

    private CacheManager cacheManager;

    @Bean
    public Map<String, Enum[]> enumsMap() {
        return EnumUtils.scan("cn.bmaster.member.enums");
    }

    @Bean
    public CacheHandler cacheHandler() {
        // 验证码最多1000个，存60秒
        CacheConfigurationBuilder<String, Object> validationCodeBuilder = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, Object.class, ResourcePoolsBuilder.heap(1000L))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(60)));
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache(CoreConst.CACHE_VALIDATION_CODE, validationCodeBuilder)
                .build(true);
        return new CacheHandler(cacheManager);
    }

    @PreDestroy
    public void destroyCache() {
        cacheManager.close();
    }

}
