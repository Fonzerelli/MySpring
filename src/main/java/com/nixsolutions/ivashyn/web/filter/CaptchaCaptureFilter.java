package com.nixsolutions.ivashyn.web.filter;

//import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for capturing Captcha fields.
 * It's purpose is to store these values internally
 */
public class CaptchaCaptureFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                 FilterChain chain) throws IOException, ServletException {

        if (req.getParameter("recaptcha_response_field") != null) {
//            req.getSession().setAttribute("captchaIsNecessary", true);
            req.getSession().setAttribute("recaptcha_response_field", req.getParameter("recaptcha_response_field"));
            req.getSession().setAttribute("recaptcha_challenge_field", req.getParameter("recaptcha_challenge_field"));
            req.getSession().setAttribute("remoteAddr", req.getRemoteAddr());
        }

        chain.doFilter(req, res);
    }
}
 
