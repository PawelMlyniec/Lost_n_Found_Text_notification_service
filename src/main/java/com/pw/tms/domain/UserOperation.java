package com.pw.tms.domain;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserOperation {
    public static String getAuthenticatedUserId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
