package com.app.user.mapper;

import com.app.user.dto.EmployeeDto;
import com.app.user.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDto(Employee employee);
    Employee toEntity(EmployeeDto employeeDto);
}