package com.project.schoolmanagement.springboot;

import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.payload.request.AdminRequest;
import com.project.schoolmanagement.springboot.service.AdminService;
import com.project.schoolmanagement.springboot.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class SpringbootAppApplication  {

	private final UserRoleService userRoleService;
	public SpringbootAppApplication(UserRoleService userRoleService){
		this.userRoleService = userRoleService;
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootAppApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		if (userRoleService.getAllUserRole().isEmpty()){
//			userRoleService.save(RoleType.ADMIN);
//			userRoleService.save(RoleType.MANAGER);
//			userRoleService.save(RoleType.ASSISTANT_MANAGER);
//			userRoleService.save(RoleType.TEACHER);
//			userRoleService.save(RoleType.STUDENT);
//			userRoleService.save(RoleType.ADVISORY_TEACHER);
//			userRoleService.save(RoleType.GUEST_USER);
//		}
//	}
}
