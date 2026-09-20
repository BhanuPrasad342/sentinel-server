package com.sentineluba.server.exception;

public class DepartmentCodeAlreadyExistsException extends RuntimeException {

    public DepartmentCodeAlreadyExistsException(String departmentCode) {
        super("Department code already exists: " + departmentCode);
    }
}