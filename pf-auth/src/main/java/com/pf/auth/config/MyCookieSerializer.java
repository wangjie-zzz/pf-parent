package com.pf.auth.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.BitSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName : MyCookieSerializer
 * @Description :
 * @Author : wangjie
 * @Date: 2021/9/30-11:38
 */
public class MyCookieSerializer implements CookieSerializer {
    private static final Log logger = LogFactory.getLog(DefaultCookieSerializer.class);
    private static final BitSet domainValid = new BitSet(128);
    private Clock clock = Clock.systemUTC();
    private String cookieName = "J_SESSION_ID";
    private Boolean useSecureCookie;
    private boolean useHttpOnlyCookie = true;
    private String cookiePath;
    private Integer cookieMaxAge;
    private String domainName;
    private Pattern domainNamePattern;
    private String jvmRoute;
    private boolean useBase64Encoding = true;
    private String rememberMeRequestAttribute;
    private String sameSite = "Lax";

    public MyCookieSerializer() {
    }

    public List<String> readCookieValues(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        List<String> matchingCookieValues = new ArrayList();
        if (cookies != null) {
            Cookie[] var4 = cookies;
            int var5 = cookies.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Cookie cookie = var4[var6];
                if (this.cookieName.equals(cookie.getName())) {
                    String sessionId = this.useBase64Encoding ? this.base64Decode(cookie.getValue()) : cookie.getValue();
                    if (sessionId != null) {
                        if (this.jvmRoute != null && sessionId.endsWith(this.jvmRoute)) {
                            sessionId = sessionId.substring(0, sessionId.length() - this.jvmRoute.length());
                        }

                        matchingCookieValues.add(sessionId);
                    }
                }
            }
        }

        return matchingCookieValues;
    }

    public void writeCookieValue(CookieValue cookieValue) {
        HttpServletRequest request = cookieValue.getRequest();
        HttpServletResponse response = cookieValue.getResponse();
        StringBuilder sb = new StringBuilder();
        sb.append(this.cookieName).append('=');
        String value = this.getValue(cookieValue);
        if (value != null && value.length() > 0) {
            this.validateValue(value);
            sb.append(value);
        }

        int maxAge = this.getMaxAge(cookieValue);
        if (maxAge > -1) {
            sb.append("; Max-Age=").append(cookieValue.getCookieMaxAge());
            ZonedDateTime expires = maxAge != 0 ? ZonedDateTime.now(this.clock).plusSeconds((long)maxAge) : Instant.EPOCH.atZone(ZoneOffset.UTC);
            sb.append("; Expires=").append(expires.format(DateTimeFormatter.RFC_1123_DATE_TIME));
        }

        String domain = this.getDomainName(request);
        if (domain != null && domain.length() > 0) {
            this.validateDomain(domain);
            sb.append("; Domain=").append(domain);
        }

        String path = this.getCookiePath(request);
        if (path != null && path.length() > 0) {
            this.validatePath(path);
            sb.append("; Path=").append(path);
        }

        if (this.isSecureCookie(request)) {
            sb.append("; Secure");
        }

        if (this.useHttpOnlyCookie) {
            sb.append("; HttpOnly");
        }

        if (this.sameSite != null) {
            sb.append("; SameSite=").append(this.sameSite);
        }

        response.addHeader("Set-Cookie", sb.toString());
    }

    private String base64Decode(String base64Value) {
        try {
            byte[] decodedCookieBytes = Base64.getDecoder().decode(base64Value);
            return new String(decodedCookieBytes);
        } catch (Exception var3) {
            logger.debug("Unable to Base64 decode value: " + base64Value);
            return null;
        }
    }

    private String base64Encode(String value) {
        byte[] encodedCookieBytes = Base64.getEncoder().encode(value.getBytes());
        return new String(encodedCookieBytes);
    }

    private String getValue(CookieValue cookieValue) {
        String requestedCookieValue = cookieValue.getCookieValue();
        String actualCookieValue = requestedCookieValue;
        if (this.jvmRoute != null) {
            actualCookieValue = requestedCookieValue + this.jvmRoute;
        }

        if (this.useBase64Encoding) {
            actualCookieValue = this.base64Encode(actualCookieValue);
        }

        return actualCookieValue;
    }

    private void validateValue(String value) {
        int start = 0;
        int end = value.length();
        if (end > 1 && value.charAt(0) == '"' && value.charAt(end - 1) == '"') {
            start = 1;
            --end;
        }

        char[] chars = value.toCharArray();

        for(int i = start; i < end; ++i) {
            char c = chars[i];
            if (c < '!' || c == '"' || c == ',' || c == ';' || c == '\\' || c == 127) {
                throw new IllegalArgumentException("Invalid character in cookie value: " + c);
            }
        }

    }

    private int getMaxAge(CookieValue cookieValue) {
        int maxAge = cookieValue.getCookieMaxAge();
        if (maxAge < 0) {
            if (this.rememberMeRequestAttribute != null && cookieValue.getRequest().getAttribute(this.rememberMeRequestAttribute) != null) {
                cookieValue.setCookieMaxAge(2147483647);
            } else if (this.cookieMaxAge != null) {
                cookieValue.setCookieMaxAge(this.cookieMaxAge);
            }
        }

        return cookieValue.getCookieMaxAge();
    }

    private void validateDomain(String domain) {
        int i = 0;
        int cur = -1;

        for(char[] chars = domain.toCharArray(); i < chars.length; ++i) {
            int prev = cur;
            cur = chars[i];
            if (!domainValid.get(cur) || (prev == 46 || prev == -1) && (cur == 46 || cur == 45) || prev == 45 && cur == 46) {
                throw new IllegalArgumentException("Invalid cookie domain: " + domain);
            }
        }

        if (cur == 46 || cur == 45) {
            throw new IllegalArgumentException("Invalid cookie domain: " + domain);
        }
    }

    private void validatePath(String path) {
        char[] var2 = path.toCharArray();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            char ch = var2[var4];
            if (ch < ' ' || ch > '~' || ch == ';') {
                throw new IllegalArgumentException("Invalid cookie path: " + path);
            }
        }

    }

    void setClock(Clock clock) {
        this.clock = clock.withZone(ZoneOffset.UTC);
    }

    public void setUseSecureCookie(boolean useSecureCookie) {
        this.useSecureCookie = useSecureCookie;
    }

    public void setUseHttpOnlyCookie(boolean useHttpOnlyCookie) {
        this.useHttpOnlyCookie = useHttpOnlyCookie;
    }

    private boolean isSecureCookie(HttpServletRequest request) {
        return this.useSecureCookie == null ? request.isSecure() : this.useSecureCookie;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    public void setCookieName(String cookieName) {
        if (cookieName == null) {
            throw new IllegalArgumentException("cookieName cannot be null");
        } else {
            this.cookieName = cookieName;
        }
    }

    public void setCookieMaxAge(int cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    public void setDomainName(String domainName) {
        if (this.domainNamePattern != null) {
            throw new IllegalStateException("Cannot set both domainName and domainNamePattern");
        } else {
            this.domainName = domainName;
        }
    }

    public void setDomainNamePattern(String domainNamePattern) {
        if (this.domainName != null) {
            throw new IllegalStateException("Cannot set both domainName and domainNamePattern");
        } else {
            this.domainNamePattern = Pattern.compile(domainNamePattern, 2);
        }
    }

    public void setJvmRoute(String jvmRoute) {
        this.jvmRoute = "." + jvmRoute;
    }

    public void setUseBase64Encoding(boolean useBase64Encoding) {
        this.useBase64Encoding = useBase64Encoding;
    }

    public void setRememberMeRequestAttribute(String rememberMeRequestAttribute) {
        if (rememberMeRequestAttribute == null) {
            throw new IllegalArgumentException("rememberMeRequestAttribute cannot be null");
        } else {
            this.rememberMeRequestAttribute = rememberMeRequestAttribute;
        }
    }

    public void setSameSite(String sameSite) {
        this.sameSite = sameSite;
    }

    private String getDomainName(HttpServletRequest request) {
        if (this.domainName != null) {
            return this.domainName;
        } else {
            if (this.domainNamePattern != null) {
                Matcher matcher = this.domainNamePattern.matcher(request.getServerName());
                if (matcher.matches()) {
                    return matcher.group(1);
                }
            }

            return null;
        }
    }

    private String getCookiePath(HttpServletRequest request) {
        return this.cookiePath == null ? request.getContextPath() + "/" : this.cookiePath;
    }

    static {
        char c;
        for(c = '0'; c <= '9'; ++c) {
            domainValid.set(c);
        }

        for(c = 'a'; c <= 'z'; ++c) {
            domainValid.set(c);
        }

        for(c = 'A'; c <= 'Z'; ++c) {
            domainValid.set(c);
        }

        domainValid.set(46);
        domainValid.set(45);
    }
}
