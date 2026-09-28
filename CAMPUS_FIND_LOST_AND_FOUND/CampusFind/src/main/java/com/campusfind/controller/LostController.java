package com.campusfind.controller;

import com.campusfind.dto.LostRequest;
import com.campusfind.entity.LostReport;
import com.campusfind.service.LostReportService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lost")
@CrossOrigin
public class LostController {
    private final LostReportService service;

    public LostController(LostReportService service) {
        this.service = service;
    }

    @PostMapping
    public LostReport create(@Valid @RequestBody LostRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<LostReport> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public LostReport get(@PathVariable Long id) {
        return service.get(id);
    }
}
