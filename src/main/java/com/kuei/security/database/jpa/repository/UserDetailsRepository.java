package com.kuei.security.database.jpa.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public class UserDetailsRepository
{
	// 線程安全 Map Java 8 實現
	private static Map<String, UserDetails> USER_MAP = new ConcurrentHashMap<>();

	public void addUser(UserDetails user)
	{
		USER_MAP.putIfAbsent(user.getUsername(), user);
	}
	
	public void editUser(UserDetails user)
	{
		USER_MAP.put(user.getUsername(), user);
	}
	
	public void deleteUser(String username)
	{
		USER_MAP.remove(username);
	}

	public void changePassword(String oldPassword, String newPassword)
	{
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		
		String username = Optional.ofNullable(currentUser)
				.map(o -> o.getName())
				.orElseThrow(() -> new AccessDeniedException("Can't change password as no Authentication object found in context for current user."));
		
		UserDetails user = USER_MAP.get(username);
		//user.s
	}

	public boolean userExists(String username)
	{
		return USER_MAP.containsKey(username);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return USER_MAP.get(username);
	}
}
