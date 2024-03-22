package org.skypro.hw.controller;

import org.skypro.hw.entity.Employee;
import org.skypro.hw.service.DepartmentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @ExceptionHandler({HttpStatusCodeException.class})
    public String handleException(HttpStatusCodeException e) {
        return "Code: " + e.getStatusCode() + ". Error: " + e.getMessage();
    }

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByDepartment(@PathVariable Integer id) {
        return departmentService.getEmployeesByDepartment(id);
    }

    @GetMapping("/{id}/salary/sum")
    public Double getSalarySumByDepartment(@PathVariable Integer id) {
        return departmentService.getSalarySumByDepartment(id);
    }

    @GetMapping("/{id}/salary/max")
    public Double getMinSalaryInDepartment(@PathVariable Integer id) {
        return departmentService.getMinSalaryInDepartment(id);
    }

    @GetMapping("/{id}/salary/min")
    public Double getMaxSalaryInDepartment(@PathVariable Integer id) {
        return departmentService.getMaxSalaryInDepartment(id);
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> getAllEmployeesByDepartment() {
        return departmentService.getAllEmployeesByDepartment();
    }
}
