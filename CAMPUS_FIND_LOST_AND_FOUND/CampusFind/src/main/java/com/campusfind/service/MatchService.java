package com.campusfind.service;

import com.campusfind.entity.FoundItem;
import com.campusfind.entity.LostReport;
import com.campusfind.repository.FoundItemRepository;
import com.campusfind.repository.LostReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    private final LostReportRepository lostRepository;
    private final FoundItemRepository foundRepository;

    public MatchService(LostReportRepository lostRepository,
                        FoundItemRepository foundRepository) {
        this.lostRepository = lostRepository;
        this.foundRepository = foundRepository;
    }

    public List<FoundItem> findMatches(Long lostId) {
        LostReport lost = lostRepository.findById(lostId)
                .orElseThrow(() -> new RuntimeException("Lost report not found"));

        List<FoundItem> result = new ArrayList<>();

        for (FoundItem found : foundRepository.findByStatus(FoundItem.FoundStatus.AVAILABLE)) {
            boolean sameCategory =
                    found.getCategory().getId().equals(lost.getCategory().getId());

            boolean keywordMatch =
                    containsKeyword(found.getDescription(), lost.getDescription()) ||
                    containsKeyword(found.getLocation(), lost.getLocation());

            if (sameCategory && keywordMatch) {
                result.add(found);
            }
        }
        return result;
    }

    private boolean containsKeyword(String a, String b) {
        String x = a.toLowerCase();
        String y = b.toLowerCase();

        for (String word : y.split("\\s+")) {
            if (word.length() >= 3 && x.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
