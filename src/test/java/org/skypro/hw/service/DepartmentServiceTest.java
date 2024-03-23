package org.skypro.hw.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.hw.entity.Employee;
import org.skypro.hw.exception.EmployeeNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    private final int departmentId = 1;
    private final Employee employee1 = new Employee("Ivan", "Ivanov", 80, departmentId);
    private final Employee employee2 = new Employee("Petr", "Petrov", 60, departmentId);
    private final Employee employee3 = new Employee("Oleg", "Olegov", 30, 2);
    private final List<Employee> allEmployees = Arrays.asList(employee1, employee2, employee3);

    @Test
    void getMaxSalaryInDepartment_success() {
        //Подготовка ожидаемого результата
        when(employeeService.getAll()).thenReturn(allEmployees);
        double expectedMaxSalary = employee1.getSalary();

        //Начало теста
        double actualMaxSalaryInFirstDep = departmentService.getMaxSalaryInDepartment(departmentId);
        assertEquals(expectedMaxSalary, actualMaxSalaryInFirstDep);
        assertTrue(employee1.getSalary() >= employee2.getSalary());
    }

    @Test
    void getMinSalaryInDepartment_success() {
        //Подготовка ожидаемого результата
        when(employeeService.getAll()).thenReturn(allEmployees);
        double expectedMinSalary = employee2.getSalary();

        //Начало теста
        double actualMinSalary = departmentService.getMinSalaryInDepartment(departmentId);
        assertEquals(expectedMinSalary, actualMinSalary);
        assertTrue(employee1.getSalary() >= employee2.getSalary());
    }

    @Test
    void getMaxSalaryInDepartment_withEmployeeNotFoundException() {
        //Подготовка ожидаемого результата
        when(employeeService.getAll()).thenReturn(Collections.emptyList());
        String expectedMessage = "404 Сотрудник с максимальной зарплатой не найден";

        //Начало теста
        Exception exception = assertThrows(EmployeeNotFoundException.class,
                () -> departmentService.getMaxSalaryInDepartment(departmentId));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getMinSalaryInDepartment_withEmployeeNotFoundException() {
        //Подготовка ожидаемого результата
        when(employeeService.getAll()).thenReturn(Collections.emptyList());
        String expectedMessage = "404 Сотрудник с минимальной зарплатой не найден";

        //Начало теста
        Exception exception = assertThrows(EmployeeNotFoundException.class,
                () -> departmentService.getMinSalaryInDepartment(departmentId));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getSalarySumByDepartment_success() {
        //Подготовка ожидаемого результата
        when(employeeService.getAll()).thenReturn(allEmployees);
        double expectedSalarySum = employee1.getSalary() + employee2.getSalary();

        //Начало теста
        double actualSalarySum = departmentService.getSalarySumByDepartment(departmentId);
        assertEquals(departmentId, employee1.getDepartmentId());
        assertEquals(departmentId, employee2.getDepartmentId());
        assertEquals(expectedSalarySum, actualSalarySum);
    }

    @Test
    void getEmployeesByDepartment_success() {
        //Подготовка входных данных

        //Подготовка ожидаемого результата
        when(employeeService.getAll()).thenReturn(allEmployees);
        List<Employee> expectedEmployeesFromFirstDep = Arrays.asList(employee1, employee2);

        //Начало теста
        List<Employee> actualEmployeesFromFirstDep = departmentService.getEmployeesByDepartment(departmentId);
        assertEquals(departmentId, employee1.getDepartmentId());
        assertEquals(departmentId, employee2.getDepartmentId());
        assertEquals(expectedEmployeesFromFirstDep, actualEmployeesFromFirstDep);
    }

    @Test
    void getAllEmployeesByDepartment_success() {
        //Подготовка ожидаемого результата
        when(employeeService.getAll()).thenReturn(allEmployees);
        Map<Integer, List<Employee>> expectedGroupedEmployees = new HashMap<>();
        expectedGroupedEmployees.put(departmentId, Arrays.asList(employee1, employee2));
        expectedGroupedEmployees.put(employee3.getDepartmentId(), Collections.singletonList(employee3));

        //Начало теста
        Map<Integer, List<Employee>> actualGroupedEmployees = departmentService.getAllEmployeesByDepartment();
        assertEquals(expectedGroupedEmployees, actualGroupedEmployees);
    }
}
