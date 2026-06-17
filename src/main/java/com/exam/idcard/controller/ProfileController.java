package com.exam.idcard.controller;

import com.exam.idcard.model.Profile;
import com.exam.idcard.model.ProfileType;
import com.exam.idcard.service.CodeGeneratorService;
import com.exam.idcard.service.FileStorageService;
import com.exam.idcard.service.PdfService;
import com.exam.idcard.service.ProfileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ProfileController {

    private final ProfileService profileService;
    private final FileStorageService fileStorageService;
    private final CodeGeneratorService codeGeneratorService;
    private final PdfService pdfService;

    public ProfileController(ProfileService profileService,
                             FileStorageService fileStorageService,
                             CodeGeneratorService codeGeneratorService,
                             PdfService pdfService) {
        this.profileService = profileService;
        this.fileStorageService = fileStorageService;
        this.codeGeneratorService = codeGeneratorService;
        this.pdfService = pdfService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("profiles", profileService.findAll());
        return "index";
    }

    @GetMapping("/profiles/new")
    public String createForm(Model model) {
        model.addAttribute("profile", new Profile());
        model.addAttribute("types", ProfileType.values());
        return "profile-form";
    }

    @PostMapping("/profiles")
    public String saveProfile(@ModelAttribute Profile profile,
                              @RequestParam("photo") MultipartFile photo) throws Exception {
        String photoPath = fileStorageService.savePhoto(photo);
        profile.setPhotoPath(photoPath);

        Profile savedProfile = profileService.save(profile);

        String qrText = "ID: " + savedProfile.getRegistrationNumber()
                + ", Name: " + savedProfile.getFullName();

        savedProfile.setQrCodePath(codeGeneratorService.generateQrCode(qrText));
        savedProfile.setBarcodePath(codeGeneratorService.generateBarcode(savedProfile.getRegistrationNumber()));

        profileService.save(savedProfile);

        return "redirect:/";
    }

    @GetMapping("/profiles/{id}")
    public String viewProfile(@PathVariable Long id, Model model) {
        model.addAttribute("profile", profileService.findById(id));
        return "profile-preview";
    }

    @GetMapping("/profiles/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("profile", profileService.findById(id));
        model.addAttribute("types", ProfileType.values());
        return "profile-form";
    }

    @PostMapping("/profiles/{id}/delete")
    public String deleteProfile(@PathVariable Long id) {
        profileService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/profiles/{id}/pdf")
    public ResponseEntity<byte[]> exportPdf(@PathVariable Long id) throws Exception {
        Profile profile = profileService.findById(id);
        byte[] pdf = pdfService.generateProfilePdf(profile);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=id-card.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/profiles/batch")
    public String batch(Model model) {
        List<Profile> profiles = profileService.findAll();
        model.addAttribute("profiles", profiles);
        return "batch";
    }
}
