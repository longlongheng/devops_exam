package com.exam.idcard;

import com.exam.idcard.model.Profile;
import com.exam.idcard.model.ProfileType;
import com.exam.idcard.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProfileRepositoryTest {

    private final ProfileRepository profileRepository;

    @Autowired
    ProfileRepositoryTest(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Test
    void canSaveProfile() {
        Profile profile = new Profile();
        profile.setFullName("Test Student");
        profile.setEmail("test@example.com");
        profile.setDepartment("IT");
        profile.setProfileType(ProfileType.STUDENT);
        profile.setRegistrationNumber("2026-IT-001");

        Profile saved = profileRepository.save(profile);

        assertEquals("Test Student", saved.getFullName());
        assertTrue(profileRepository.existsByRegistrationNumber("2026-IT-001"));
    }
}
