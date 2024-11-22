package com.example.integration_app.configuration;

import com.example.integration_app.configuration.properties.AppCacheProperties;
import com.google.common.cache.CacheBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@EnableConfigurationProperties(AppCacheProperties.class)
public class CacheConfiguration
{
    @Bean
    @ConditionalOnExpression("'${app.cache.cacheType}'.equals('IN_MEMORY')")
    public ConcurrentMapCacheManager inMemoryCacheManager(AppCacheProperties appCacheProperties)
    {
        var cacheManager = new ConcurrentMapCacheManager(){
            @Override
            protected Cache createConcurrentMapCache(String name)
            {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(appCacheProperties.getCaches()
                                        .get(name).getExpire())
                                .build()
                                .asMap(),
                        true
                );
            }
        };
        var cacheNames = appCacheProperties.getCacheNames();
        if (cacheNames != null)
        {
            cacheManager.setCacheNames(cacheNames);
        }
        return cacheManager;
    }
}