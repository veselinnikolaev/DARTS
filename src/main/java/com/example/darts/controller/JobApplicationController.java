package com.example.darts.controller;

import com.example.darts.model.entity.JobApplication;
import com.example.darts.model.enumeration.Category;
import com.example.darts.repository.LocationRepository;
import com.example.darts.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobApplicationController {
    private final JobApplicationService service;
    private final LocationRepository locationRepository;

    @GetMapping("/all")
    public ModelAndView jobs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplication> jobsPage = service.getAllJobApplications("all", "all", pageable);
        return new ModelAndView("job_listing")
                .addObject("jobsPage", jobsPage)
                .addObject("service", service)
                .addObject("locations", locationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ModelAndView jobDetails(@PathVariable Long id) {
        JobApplication job = service.getJobApplicationById(id);
        return new ModelAndView("job_details").addObject("job", job);
    }

    @GetMapping("/category/{category}")
    public ModelAndView jobDetails(@PathVariable String category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplication> jobsPage = service.getJobApplicationsByCategory(Category.valueOf(category.toUpperCase()), pageable);
        return new ModelAndView("job_listing")
                .addObject("jobsPage", jobsPage.getContent())
                .addObject("service", service)
                .addObject("locations", locationRepository.findAll());
    }

    @PostMapping("/search")
    public ModelAndView search(@RequestParam(name="keyword", required = false, defaultValue = "all") String keyword,
                               @RequestParam(name="location", required = false, defaultValue = "all") String location,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplication> jobsPage = service.getAllJobApplications(keyword, location, pageable);
        return new ModelAndView("job_listing")
                .addObject("jobsPage", jobsPage)
                .addObject("service", service)
                .addObject("locations", locationRepository.findAll());
    }
}