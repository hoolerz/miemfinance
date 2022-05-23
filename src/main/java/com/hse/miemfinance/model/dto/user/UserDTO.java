package com.hse.miemfinance.model.dto.user;

import com.hse.miemfinance.model.dto.DataDTO;
import com.hse.miemfinance.model.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends DataDTO {

	private String username;

	private String email;

	private String preferredName;

	private String attachmentId;

	public UserDTO(User entity) {
		super(String.valueOf(entity.getId()));
		this.username = entity.getUsername();
		this.email = entity.getEmail();
		this.preferredName = entity.getPreferredName();
		//this.attachmentId = Optional.ofNullable(entity.getAttachment()).get().getEntity().getId();
	}

}
