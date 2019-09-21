package Utils;

import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

public class HttpServletRequestSerialized implements Serializable {
    private Long requestIdentifier;
    private String requestURL;
    private String method;
    private String authType;
    private Cookie[] cookies;
    private Enumeration<String> headerNames;
    private Map<String,String> attributes;

    public HttpServletRequestSerialized (HttpServletRequest httpServletRequest, Long requestIdentifier) {
        this.requestIdentifier = requestIdentifier;
        this.requestURL = httpServletRequest.getRequestURI();
        this.method = httpServletRequest.getMethod();
        this.authType = httpServletRequest.getAuthType();
        this.cookies = httpServletRequest.getCookies();
        this.headerNames = httpServletRequest.getHeaderNames();
        Enumeration<String> enumeration = httpServletRequest.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            this.attributes.put(enumeration.nextElement(), (String) httpServletRequest.getAttribute(enumeration.nextElement()));
        }
    }

    public String serialize() throws IOException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
