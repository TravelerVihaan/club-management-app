package com.github.travelervihaan.clubmanagement.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.github.travelervihaan.clubmanagement.model.employers.Employee;
import com.github.travelervihaan.clubmanagement.service.EmployeeService;

@Controller
public class RegisterController {
	
	private EmployeeService employeeService;
	
	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("employee", new Employee());
		return "addEmployee";
	}

	@PostMapping("/register")
	public String addEmployee(@ModelAttribute @Valid Employee employee,
			BindingResult bindResult) {
		if(bindResult.hasErrors())
			return "registerForm";
		else {
			employeeService.addNewEmployee(employee);
			return "registerSuccess";
		}
	}

}