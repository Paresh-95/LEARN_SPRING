package com.paresh.paresh.Service;


import com.paresh.paresh.Dto.EmployeeDTO;
import com.paresh.paresh.Entity.EmployeeEntity;
import com.paresh.paresh.Repository.EmployeeRepository;
import org.apache.el.util.ReflectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.boot.Banner;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EmployeeService {


    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository,ModelMapper modelMapper){
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }



    public Optional<EmployeeDTO> getEmployeeById(Long id){
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntity1 -> modelMapper.map( employeeEntity1, EmployeeDTO.class) );

    }


    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities =  employeeRepository.findAll();
       return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class))
                .collect(Collectors.toList());

    }

    public EmployeeDTO addEmployee(EmployeeDTO inputEmp) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmp, EmployeeEntity.class);
        EmployeeEntity employeeEntity = employeeRepository.save(toSaveEntity);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }


    public EmployeeDTO updateEmployee(EmployeeDTO inputEmp, Long employeeId) {
        EmployeeEntity empEnt  = modelMapper.map(inputEmp, EmployeeEntity.class);
        empEnt.setId(employeeId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(empEnt);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }


    public boolean isExisitingEmployee(long id){
        return employeeRepository.existsById(id);
    }



    public Boolean deleteEmployeebyId(Long id) {

        if(!isExisitingEmployee(id)){
            return false;
        }
        employeeRepository.deleteById(id);
        return true;

    }

    public EmployeeDTO updatePartialData(Long id, Map<String, Object> updates) {
        if(!isExisitingEmployee(id)){
            return null;
        }
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        //REFLECTION USE TO UPDATE OBJECT PARTIALLY
        updates.forEach((field,value)->{
            Field fieldToUpdate = ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
            fieldToUpdate.setAccessible(true);
            ReflectionUtils.setField(fieldToUpdate,employeeEntity,value);
        });

       return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);

    }
}
