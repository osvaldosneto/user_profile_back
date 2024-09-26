package com.user.userprofile.repository;

import com.user.userprofile.entity.Profile;
import com.user.userprofile.enumeration.ProfileType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Profilerepository extends JpaRepository<Profile, Long> {

    Profile findProfilesByProfileType(ProfileType profileType);

}
