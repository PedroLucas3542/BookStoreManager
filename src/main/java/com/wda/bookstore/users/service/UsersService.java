package com.wda.bookstore.users.service;

import com.wda.bookstore.users.mapper.UsersMapper;
import com.wda.bookstore.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final static UsersMapper usersMapper = UsersMapper.INSTANCE;

    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
}
