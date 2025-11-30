package com.paresh.paresh.Controllers;


import com.paresh.paresh.Dto.EmployeeDTO;
import com.paresh.paresh.Entity.EmployeeEntity;
import com.paresh.paresh.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "/hello")
    public String hello(){
        return "Hello World";
    }

    @GetMapping(path = "/{id}")
    public EmployeeEntity getEmployeeById(@PathVariable Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<EmployeeEntity> getAllEmployee(){
        return employeeRepository.findAll();
    }

   @PostMapping
    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity inputEmp){
        return employeeRepository.save(inputEmp);

   }



}
