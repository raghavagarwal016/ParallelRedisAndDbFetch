package ParallelRedisAndDbFetch.service.impl;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ParallelRedisAndDbFetch.dto.UserRequest;
import ParallelRedisAndDbFetch.dto.UserResponse;
import ParallelRedisAndDbFetch.entity.User;
import ParallelRedisAndDbFetch.repository.DbRepository;
import ParallelRedisAndDbFetch.repository.RedisRepository;
import ParallelRedisAndDbFetch.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private RedisRepository redisRepository;

  @Autowired
  private DbRepository dbRepository;


  @Override
  public String saveUser(UserRequest userRequest) {
    User user = new User();
    BeanUtils.copyProperties(userRequest, user);
    user.setId(UUID.randomUUID().toString());
    User savedUser = dbRepository.save(user);
    redisRepository.cacheUserDetails(savedUser);
    return savedUser.getId();
  }

  @Override
  public UserResponse getUserDetails(String id) throws Exception {
    User user = retrieveData(getUserFromCache(id), getUserFromDb(id)).get();
    UserResponse userResponse = new UserResponse();
    BeanUtils.copyProperties(user, userResponse);
    return userResponse;
  }

  private CompletableFuture<User> getUserFromCache(String id) {
    return CompletableFuture.supplyAsync(() -> redisRepository.getUserDetails(id));
  }

  private CompletableFuture<User> getUserFromDb(String id) {
    return CompletableFuture.supplyAsync(() -> dbRepository.findById(id).get());
  }

  private CompletableFuture<User> retrieveData(CompletableFuture<User> cacheUser, CompletableFuture<User> dbUser) {
    return CompletableFuture.anyOf(cacheUser, dbUser).thenApply(user -> (User) user);
  }

}
