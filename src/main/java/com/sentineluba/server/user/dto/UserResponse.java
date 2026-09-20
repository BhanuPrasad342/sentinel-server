package com.sentineluba.server.user.dto;

public class UserResponse {
    private Long id;
    private String employeeId;
    private String name;
    private String email;
    private String role;
    private String status;
    private Long departmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public UserResponse(Long id, String employeeId, String name, String email, String role, Long departmentId, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.departmentId = departmentId;
        this.status = status;
    }
}
