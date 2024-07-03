package com.example.test.services;

import com.example.test.model.User;
import com.example.test.repository.UsersRepository;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UsersRepository repo;

   
    public UserServiceImpl(UsersRepository repo) {
        this.repo = repo;
    }

    @Override
    public User addUser(User user) {
        return repo.save(user);
    }
}
