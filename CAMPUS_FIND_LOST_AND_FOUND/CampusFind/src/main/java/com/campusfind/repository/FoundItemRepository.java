package com.campusfind.repository;

import com.campusfind.entity.FoundItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {
    List<FoundItem> findByStatus(FoundItem.FoundStatus status);
}
