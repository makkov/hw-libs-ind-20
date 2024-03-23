package org.skypro.hw.service;

import org.apache.commons.lang3.StringUtils;
import org.skypro.hw.entity.Employee;
import org.skypro.hw.exception.EmployeeAlreadyAddedException;
import org.skypro.hw.exception.EmployeeNotFoundException;
import org.skypro.hw.exception.EmployeeStorageIsFullException;
import org.skypro.hw.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();

    private final static int MAX_SIZE = 2;

    public Employee add(String firstName, String lastName, double salary, int departmentId) {
        validateCapitalize(firstName, lastName);
        validateIsAlpha(firstName, lastName);

        if (employees.size() >= MAX_SIZE) {
            throw new EmployeeStorageIsFullException("Массив сотрудников переполнен");
        }

        Employee newEmployee = new Employee(firstName, lastName, salary, departmentId);

        if (employees.contains(newEmployee)) {
            throw new EmployeeAlreadyAddedException("Такой сотрудник уже есть");
        }

        employees.add(newEmployee);
        return newEmployee;
    }

    public Employee find(String firstName, String lastName, double salary, int departmentId) {
        validateCapitalize(firstName, lastName);
        validateIsAlpha(firstName, lastName);

        Employee employeeForFound = new Employee(firstName, lastName, salary, departmentId);
        for (Employee e : employees) {
            if (e.equals(employeeForFound)) {
                return e;
            }
        }

        throw new EmployeeNotFoundException("Такого сотрудника нет");
    }

    public Employee remove(String firstName, String lastName, double salary, int departmentId) {
        validateCapitalize(firstName, lastName);
        validateIsAlpha(firstName, lastName);

        Employee employeeForRemove = new Employee(firstName, lastName, salary, departmentId);

        boolean removeResult = employees.remove(employeeForRemove);
        if (removeResult) {
            return employeeForRemove;
        } else {
            throw new EmployeeNotFoundException("Сотрудник не удален - не был найден в базе");
        }
    }

    public List<Employee> getAll() {
        return employees;
    }

    private void validateCapitalize(String firstName, String lastName) {
        String capitalizeFirstName = StringUtils.capitalize(firstName);
        if (!firstName.equals(capitalizeFirstName)) {
            throw new ValidationException("Имя сотрудника начинается не с большой буквы");
        }

        String capitalizeLastName = StringUtils.capitalize(lastName);
        if (!lastName.equals(capitalizeLastName)) {
            throw new ValidationException("Фамилия сотрудника начинается не с большой буквы");
        }
    }

    private void validateIsAlpha(String firstName, String lastName) {
        if (!StringUtils.isAlpha(firstName)) {
            throw new ValidationException("Имя содержит запрещенные символы");
        }

        if (!StringUtils.isAlpha(lastName)) {
            throw new ValidationException("Фамилия содержит запрещенные символы");
        }
    }
}
