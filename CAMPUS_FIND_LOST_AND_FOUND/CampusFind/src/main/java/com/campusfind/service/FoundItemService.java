package com.campusfind.service;

import com.campusfind.dto.FoundRequest;
import com.campusfind.dto.StatusRequest;
import com.campusfind.entity.Category;
import com.campusfind.entity.FoundItem;
import com.campusfind.entity.User;
import com.campusfind.exception.BadRequestException;
import com.campusfind.repository.CategoryRepository;
import com.campusfind.repository.FoundItemRepository;
import com.campusfind.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoundItemService {
    private final FoundItemRepository foundRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public FoundItemService(FoundItemRepository foundRepository,
                            UserRepository userRepository,
                            CategoryRepository categoryRepository) {
        this.foundRepository = foundRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public FoundItem create(FoundRequest request) {
        User staff = userRepository.findById(request.staffId())
                .orElseThrow(() -> new BadRequestException("Staff user not found"));

        if (staff.getRole() != User.Role.STAFF && staff.getRole() != User.Role.ADMIN) {
            throw new BadRequestException("Only staff or admin can report found items");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new BadRequestException("Category not found"));

        FoundItem item = new FoundItem();
        item.setDescription(request.description());
        item.setLocation(request.location());
        item.setFoundDate(request.foundDate());
        item.setReportedBy(staff);
        item.setCategory(category);
        return foundRepository.save(item);
    }

    public List<FoundItem> getAll() {
        return foundRepository.findAll();
    }

    public FoundItem get(Long id) {
        return foundRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Found item not found"));
    }

    public FoundItem updateStatus(Long id, StatusRequest request) {
        FoundItem item = get(id);

        User actor = userRepository.findById(request.userId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        boolean isAdmin = actor.getRole() == User.Role.ADMIN;
        boolean isReportingStaff =
                actor.getRole() == User.Role.STAFF &&
                actor.getId().equals(item.getReportedBy().getId());

        if (!isAdmin && !isReportingStaff) {
            throw new BadRequestException(
                    "Only an admin or the reporting staff member can change this item's status"
            );
        }

        if (request.status() == FoundItem.FoundStatus.RETURNED &&
                item.getStatus() != FoundItem.FoundStatus.CLAIMED) {
            throw new BadRequestException(
                    "A found item must be CLAIMED before it can be RETURNED"
            );
        }

        item.setStatus(request.status());
        return foundRepository.save(item);
    }
}
