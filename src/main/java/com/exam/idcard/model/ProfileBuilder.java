package com.exam.idcard.model;

public class ProfileBuilder {

    public static Profile buildDefaultStudent() {
        Profile profile = new Profile();
        profile.setFullName("Default Student");
        profile.setEmail("student@example.com");
        profile.setDepartment("IT");
        profile.setPhone("000000000");
        profile.setProfileType(ProfileType.STUDENT);
        return profile;
    }

    public static Profile buildDefaultEmployee() {
        Profile profile = new Profile();
        profile.setFullName("Default Employee");
        profile.setEmail("employee@example.com");
        profile.setDepartment("HR");
        profile.setPhone("000000000");
        profile.setProfileType(ProfileType.EMPLOYEE);
        return profile;
    }
}
