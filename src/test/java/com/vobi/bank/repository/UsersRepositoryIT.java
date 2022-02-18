package com.vobi.bank.repository;

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

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class UsersRepositoryIT {

	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	UserTypeRepository userTypeRepository;
	
	@Test
	@Order(1)
	void debeValidarLasDependencias() {
		assertNotNull(userRepository);
		assertNotNull(userTypeRepository);
	}
	
	@Test
	@Order(2)
	void debeCrearUnUser() {
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
		user= userRepository.save(user);
		
		//Assert		
		assertNotNull(user,"El usuario no debe ser null");
	}
	
	@Test
	@Order(3)
	void debeModificarUnUser() {
		//Arrange		
		Users user=userRepository.findById("mail@mail.com").get();		
		user.setEnable("N");
		
		//Act
		user= userRepository.save(user);
		
		//Assert		
		assertNotNull(user,"El usuario no debe ser null");
	}
	
	@Test
	@Order(4)
	void debeBorrarUnUser() {
		//Arrange
		String userEmail="mail@mail.com";
		Users user=null;
		Optional<Users> userOptional=null;
		
		assertTrue(userRepository.findById(userEmail).isPresent(),"Usuario para eliminar no encontrado");
		
		user=userRepository.findById(userEmail).get();
		
		//Act
		userRepository.delete(user);
		userOptional= userRepository.findById(userEmail);
		
		//Assert
		
		assertFalse(userOptional.isPresent(),"No se pudo eliminar el usuario");
	}

	@Test
	@Order(5)
	void debeConsultarTodosLosUsers() {
		//Arrange
		List<Users> users=null;
		
		//Act
		users=userRepository.findAll();
		users.forEach(user->log.info(user.getName()));
		//Assert
		
		assertFalse(users.isEmpty(),"No se consulto Customers");
	}

}
