package ParallelRedisAndDbFetch.repository;

import ParallelRedisAndDbFetch.entity.User;

public interface RedisRepository {

  /**
   *
   * @param user
   */
  void cacheUserDetails(User user);

  /**
   *
   * @param id
   * @return
   */
  User getUserDetails(String id);
}
