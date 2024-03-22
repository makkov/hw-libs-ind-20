package org.skypro.hw.service;

import org.skypro.hw.entity.Employee;
import org.skypro.hw.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Double getMaxSalaryInDepartment(Integer departmentId) {
        /*
        Пример поиска без стрима:
        List<Employee> employees = employeeService.getAll();

        double maxSalary = Double.MIN_VALUE;
        Employee employeeWithMaxSalary = null;

        for (Employee e : employees) {
            if (e.getDepartmentId() == departmentId && e.getSalary() > maxSalary) {
                maxSalary = e.getSalary();
                employeeWithMaxSalary = e;
            }
        }

        if (employeeWithMaxSalary == null) {
            throw new EmployeeNotFoundException("Сотрудник с максимальной зарплатой не найден");
        } else {
            return employeeWithMaxSalary;
        }
        */

        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .max(Comparator.comparing(Employee::getSalary))
                .map(Employee::getSalary)
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник с максимальной зарплатой не найден"));
    }

    public Double getMinSalaryInDepartment(Integer departmentId) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .min(Comparator.comparing(Employee::getSalary))
                .map(Employee::getSalary)
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник с минимальной зарплатой не найден"));
    }

    public List<Employee> getEmployeesByDepartment(Integer departmentId) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .collect(toList());
    }

    public Double getSalarySumByDepartment(Integer departmentId) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .map(Employee::getSalary)
                .reduce(0.0, Double::sum);
    }

    public Map<Integer, List<Employee>> getAllEmployeesByDepartment() {
        return employeeService.getAll().stream()
                .collect(groupingBy(Employee::getDepartmentId, toList()));
    }
}
