package com.nixsolutions.ivashyn.util;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Created by Dmytro Ivashyn
 * Date: 4/1/15
 */
@Service
@Scope(value = "session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class LoginUtil {

    public static final int MAX_UNCAPTURED_LOGIN_ATTEMPTS_NUMBER = 3;
    private int failedLoginCounter = 0;

    public boolean captchaIsNecessary() {
        return failedLoginCounter >= MAX_UNCAPTURED_LOGIN_ATTEMPTS_NUMBER;
    }

    public void resetLoginCounter() {
        failedLoginCounter = 0;
    }

    public void incrementLoginCounter() {
        failedLoginCounter++;
    }

}
