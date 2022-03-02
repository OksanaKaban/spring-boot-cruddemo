package com.springboot.cruddemo.rest;

import com.springboot.cruddemo.dao.EmployeeDAO;
import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private EmployeeService employeeService;
    // quick and dirty: inject EmployeeDAO (use constructor injection)

    @Autowired // optional @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // expose "/employees" endpoint and return list of employees
    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    // add mapping for get /employees/{employeeId}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("employee id not found " + employeeId);
        }
        return employee;
    }

    // add mapping for post /employees - add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        // also just in case they pass an Id in JSON - set Id to 0
        // this is to force a save of new item instead of update
        employee.setId(0);
        employeeService.save(employee);
        return employee;
    }

    // add mapping for put /employees - update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return employee;
    }

    // add mapping for delete /employees/{employeeId}
    @DeleteMapping("/employees/{employeeId}")
public String deleteEmployee(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        if(employee == null) {
            throw new RuntimeException("this employee id not found: " + employeeId);
        }
        employeeService.deleteById(employeeId);
        return "deleted employee id - " + employeeId;
    }


}
