package com.campusfind.service;

import com.campusfind.dto.LostRequest;
import com.campusfind.entity.Category;
import com.campusfind.entity.LostReport;
import com.campusfind.entity.User;
import com.campusfind.exception.BadRequestException;
import com.campusfind.repository.CategoryRepository;
import com.campusfind.repository.LostReportRepository;
import com.campusfind.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LostReportService {
    private final LostReportRepository lostRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public LostReportService(LostReportRepository lostRepository,
                             UserRepository userRepository,
                             CategoryRepository categoryRepository) {
        this.lostRepository = lostRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public LostReport create(LostRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new BadRequestException("Category not found"));

        LostReport report = new LostReport();
        report.setDescription(request.description());
        report.setLocation(request.location());
        report.setLostDate(request.lostDate());
        report.setReportedBy(user);
        report.setCategory(category);
        return lostRepository.save(report);
    }

    public List<LostReport> getAll() {
        return lostRepository.findAll();
    }

    public LostReport get(Long id) {
        return lostRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Lost report not found"));
    }
}
