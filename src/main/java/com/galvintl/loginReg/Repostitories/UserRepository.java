package com.galvintl.loginReg.Repostitories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.galvintl.loginReg.Models.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}


