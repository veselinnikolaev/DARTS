package com.example.darts.model.entity;

import com.example.darts.model.binding.JobApplicationBindingModel;
import com.example.darts.model.enumeration.Category;
import com.example.darts.model.enumeration.JobNature;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Table(name = "job_applications")
@Data
@NoArgsConstructor
public class JobApplication extends BaseEntity {
    private String position;
    @ManyToOne
    private Company company;
    @ManyToOne
    private Location location;
    private Integer vacancy;
    @Positive
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private JobNature jobNature;
    private LocalDate posted;
    private LocalDate due;
    @ManyToMany(mappedBy = "jobApplications")
    private List<Skill> requiredSkills;
    @ManyToMany(mappedBy = "jobApplications")
    private List<Experience> requiredExperiences;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToMany(mappedBy = "jobApplications")
    private List<Account> applicants;

    public JobApplication(JobApplicationBindingModel bindingModel, Company company, Account account, Location location,
                          List<Skill> requiredSkills, List<Experience> requiredExperiences) {
        this.position = bindingModel.getPosition();
        this.company = company == null ? new Company(account) : company;
        this.location = location;
        this.vacancy = bindingModel.getVacancy();
        this.salary = BigDecimal.valueOf(bindingModel.getSalary());
        this.jobNature = JobNature.valueOf(bindingModel.getJobNature());
        this.posted = LocalDate.now();
        this.due = LocalDate.parse(bindingModel.getDue());
        this.requiredSkills = requiredSkills;
        this.requiredExperiences = requiredExperiences;
        this.description = bindingModel.getDescription();
        this.category = Category.valueOf(bindingModel.getCategory());
    }
}
