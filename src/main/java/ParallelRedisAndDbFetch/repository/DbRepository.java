package ParallelRedisAndDbFetch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ParallelRedisAndDbFetch.entity.User;

public interface DbRepository extends JpaRepository<User, String> {

}
