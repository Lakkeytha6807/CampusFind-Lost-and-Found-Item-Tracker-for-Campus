package com.campusfind.controller;

import com.campusfind.dto.FoundRequest;
import com.campusfind.dto.StatusRequest;
import com.campusfind.entity.FoundItem;
import com.campusfind.service.FoundItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/found")
@CrossOrigin
public class FoundController {
    private final FoundItemService service;

    public FoundController(FoundItemService service) {
        this.service = service;
    }

    @PostMapping
    public FoundItem create(@Valid @RequestBody FoundRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<FoundItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public FoundItem get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}/status")
    public FoundItem updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusRequest request) {
        return service.updateStatus(id, request);
    }
}
