package com.exam.idcard.service;

import com.exam.idcard.model.Profile;
import com.exam.idcard.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Profile findById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public Profile save(Profile profile) {
        if (profile.getRegistrationNumber() == null || profile.getRegistrationNumber().isBlank()) {
            profile.setRegistrationNumber(generateRegistrationNumber(profile));
        }
        return profileRepository.save(profile);
    }

    public void delete(Long id) {
        profileRepository.deleteById(id);
    }

    public List<Profile> search(String keyword) {
        return profileRepository.findByFullNameContainingIgnoreCase(keyword);
    }

    private String generateRegistrationNumber(Profile profile) {
        String year = String.valueOf(Year.now().getValue());
        String department = profile.getDepartment() == null ? "GEN" : profile.getDepartment().toUpperCase();
        long nextNumber = profileRepository.count() + 1;
        return year + "-" + department + "-" + String.format("%03d", nextNumber);
    }
}
