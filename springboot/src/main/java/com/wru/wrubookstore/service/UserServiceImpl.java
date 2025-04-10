package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.UserDto;
import com.wru.wrubookstore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto selectUser(Integer userId) throws Exception{
        return userRepository.selectUser(userId);
    }
    @Override
    public int count() throws Exception {
        return userRepository.count();
    }

    @Override
    public List<UserDto> selectAll() throws Exception {
        return userRepository.selectAll();
    }

    @Override
    public void deleteAll() throws Exception {
        userRepository.deleteAll();
    }

    @Override
    public int insert(UserDto userDto) throws Exception {
        return userRepository.insert(userDto);
    }

    @Override
    public UserDto select(Integer userId) throws Exception {
        return userRepository.select(userId);
    }

    @Override
    public UserDto login(String email, String password) {
        UserDto userDto;
        try {
            userDto = userRepository.selectByEmailAndPassword(email.trim(), password.trim());
        } catch (Exception e) {
            return null;
        }
        return userDto;
    }

    @Override
    public int update(UserDto userDto) throws Exception {
        return userRepository.update(userDto);
    }

    @Override
    public int delete(String email) throws Exception {
        return userRepository.delete(email);
    }

    @Override
    public int isEmailDuplicated(String email) throws Exception {
        return userRepository.isEmailDuplicated(email.trim());
    }
}