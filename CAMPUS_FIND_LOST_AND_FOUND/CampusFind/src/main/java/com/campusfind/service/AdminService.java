package com.campusfind.service;

import com.campusfind.entity.FoundItem;
import com.campusfind.entity.LostReport;
import com.campusfind.repository.FoundItemRepository;
import com.campusfind.repository.LostReportRepository;
import com.campusfind.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final LostReportRepository lostRepository;
    private final FoundItemRepository foundRepository;

    public AdminService(UserRepository userRepository,
                        LostReportRepository lostRepository,
                        FoundItemRepository foundRepository) {
        this.userRepository = userRepository;
        this.lostRepository = lostRepository;
        this.foundRepository = foundRepository;
    }

    public Map<String, Long> dashboard() {
        long pending = lostRepository.findByStatus(LostReport.ReportStatus.OPEN).size()
                + foundRepository.findByStatus(FoundItem.FoundStatus.AVAILABLE).size();

        long matched = lostRepository.findByStatus(LostReport.ReportStatus.MATCHED).size()
                + foundRepository.findByStatus(FoundItem.FoundStatus.CLAIMED).size();

        long returned = lostRepository.findByStatus(LostReport.ReportStatus.RETURNED).size()
                + foundRepository.findByStatus(FoundItem.FoundStatus.RETURNED).size();

        return Map.of(
                "users", userRepository.count(),
                "pending", pending,
                "matched", matched,
                "returned", returned
        );
    }
}
