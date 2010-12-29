package br.org.ged.direto.model.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.web.FilterInvocation;

import br.org.ged.direto.model.entity.Usuario;

/**
 * @author oleg.zhurakousky@springsource.com
 *
 */
public class SuspendRealTimeVoter implements AccessDecisionVoter {

	private static Set<String> revokedUsers = new HashSet<String>();
	
	@Override
	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	/**
	 * Will vote based on existence of a particular user 
	 * in the "revokedUsers" set;
	 */
	@Override
	public int vote(Authentication authentication, Object object,
			Collection<ConfigAttribute> config) {
		
		System.out.println("REVOKED USERS: "+revokedUsers.toString());
		
		String userName = authentication.getName();
		
		System.out.println(config.toString());
		System.out.println(object.toString());
		
		boolean blocked = false;
		
		//String authorization = "ACCESS_GRANTED";
		
		if (revokedUsers.contains(userName)){
			Usuario o = (Usuario) authentication.getPrincipal();
			//String papel = o.getUsuPapel();
			o.setUsuPapel("USER");
			//o.getAuthorities().remove(new GrantedAuthorityImpl("ROLE_ADMIN"));
			
			//o.getAuthorities().add(new GrantedAuthorityImpl("ROLE_USER"));
			
			List<GrantedAuthority> list = (List<GrantedAuthority>) o.getAuthorities();
			 //list.remove(0);
			// authentication.getAuthorities().remove(new GrantedAuthorityImpl("ROLE_ADMIN"));
			list.add(new GrantedAuthorityImpl("ROLE_US"));
			// authentication.getAuthorities().addAll(list);
			 
			 
			System.out.println(authentication.toString());
			System.out.println(o.getAuthorities().contains(new GrantedAuthorityImpl("ROLE_USER")));
			
			/*Iterator<GrantedAuthority> ite = o.getAuthorities().iterator();
			while(ite.hasNext()){
				GrantedAuthorityImpl g = (GrantedAuthorityImpl) ite.next();
				g.
			}*/
			
			//authentication.
			
			FilterInvocation f = (FilterInvocation) object;
			
			if(f.getRequestUrl().contains("admin")){
				blocked = true;
			}
			
			
		}
		
		return blocked ? ACCESS_DENIED : ACCESS_GRANTED;
		//return revokedUsers.contains(userName) ? ACCESS_DENIED : ACCESS_GRANTED;
		//return ACCESS_GRANTED;
		
	}
	
	/**
	 * 
	 * @param user
	 */
	public void suspend(String user){
		revokedUsers.add(user);
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean isSuspended(String user){
		return revokedUsers.contains(user);
	}
	
	public void grant(String user){
		revokedUsers.remove(user);
	}
	
	
}
