package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.User;
public interface UserRepository extends PagingAndSortingRepository<User, Long>,JpaSpecificationExecutor<User>{
    public User findByUsernameAndPassword(String username,String password);
}

