package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.EmployeeDto;
import com.wru.wrubookstore.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements  EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto login(String email, String password) {
        EmployeeDto employeeDto;
        try {
            employeeDto = employeeRepository.selectByEmailAndPassword(email.trim(), password.trim());
        } catch (Exception e) {
            return null;
        }
        return employeeDto;
    }

    @Override
    public int isEmailDuplicated(String email) throws Exception {
        return employeeRepository.isEmailDuplicated(email.trim());
    }
}
