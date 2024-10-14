package com.hellowiftikar.spring.jwt.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hellowiftikar.spring.jwt.service.JwtService;
import com.hellowiftikar.spring.jwt.service.UserDetailsServiceImp;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtService jwtService;
	
	private final UserDetailsServiceImp userDetailsService;
	
	
	


	


	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImp userDetailsService) {
		super();
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}








	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		String authHeder=request.getHeader("Authorization");
		
		if(authHeder==null || authHeder.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token= authHeder.substring(7);
		String username= jwtService.extractUsername(token);
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails =userDetailsService.loadUserByUsername(username);
		
			if(jwtService.isValid(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
