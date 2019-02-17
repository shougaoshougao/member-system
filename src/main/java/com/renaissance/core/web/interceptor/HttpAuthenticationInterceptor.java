/**
 * 
 */
package com.renaissance.core.web.interceptor;

import com.renaissance.core.exception.AuthorizationException;
import com.renaissance.core.exception.ForbiddenException;
import cn.bmaster.member.service.TokenService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Wilson
 *
 */
public class HttpAuthenticationInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpAuthenticationInterceptor.class);
	
	private static final String AUTHORIZATION = "Authorization";
    
	@Autowired
	private TokenService tokenService;

	@Value("${project.http.authentication.mock:false}")
	private boolean mock;
	

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if(mock) {
			return super.preHandle(request, response, handler);
		}
		// 只拦截自己定义的controller方法(去除spring默认的controller)
		if ((handler instanceof HandlerMethod) && !((HandlerMethod)handler).getBeanType().getPackage().getName().startsWith("org.springframework")
				&& !((HandlerMethod)handler).getBeanType().getPackage().getName().startsWith("springfox.documentation.swagger")
		) {
            
            HandlerMethod handlerMethod = (HandlerMethod) handler;
		
            // Find @HttpAuthentication annotation on handler method
            HttpAuthentication httpAuthentication = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), HttpAuthentication.class);
            
            // If @httpAuthentication annotation exists and ignore property is true, bypass the request
            if (httpAuthentication != null && httpAuthentication.ignore()) {
                
            	logger.trace("##preHandler - find @HttpAuthentication annotation and bypass the request.");
            	
            	return super.preHandle(request, response, handlerMethod);
            
            }
            
            // get signature from HttpHeader and user id from Cookie
            String authorization = request.getHeader(AUTHORIZATION);

            logger.trace("##preHandler - Http Header Authorization: {}", authorization);

            // check signature from the request
            if(StringUtils.isNotBlank(authorization) && authorization.startsWith("Bearer")) {
            	
				// Get the base64 encoded credentials from authentication http header
				String encodedCredential = authorization.substring("Bearer".length()).trim();
				
				// Decode credentials
				String credential = new String(Base64.decodeBase64(encodedCredential), Charset.forName("UTF-8"));
                
                logger.trace("##preHandler - trying to do signature verification for token[{}].", credential);
                
                if(tokenService.verifyToken(credential)) { // if token is valid

                	return super.preHandle(request, response, handler);

                } else { // token not valid

					logger.warn("##preHandler - http request do not pass token validation.");

					// send error for invalid http request
					throw new AuthorizationException("401", "Access token validation does not pass");
				}
                

            } 

            logger.error("##preHandler - http request do not match http basic authorization.");
            
            // send error for invalid http request
            throw new AuthorizationException("401", "Http basic authorization required");
            
		}
		
		return super.preHandle(request, response, handler);
	
	}

}
