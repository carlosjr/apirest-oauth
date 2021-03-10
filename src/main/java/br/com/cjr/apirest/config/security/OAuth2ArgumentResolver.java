package br.com.cjr.apirest.config.security;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import br.com.cjr.apirest.model.request.BaseRequest;

@ControllerAdvice
public class OAuth2ArgumentResolver extends RequestBodyAdviceAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return BaseRequest.class.isAssignableFrom(methodParameter.getParameterType());
	}

	@Override
	public Object afterBodyRead(final Object body, final HttpInputMessage inputMessage, final MethodParameter parameter,
			final Type targetType, final Class<? extends HttpMessageConverter<?>> converterType) {
		final Object obj = super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
		SecurityContextHolder.getContext();
		List<String> tokens = inputMessage.getHeaders().get("Authorization");
		if ((tokens != null) && !tokens.isEmpty()) {
			String token = tokens.get(0);
			if ((token != null) && token.startsWith("Bearer ")) {
				token = token.substring(7);
			}
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
			((BaseRequest) obj).setIdUsuario((Integer) accessToken.getAdditionalInformation().get("id"));
			((BaseRequest) obj).setNomeUsuario((String) accessToken.getAdditionalInformation().get("nomeUsuario"));
		}
		((BaseRequest) obj).setHost(inputMessage.getHeaders().getHost().toString());

		return obj;
	}
}
