package ParallelRedisAndDbFetch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ParallelRedisAndDbFetch.dto.UserRequest;
import ParallelRedisAndDbFetch.dto.UserResponse;
import ParallelRedisAndDbFetch.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserDetails(@PathVariable("id") String id) throws Exception {
    UserResponse userResponse = userService.getUserDetails(id);
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  @PostMapping("/save")
  public ResponseEntity<String> getUserDetails(@RequestBody UserRequest userRequest) {
    String id = userService.saveUser(userRequest);
    return new ResponseEntity<>(id, HttpStatus.OK);
  }

}
