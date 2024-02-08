package com.kbekind.codingevents.data;

import com.kbekind.codingevents.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}