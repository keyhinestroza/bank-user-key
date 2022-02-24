package com.vobi.bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vobi.bank.domain.UserType;
import com.vobi.bank.domain.Users;
import com.vobi.bank.repository.UserTypeRepository;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class UsersServiceIT {

	@Autowired
	UsersService usersService;
	
	@Autowired
	UserTypeRepository userTypeRepository;
	
	@Test
	@Order(1)
	void debeValidarLasDependencias() {
		assertNotNull(usersService);
		assertNotNull(userTypeRepository);
	}
	
	@Test
	@Order(2)
	void debeCrearUnCustomer() throws Exception {
		//Arrange
		Integer idUserType=1;
		
		Users user=null;
		UserType userType = userTypeRepository.findById(idUserType).get();
		
		user= new Users();
		user.setUserEmail("mail@mail.com");
		user.setUserType(userType);
		user.setEnable("Y");
		user.setName("El nombre");
		user.setToken("54845241654548421321sadasdsadkjsad5");
		
		//Act
		user= usersService.save(user);
		
		//Assert
		
		assertNotNull(user,"El customer no debe ser null");
	}
	
	@Test
	@Order(3)
	void debeModificarUnCustomer() throws Exception {
		//Arrange		
		Users user=usersService.findById("mail@mail.com").get();		
		user.setEnable("N");
		
		//Act
		user= usersService.update(user);
		
		//Assert		
		assertNotNull(user,"El usuario no debe ser null");
	}


	@Test
	@Order(4)
	void debeBorrarUnCustomer() throws Exception {		
		//Arrange
		String userEmail="mail@mail.com";
		Users user=null;
		Optional<Users> userOptional=null;
		
		assertTrue(usersService.findById(userEmail).isPresent(),"Usuario para eliminar no encontrado");
		
		user=usersService.findById(userEmail).get();
		
		//Act
		usersService.delete(user);
		userOptional= usersService.findById(userEmail);
		
		//Assert
		
		assertFalse(userOptional.isPresent(),"No se pudo eliminar el usuario");
	}
	
	@Test
	@Order(5)
	void debeConsultarTodosLosCustomers() {
		//Arrange
		List<Users> users=null;
		
		//Act
		users=usersService.findAll();
		users.forEach(user->log.info(user.getName()));
		//Assert
		
		assertFalse(users.isEmpty(),"No se consulto Customers");
	}

}
