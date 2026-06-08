package com.app.user.service;

import com.app.user.dto.EmployeeDto;
import com.app.user.entity.Employee;
import com.app.user.mapper.EmployeeMapper;
import com.app.user.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    public EmployeeServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmployee_existingEmail_throwsException() {
        EmployeeDto employeeDto = new EmployeeDto("John Doe", "john.doe@example.com", "IT", "Developer");

        when(employeeRepository.existsByEmail(employeeDto.email())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> employeeService.createEmployee(employeeDto));
        assertEquals("Email is already in use.", exception.getMessage());
    }

    @Test
    void createEmployee_savesEmployee() {
        EmployeeDto employeeDto = new EmployeeDto("John Doe", "john.doe@example.com", "IT", "Developer");
        Employee employee = new Employee();

        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeRepository.existsByEmail(employeeDto.email())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);

        assertNotNull(createdEmployee);
        verify(employeeRepository).save(employee);
    }

    @Test
    void getEmployee_employeeExists_returnsEmployee() {
        Long id = 1L;
        Employee employee = new Employee();
        EmployeeDto employeeDto = new EmployeeDto("John Doe", "john.doe@example.com", "IT", "Developer");

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        Optional<EmployeeDto> foundEmployee = employeeService.getEmployee(id);

        assertTrue(foundEmployee.isPresent());
        assertEquals(employeeDto, foundEmployee.get());
    }

    @Test
    void updateEmployee_employeeExists_updatesEmployee() {
        Long id = 1L;
        Employee existingEmployee = new Employee();
        existingEmployee.setId(id);
        EmployeeDto employeeDto = new EmployeeDto("John Doe Updated", "john.doe@example.com", "IT", "Developer");

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeMapper.toDto(existingEmployee)).thenReturn(employeeDto);

        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);

        assertNotNull(updatedEmployee);
        assertEquals(employeeDto.name(), updatedEmployee.name());
    }

    @Test
    void deleteEmployee_callsRepositoryDelete() {
        Long id = 1L;
        employeeService.deleteEmployee(id);
        verify(employeeRepository).deleteById(id);
    }
}