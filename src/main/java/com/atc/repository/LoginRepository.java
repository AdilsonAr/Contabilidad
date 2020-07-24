package com.atc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atc.model.Login;
/*
 * created 27/06/2020
 * author Adilson Arbuez
 */
@Repository
public interface LoginRepository extends JpaRepository<Login, Integer>{
public Login findByLogin(String login);
}
