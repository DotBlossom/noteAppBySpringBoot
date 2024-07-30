package project.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import project.entity.UserEntity;

@SuppressWarnings("serial")
public class CustomUserDetail implements UserDetails {
	
	private UserEntity userEntity;
	
	public CustomUserDetail(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> collection = new ArrayList();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return userEntity.getRole();
			}
		});
		return collection;
	}



	@Override
	public String getPassword() {
		return userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getUsername();
	}


}
