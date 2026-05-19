package com.vishal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vishal.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

}
