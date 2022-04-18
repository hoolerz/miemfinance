package com.hse.miemfinance.model.dto;

import com.hse.miemfinance.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO  extends DataDTO {

	private String username;

	private String email;

	public UserDTO(User entity) {
		this.id = String.valueOf(entity.getId());
		this.username = entity.getUsername();
		this.email = entity.getEmail();
	}

}
