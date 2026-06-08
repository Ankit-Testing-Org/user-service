package com.app.user.service;

import com.app.user.dto.EmployeeDto;
import com.app.user.entity.Employee;
import com.app.user.mapper.EmployeeMapper;
import com.app.user.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsByEmail(employeeDto.email())) {
            throw new RuntimeException("Email is already in use.");
        }
        Employee employee = employeeMapper.toEntity(employeeDto);
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeDto> getEmployee(Long id) {
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found."));
        employee.setName(employeeDto.name());
        employee.setEmail(employeeDto.email());
        employee.setDepartment(employeeDto.department());
        employee.setRole(employeeDto.role());
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}