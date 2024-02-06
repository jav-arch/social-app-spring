package com.jk.socialapp.services;

import com.jk.socialapp.payloads.UserDto;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto user);
    public UserDto updateUser(UserDto user, Integer userId);
    public UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);

}
