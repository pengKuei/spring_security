package com.kuei.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import com.kuei.security.database.jpa.repository.UserDetailsRepository;

@Configuration
public class SecurityConfig
{
	@Bean
	public UserDetailsRepository userDetailsRepository() 
	{
		UserDetailsRepository userDetailsRepository = new UserDetailsRepository();

		UserDetails felordcn = User
			.withUsername("Felordcn")
			.password("{noop}12345")
			.authorities(AuthorityUtils.NO_AUTHORITIES).build();

		userDetailsRepository.addUser(felordcn);
		return userDetailsRepository;
	}

	@Bean
	public UserDetailsManager userDetailsManager(UserDetailsRepository userDetailsRepository)
	{
		return new UserDetailsManager()
		{
			@Override
			public void createUser(UserDetails user)
			{
				userDetailsRepository.addUser(user);
			}

			@Override
			public void updateUser(UserDetails user)
			{
				userDetailsRepository.editUser(user);
			}

			@Override
			public void deleteUser(String username)
			{
				userDetailsRepository.deleteUser(username);
			}

			@Override
			public void changePassword(String oldPassword, String newPassword)
			{
				userDetailsRepository.changePassword(oldPassword, newPassword);
			}

			@Override
			public boolean userExists(String username)
			{
				return userDetailsRepository.userExists(username);
			}

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
			{
				return userDetailsRepository.loadUserByUsername(username);
			}
		};
	}
}
