package com.atc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atc.model.User;
/*
 * author Adilson Arbuez
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
