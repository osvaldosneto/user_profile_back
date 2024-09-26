package com.user.userprofile.service.impl;

import com.user.userprofile.DTO.UserDTO;
import com.user.userprofile.entity.Profile;
import com.user.userprofile.entity.User;
import com.user.userprofile.repository.UserRepository;
import com.user.userprofile.security.UserSS;
import com.user.userprofile.service.ProfileService;
import com.user.userprofile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private ProfileService profileService;
    private PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder, ProfileService profileService){
        this.repository = repository;
        this.encoder = encoder;
        this.profileService = profileService;
    }

    @Override
    public List<UserDTO> getAllUsers(UserSS userDetails) {
        List<User> users = new ArrayList<>();
        boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if(isAdmin){
            users = repository.findAll();
        } else {
            User user = getUser(userDetails.getId());
            users.add(user);
        }
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    private User getUser(Long id){
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToUser(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        User newUser = repository.save(user);
        return convertToDTO(newUser);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO, UserDetails userDetails) {

        User existingUser = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());

        LocalDate localDate = LocalDate.parse(userDTO.getBirthdate());
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        existingUser.setBirthdate(date);

        boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (isAdmin && userDTO.getProfile() != null) {
            Profile profile = profileService.findByProfileType(userDTO.getProfile());
            existingUser.setProfile(profile);
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(encoder.encode(userDTO.getPassword()));
        }

        User updatedUser = repository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setBirthdate(user.getBirthdate().toString());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setProfile(user.getProfile().getProfileType().getDescription());
        return userDTO;
    }

    private User convertToUser(UserDTO userDTO){

        User user = new User();
        Profile profile = profileService.findByProfileType(userDTO.getProfile());
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setPhone(userDTO.getPhone());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setProfile(profile);

        LocalDate localDate = LocalDate.parse(userDTO.getBirthdate());
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        user.setBirthdate(date);

        return user;
    }

}
