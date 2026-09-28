package com.campusfind.controller;

import com.campusfind.entity.FoundItem;
import com.campusfind.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin
public class MatchController {
    private final MatchService service;

    public MatchController(MatchService service) {
        this.service = service;
    }

    @GetMapping("/{lostId}")
    public List<FoundItem> matches(@PathVariable Long lostId) {
        return service.findMatches(lostId);
    }
}
