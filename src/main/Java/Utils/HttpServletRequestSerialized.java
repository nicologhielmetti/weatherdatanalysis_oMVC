package Utils;

import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class HttpServletRequestSerialized implements Serializable {
    private Long requestIdentifier;
    private String requestURL;
    private String method;
    private String authType;
    private Cookie[] cookies;
    private Map<String,String> headers;
    private Map<String,String> attributes;

    public HttpServletRequestSerialized (HttpServletRequest httpServletRequest, Long requestIdentifier) {
        this.requestIdentifier = requestIdentifier;
        this.requestURL = httpServletRequest.getRequestURI();
        this.method = httpServletRequest.getMethod();
        this.authType = httpServletRequest.getAuthType();
        this.cookies = httpServletRequest.getCookies();
        this.headers = new HashMap<>();
        this.attributes = new HashMap<>();
        Enumeration<String> enumerationHead = httpServletRequest.getHeaderNames();
        String enHead;
        while (enumerationHead.hasMoreElements()) {
             enHead = enumerationHead.nextElement();
            this.headers.put(enHead, (String) httpServletRequest.getHeader(enHead));
        }
        Enumeration<String> enumerationAttr = httpServletRequest.getAttributeNames();
        String enAttr;
        while (enumerationAttr.hasMoreElements()) {
            enAttr = enumerationAttr.nextElement();
            this.attributes.put(enAttr, httpServletRequest.getAttribute(enAttr).toString());
        }
    }

    public String serialize() throws IOException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public Long getRequestIdentifier() {
        return requestIdentifier;
    }

    public void setRequestIdentifier(Long requestIdentifier) {
        this.requestIdentifier = requestIdentifier;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public Map<String,String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
