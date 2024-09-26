package com.user.userprofile.service;

import com.user.userprofile.entity.Profile;

import java.util.List;

public interface ProfileService {

    List<Profile> getAllProfiles();

    Profile createProfile(Profile profile);

    Profile findByProfileId(Long id);

    Profile findByProfileType(String profileType);

}
