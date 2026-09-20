package com.sentineluba.server.department.controller;

import com.sentineluba.server.common.ApiResponse;
import com.sentineluba.server.department.dto.DepartmentRequest;
import com.sentineluba.server.department.dto.DepartmentResponse;
import com.sentineluba.server.department.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse department = service.createDepartment(request);

        ApiResponse<DepartmentResponse> response =
                new ApiResponse<>(
                        true,
                        "Department created successfully",
                        department
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {

        List<DepartmentResponse> departments = service.getAllDepartments();

        ApiResponse<List<DepartmentResponse>> response =
                new ApiResponse<>(
                        true,
                        "Departments retrieved successfully",
                        departments
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(
            @PathVariable Long id) {

        DepartmentResponse department = service.getDepartmentById(id);

        ApiResponse<DepartmentResponse> response =
                new ApiResponse<>(
                        true,
                        "Department retrieved successfully",
                        department
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse department =
                service.updateDepartment(id, request);

        ApiResponse<DepartmentResponse> response =
                new ApiResponse<>(
                        true,
                        "Department updated successfully",
                        department
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(
            @PathVariable Long id) {

        service.deleteDepartment(id);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        "Department deactivated successfully",
                        null
                );

        return ResponseEntity.ok(response);
    }
}