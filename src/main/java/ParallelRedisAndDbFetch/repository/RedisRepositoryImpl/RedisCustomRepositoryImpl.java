package ParallelRedisAndDbFetch.repository.RedisRepositoryImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import ParallelRedisAndDbFetch.entity.User;
import ParallelRedisAndDbFetch.repository.RedisRepository;

@Repository
public class RedisCustomRepositoryImpl implements RedisRepository {

  public static final String USER = "USER_";

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void cacheUserDetails(User user) {
    Map ruleHash = objectMapper.convertValue(user, Map.class);
    redisTemplate.opsForHash().put(USER + user.getId(), user.getId(), ruleHash);
  }

  @Override
  public User getUserDetails(String id) {
    Map userMap = (Map) redisTemplate.opsForHash().get(USER + id, id);
    User user = objectMapper.convertValue(userMap, User.class);
    return user;
  }

}
