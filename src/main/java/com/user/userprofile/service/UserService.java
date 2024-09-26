package com.user.userprofile.service;

import com.user.userprofile.DTO.UserDTO;
import com.user.userprofile.entity.User;
import com.user.userprofile.security.UserSS;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers(UserSS userDetails);

    UserDTO getUserById(Long id);

    UserDTO createUser(UserDTO userDTO);

    void deleteUser(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO, UserDetails userDetails);

}
