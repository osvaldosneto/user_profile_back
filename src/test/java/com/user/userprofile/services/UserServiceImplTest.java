package com.user.userprofile.services;

import com.user.userprofile.DTO.UserDTO;
import com.user.userprofile.entity.Profile;
import com.user.userprofile.entity.User;
import com.user.userprofile.enumeration.ProfileType;
import com.user.userprofile.repository.UserRepository;
import com.user.userprofile.security.UserSS;
import com.user.userprofile.service.ProfileService;
import com.user.userprofile.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private ProfileService profileService;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        // Arrange
        doNothing().when(repository).deleteById(1L);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        // Arrange
        UserDTO userDTO = new UserDTO(null, "updatedUser", "updatedEmail@example.com", "987654321", "1990-01-01", "UpdatedFirst", "UpdatedLast", "ADMIN", "newPassword");
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("existingUser");

        Profile profile = new Profile();
        profile.setProfileType(ProfileType.ADMIN);
        Set<Profile> setProfile = new HashSet<>();
        setProfile.add(profile);

        when(repository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(profileService.findByProfileType("ADMIN")).thenReturn(profile);
        when(encoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(repository.save(any(User.class))).thenReturn(existingUser);

        UserDetails userDetails = new UserSS(1L, "admin", "password", setProfile);

        // Act
        UserDTO result = userService.updateUser(1L, userDTO, userDetails);

        // Assert
        assertNotNull(result);
        assertEquals("updatedUser", result.getUsername());
        assertEquals("updatedEmail@example.com", result.getEmail());
    }
}
