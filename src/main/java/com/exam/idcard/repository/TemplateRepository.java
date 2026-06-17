package com.exam.idcard.repository;

import com.exam.idcard.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findByTemplateNameContainingIgnoreCase(String templateName);
    boolean existsByTemplateName(String templateName);
}
