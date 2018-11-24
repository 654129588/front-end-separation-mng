package com.wisdom.mng.config;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/***
 * @author CHENWEICONG
 * @create 2018-11-22 14:34
 * @desc chenweicong
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    /**
     *
     */
    private static final long serialVersionUID = 6975601077710753878L;
    private final String password,username;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        password = request.getParameter("password");
        username = request.getParameter("username");
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; password: ").append(this.getPassword());
        sb.append(super.toString()).append("; username: ").append(this.getPassword());
        return sb.toString();
    }
}
