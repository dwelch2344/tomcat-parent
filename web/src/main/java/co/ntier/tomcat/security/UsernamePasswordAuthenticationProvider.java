package co.ntier.tomcat.security;

import java.util.Arrays;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UsernamePasswordAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	
	private final String user, password, role;
	
	public UsernamePasswordAuthenticationProvider(String user, String password, String role) {
		super();
		this.user = user;
		this.password = password;
		this.role = role;
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken auth)
			throws AuthenticationException {
		
		String password = (String) auth.getCredentials();
		if( this.user.equalsIgnoreCase(username) && this.password.equals(password)){
			return new User( this.user, this.password, Arrays.asList(new SimpleGrantedAuthority( this.role )) );
		}
		
		throw new BadCredentialsException("Invalid account details");
		
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
	}

}
