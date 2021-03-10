package br.com.cjr.apirest.config.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import br.com.cjr.apirest.model.OAuthUser;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		OAuthUser usuario = (OAuthUser) authentication.getPrincipal();
		
		Map<String, Object> addInfo = new HashMap<>();
		addInfo.put("id", usuario.getId());
		addInfo.put("nomeUsuario", usuario.getNomeUsuario());
		addInfo.put("emailUsuario", usuario.getEmail());
		addInfo.put("primeiroAcesso", usuario.getPrimeiroAcesso());
		addInfo.put("aceiteTermosUso", usuario.getAceiteTermosUso());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
		return accessToken;
	}

}