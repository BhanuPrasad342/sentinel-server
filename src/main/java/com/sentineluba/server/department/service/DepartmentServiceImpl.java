package com.sentineluba.server.department.service;

import com.sentineluba.server.department.dto.DepartmentRequest;
import com.sentineluba.server.department.dto.DepartmentResponse;
import com.sentineluba.server.department.entity.Department;
import com.sentineluba.server.department.mapper.DepartmentMapper;
import com.sentineluba.server.department.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import com.sentineluba.server.exception.DepartmentCodeAlreadyExistsException;
import com.sentineluba.server.exception.DepartmentNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger log =
            LoggerFactory.getLogger(DepartmentServiceImpl.class);
    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    public DepartmentServiceImpl(DepartmentRepository repository,
                                 DepartmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        log.info("Creating department with code: {}", request.getDepartmentCode());

        if (repository.existsByDepartmentCode(request.getDepartmentCode())) {

            log.warn("Department code already exists: {}",
                    request.getDepartmentCode());

            throw new DepartmentCodeAlreadyExistsException(
                    request.getDepartmentCode()
            );
        }

        Department department = mapper.toEntity(request);

        Department savedDepartment = repository.save(department);

        log.info("Department created successfully with id: {}",
                savedDepartment.getId());

        return mapper.toResponse(savedDepartment);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        log.info("Fetching department with id: {}", id);

        Department department = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with id: {}", id);
                    return new DepartmentNotFoundException(id);
                });

        return mapper.toResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id,
                                               DepartmentRequest request) {

        log.info("Updating department with id: {}", id);

        Department department = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with id: {}", id);
                    return new DepartmentNotFoundException(id);
                });

        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());

        Department updatedDepartment = repository.save(department);

        log.info("Department updated successfully with id: {}", id);

        return mapper.toResponse(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {

        log.info("Deactivating department with id: {}", id);

        Department department = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with id: {}", id);
                    return new DepartmentNotFoundException(id);
                });

        department.setStatus(false);

        repository.save(department);

        log.info("Department deactivated successfully with id: {}", id);
    }
}