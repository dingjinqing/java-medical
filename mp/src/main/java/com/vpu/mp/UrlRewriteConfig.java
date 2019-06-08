package com.vpu.mp;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 * 
 * @author lixinguo
 *
 */
@Configuration
public class UrlRewriteConfig extends UrlRewriteFilter {
	private static final String URL_REWRITE = "classpath:/urlrewrite.xml";

	@Value(URL_REWRITE)
	private Resource resource;

	@Override
	protected void loadUrlRewriter(FilterConfig filterConfig) throws ServletException {
		try {
			// Create a UrlRewrite Conf object with the injected resource
			Conf conf = new Conf(filterConfig.getServletContext(), resource.getInputStream(), resource.getFilename(),
					"@@traceability@@");
			checkConf(conf);
		} catch (IOException ex) {
			throw new ServletException("Unable to load URL rewrite configuration file from " + URL_REWRITE, ex);
		}
	}
}
