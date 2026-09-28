package com.campusfind.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "lost_reports")
public class LostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @NotNull
    private LocalDate lostDate;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.OPEN;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User reportedBy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    public enum ReportStatus {
        OPEN, MATCHED, RETURNED
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDate getLostDate() { return lostDate; }
    public void setLostDate(LocalDate lostDate) { this.lostDate = lostDate; }
    public ReportStatus getStatus() { return status; }
    public void setStatus(ReportStatus status) { this.status = status; }
    public User getReportedBy() { return reportedBy; }
    public void setReportedBy(User reportedBy) { this.reportedBy = reportedBy; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
