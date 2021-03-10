package br.com.cjr.apirest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cjr.apirest.model.OAuthUser;
import br.com.cjr.apirest.model.Usuario;
import br.com.cjr.apirest.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuario = usuarioRepository.findByEmailAndAtivo(username, true);
		if(usuario.isPresent()) {
			Usuario user =  usuario.get();
			return new OAuthUser(user.getEmail(), user.getPassword(), user.getNome(), user.getPrimeiroAcesso(),
								 user.getAceiteTermosUso(), user.getId(),  user.getAuthorities());
		}
		
		throw new UsernameNotFoundException("Dados inválidos ou usuário inativo!");
	}

}
