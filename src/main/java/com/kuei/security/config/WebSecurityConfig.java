package com.kuei.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http
			.csrf()
				.disable()
			.cors()
			.and()
			.authorizeRequests()
				.anyRequest()
					.authenticated()
			.and()
			.formLogin()
				//.loginPage("/login") // 登入頁面默認 /login
				.loginProcessingUrl("/process") // 实际表单向后台提交用户信息的 Action ，再由过滤器UsernamePasswordAuthenticationFilter 拦截处理，该 Action 其实不会处理任何逻辑
				.usernameParameter("username") // 定義用戶參數名稱，默認值 username
				.passwordParameter("password") // 定義用戶參數密碼名稱，默認值 password
				//.successHandler(successHandler) // 自定义认证成功处理器，可替代上面所有的 success 方式
				//.defaultSuccessUrl("/home", true) // 如果 alwaysUse 为 true 只要进行认证流程而且成功，会一直跳转到此。一般推荐默认值 false
				.successForwardUrl("/login/success") // 效果等同于上面 defaultSuccessUrl 的 alwaysUse 为 true 但是要注意 RequestMethod 。
				//.failureHandler(authenticationFailureHandler) // 自定义失败处理器，可替代上面所有的 failure 方式
				//.failureUrl("/login-fail") // 登入失敗後的路徑，通常只有在前後端合併的服務才會使用
				.failureForwardUrl("/login/failure") // 登录失败会转发到此， 一般前后分离用到它。 可定义一个 Controller （控制器）来处理返回值,但是要注意 RequestMethod

				.permitAll(true) // form 表单登录是否放开
			;
	}
}
