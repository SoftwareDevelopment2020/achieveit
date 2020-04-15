package com.softwaredevelopment.achieveit.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {
    @Value("${redis.host}")
    private String HOST;
    @Value("${redis.port}")
    private int PORT;
    @Value("${redis.password}")
    private String PASSWORD;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(HOST);
        redisStandaloneConfiguration.setPort(PORT);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(PASSWORD));

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder = LettuceClientConfiguration.builder();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfigurationBuilder.build());
    }

    /**
     * 设置缓存过期时间
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfigurationWithTtl(60),
                this.getRedisCacheConfigurationMap() // 指定 key 策略
        );
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        //SsoCache和BasicDataCache进行过期时间配置
        redisCacheConfigurationMap.put("userDetail", this.getRedisCacheConfigurationWithTtl(600));//自定义设置缓存时间
        redisCacheConfigurationMap.put("risk", this.getRedisCacheConfigurationWithTtl(600));//自定义设置缓存时间
        redisCacheConfigurationMap.put("base", this.getRedisCacheConfigurationWithTtl(600));//自定义设置缓存时间

        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.registerModule(new SimpleModule().addDeserializer(
                SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer()));
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(jackson2JsonRedisSerializer))
                .entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }

    class SimpleGrantedAuthorityDeserializer extends StdDeserializer<SimpleGrantedAuthority> {
        public SimpleGrantedAuthorityDeserializer() {
            super(SimpleGrantedAuthority.class);
        }

        @Override
        public SimpleGrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode tree = p.getCodec().readTree(p);
            return new SimpleGrantedAuthority(tree.get("authority").textValue());
        }
    }
}
