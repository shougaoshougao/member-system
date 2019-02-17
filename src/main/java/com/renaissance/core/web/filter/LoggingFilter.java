package com.renaissance.core.web.filter;

import com.renaissance.core.utils.JsonUtils;
import com.renaissance.core.utils.WebUtils;
import cn.bmaster.member.Constants;
import cn.bmaster.member.service.TokenService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Wilson
 */
public class LoggingFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	private Set<String> excludedPaths = new HashSet<>();

	private String requestPrefix = "REQUEST: ";

	@Autowired
	private TokenService tokenService;

	public LoggingFilter() {

	}

	public LoggingFilter excludePath(String path) {
		excludedPaths.add(path);
		return this;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		String excludedPaths = filterConfig.getInitParameter("excludedPaths");
		if (isNotBlank(excludedPaths)) {
			String[] paths = excludedPaths.split("\\s*,\\s*");
			this.excludedPaths = new HashSet<>(asList(paths));
		}

    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
			throw new ServletException("LoggingFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// exclude match path
		for (String excludedPath : excludedPaths) {
			String requestURI = httpRequest.getRequestURI();
			if (requestURI.startsWith(excludedPath)) {
				filterChain.doFilter(httpRequest, httpResponse);
				return;
			}
		}

		// transfer request
		LoggingHttpServletRequestWrapper requestWrapper = new LoggingHttpServletRequestWrapper(httpRequest);

		// extract request information
		LoggingRequestData loggingRequest = getLoggingRequest(requestWrapper);

		// log request info
		logger.debug(requestPrefix + JsonUtils.toPrettyJson(loggingRequest));

		filterChain.doFilter(requestWrapper, httpResponse);
	}

	@Override
	public void destroy() {
	}

	protected LoggingRequestData getLoggingRequest(LoggingHttpServletRequestWrapper requestWrapper) {

		Long userId = null;
		String sender = requestWrapper.getLocalAddr();
		String method = requestWrapper.getMethod();
		String path = requestWrapper.getRequestURI();
		String postParam = requestWrapper.isFormPost() ? WebUtils.toQueryString(requestWrapper.getParameters()) : null;
		String getParam = (requestWrapper.getQueryString() == null ? "" : requestWrapper.getQueryString());
		String headerJson = JsonUtils.toJson(requestWrapper.getHeaders());
		String bodyJson = requestWrapper.isJsonPost() ? requestWrapper.getContent() : null;
        String authorization = requestWrapper.getHeader("Authorization");
        if(StringUtils.isNotBlank(authorization) && authorization.startsWith("Bearer")) {
			String encodedCredential = authorization.substring("Bearer".length()).trim();
			String credential = new String(Base64.decodeBase64(encodedCredential), Charset.forName("UTF-8"));
			userId = tokenService.getUserId(credential);
        }
		LocalDateTime time = LocalDateTime.now();
		LoggingRequestData loggingRequest = new LoggingRequestData(userId, sender, method, path, postParam, getParam, headerJson, bodyJson, time, Constants.MEMBER_SYSTEM);
		return loggingRequest;
	}

}
