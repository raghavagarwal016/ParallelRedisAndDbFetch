package ParallelRedisAndDbFetch.service;

import ParallelRedisAndDbFetch.dto.UserRequest;
import ParallelRedisAndDbFetch.dto.UserResponse;

public interface UserService {

  /**
   *
   * @param userRequest
   * @return
   */
   String saveUser(UserRequest userRequest);


  /**
   *
    * @param id
   * @return
   */
  UserResponse getUserDetails(String id) throws Exception;
}
