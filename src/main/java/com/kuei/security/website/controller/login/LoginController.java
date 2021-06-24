package com.kuei.security.website.controller.login;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuei.security.database.jpa.repository.UserDetailsRepository;

@RestController
@RequestMapping("/login")
public class LoginController
{
	@Resource
	private UserDetailsRepository userDetailsRepository;

	/**
	 * 登录失败返回 401 以及提示信息.
	 *
	 * @return the rest
	 */
	@PostMapping("failure")
	public String loginFailure()
	{
		return String.format("%s%s", HttpStatus.UNAUTHORIZED.value(), "登录失败了，老哥");
	}

	@PostMapping("/success")
	public String loginSuccess() 
	{
		// 登录成功后用户的认证信息 UserDetails会存在 安全上下文寄存器 SecurityContextHolder 中
		User principal = (User)
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = principal.getUsername();
		UserDetails userDetails = userDetailsRepository.loadUserByUsername(username);
		//SysUser sysUser = sysUserService.queryByUsername(username);
		// 脱敏
		//sysUser.setEncodePassword("[PROTECT]");
		//return RestBody.okData(sysUser,"登录成功");
		return String.format("%s%s", HttpStatus.UNAUTHORIZED.value(), "登录成功");
	}
}
