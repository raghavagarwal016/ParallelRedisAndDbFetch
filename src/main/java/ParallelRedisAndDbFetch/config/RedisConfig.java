package ParallelRedisAndDbFetch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

  @Value("${spring.redis.host}")
  private String REDIS_HOSTNAME;
  @Value("${spring.redis.port}")
  private int REDIS_PORT;

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration =
        new RedisStandaloneConfiguration(REDIS_HOSTNAME, REDIS_PORT);
    return new LettuceConnectionFactory(redisStandaloneConfiguration);

  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
    template.setConnectionFactory(redisConnectionFactory());
    template.setKeySerializer(stringSerializer);
    template.setHashKeySerializer(stringSerializer);
    template.setValueSerializer(jdkSerializationRedisSerializer);
    template.setHashValueSerializer(jdkSerializationRedisSerializer);
    template.setEnableTransactionSupport(true);
    template.afterPropertiesSet();
    return template;
  }

}
