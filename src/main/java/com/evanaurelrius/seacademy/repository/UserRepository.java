package com.evanaurelrius.seacademy.repository;

import com.evanaurelrius.seacademy.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {

    Optional<Account> findBySidAndPassword(String sid, String password);

    Optional<Account> findBySid(String sid);

}
