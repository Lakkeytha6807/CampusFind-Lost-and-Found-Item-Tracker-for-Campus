package com.campusfind.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "found_items")
public class FoundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @NotNull
    private LocalDate foundDate;

    @Enumerated(EnumType.STRING)
    private FoundStatus status = FoundStatus.AVAILABLE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "staff_id")
    private User reportedBy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    public enum FoundStatus {
        AVAILABLE, CLAIMED, RETURNED
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDate getFoundDate() { return foundDate; }
    public void setFoundDate(LocalDate foundDate) { this.foundDate = foundDate; }
    public FoundStatus getStatus() { return status; }
    public void setStatus(FoundStatus status) { this.status = status; }
    public User getReportedBy() { return reportedBy; }
    public void setReportedBy(User reportedBy) { this.reportedBy = reportedBy; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
