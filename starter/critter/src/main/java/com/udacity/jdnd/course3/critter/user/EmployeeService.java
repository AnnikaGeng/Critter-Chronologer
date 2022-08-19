package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.getOne(id);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        DayOfWeek day = employeeRequestDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skills = employeeRequestDTO.getSkills();

        List<Employee> employees = getAllEmployees();
        List<Employee> availableEmployee = new ArrayList<>();

        employees.forEach(employee -> {
            if(employee.getDaysAvailable().contains(day) && employee.getSkills().containsAll(skills)) {
                availableEmployee.add(employee);
            }
        });
        return availableEmployee;
    }
}






















