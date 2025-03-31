package com.scm.message;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public static void removeMessage() {
        try {
            ServletRequestAttributes attr = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

            if (attr != null) {
                HttpSession session = attr.getRequest().getSession();
                session.removeAttribute("message");
            }
        } catch (Exception e) {
            System.out.println("Error in SessionHelper: " + e);
            e.printStackTrace();
        }

    }

}