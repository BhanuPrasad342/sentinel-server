package com.sentineluba.server.department.mapper;

import com.sentineluba.server.department.dto.DepartmentRequest;
import com.sentineluba.server.department.dto.DepartmentResponse;
import com.sentineluba.server.department.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequest request) {

        Department department = new Department();

        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());

        return department;
    }

    public DepartmentResponse toResponse(Department department) {

        DepartmentResponse response = new DepartmentResponse();

        response.setId(department.getId());
        response.setDepartmentCode(department.getDepartmentCode());
        response.setDepartmentName(department.getDepartmentName());
        response.setDescription(department.getDescription());
        response.setStatus(department.getStatus());

        return response;
    }
}