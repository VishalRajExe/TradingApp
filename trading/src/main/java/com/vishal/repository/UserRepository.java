package com.vishal.repository;

import com.vishal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface UserRepository extends JpaRepository<User,Long>{
	User findbyEmail(String email);
}
