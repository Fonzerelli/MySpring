package com.myspring.ivashyn.web.filter;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter for verifying if the submitted Captcha fields
 * are valid.
 * <p/>
 * This filter also allows you to set a proxy if needed
 */
public class CaptchaVerifierFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaVerifierFilter.class);

    private Boolean useProxy;
    private String proxyPort;
    private String proxyHost;
    private String failureUrl;
    private String privateKey;

    // Inspired by log output: AbstractAuthenticationProcessingFilter.java:unsuccessfulAuthentication:320)
    // Delegating to authentication failure handlerorg.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler@15d4273
    private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                 FilterChain chain) throws IOException, ServletException {

        if (!req.getServletPath().contains("signUp")) {

            boolean passCaptureFilter = false;
            if (req.getSession().getAttribute("passCaptureFilter") != null) {
                passCaptureFilter = (Boolean) req.getSession().getAttribute("passCaptureFilter");
            }

            if (passCaptureFilter) {
                req.getSession().setAttribute("passCaptureFilter", false);
                chain.doFilter(req, res);
            } else {
                boolean captchaIsNecessary = false;
                if (req.getSession().getAttribute("captchaIsNecessary") != null) {
                    captchaIsNecessary = (Boolean) req.getSession().getAttribute("captchaIsNecessary");

                    if (captchaIsNecessary) {

                        // Create a new recaptcha (by Soren Davidsen)
                        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();

                        // Set the private key (assigned by Google)
                        reCaptcha.setPrivateKey(privateKey);

                        // Assign proxy if needed
                        if (useProxy) {
                            Properties systemSettings = System.getProperties();
                            systemSettings.put("http.proxyPort", proxyPort);
                            systemSettings.put("http.proxyHost", proxyHost);
                        }

                        try {
                            // Send HTTP request to validate user's Captcha
                            ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(
                                    (String) req.getSession().getAttribute("remoteAddr"),
                                    (String) req.getSession().getAttribute("recaptcha_challenge_field"),
                                    (String) req.getSession().getAttribute("recaptcha_response_field"));

                            // Check if valid
                            if (!reCaptchaResponse.isValid()) {
                                LOGGER.debug("Captcha is invalid!");

                                // Redirect user to login page
                                failureHandler.setDefaultFailureUrl(failureUrl);
                                failureHandler.onAuthenticationFailure(req, res, new BadCredentialsException("Captcha invalid!"));
                                req.getSession().setAttribute("captcha_error", Boolean.TRUE);

                            } else {
                                LOGGER.debug("Captcha is valid!");
                                req.getSession().setAttribute("captcha_error", Boolean.FALSE);
                            }
                        } catch (Exception e) {
                            LOGGER.debug("Error validating captcha!", e);
                            failureHandler.setDefaultFailureUrl(failureUrl);
                            failureHandler.onAuthenticationFailure(req, res, new BadCredentialsException("Captcha invalid!"));
                            req.getSession().setAttribute("captcha_error", Boolean.TRUE);
                        }

                        req.getSession().setAttribute("captchaIsNecessary", false);
                    }
                }
            }
        }
        chain.doFilter(req, res);

    }

    public Boolean getUseProxy() {
        return useProxy;
    }

    public void setUseProxy(Boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getFailureUrl() {
        return failureUrl;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
 
