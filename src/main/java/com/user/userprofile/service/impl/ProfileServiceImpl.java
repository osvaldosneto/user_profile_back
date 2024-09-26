package com.user.userprofile.service.impl;

import com.user.userprofile.entity.Profile;
import com.user.userprofile.enumeration.ProfileType;
import com.user.userprofile.repository.Profilerepository;
import com.user.userprofile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private Profilerepository repository;

    @Override
    public List<Profile> getAllProfiles() {
        return repository.findAll();
    }

    @Override
    public Profile createProfile(Profile profile) {
        return repository.save(profile);
    }

    @Override
    public Profile findByProfileId(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Profile findByProfileType(String profileType) {
        return repository.findProfilesByProfileType(ProfileType.valueOf(profileType));
    }
}
