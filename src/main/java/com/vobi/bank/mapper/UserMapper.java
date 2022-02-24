package com.vobi.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vobi.bank.domain.Users;
import com.vobi.bank.dto.UsersDTO;

@Mapper
public interface UserMapper {

	@Mapping(source="userType.ustyId",target="ustyId")
	public UsersDTO userToUserDTO(Users user);
	
	@Mapping(target="userType.ustyId",source="ustyId")
	public Users userDTOToUser(UsersDTO userDTO);
	
	public List<UsersDTO> userListToUserDTOList(List<Users> user);

	public List<Users> userDTOListToUserList(List<UsersDTO> userDTOs);
}
