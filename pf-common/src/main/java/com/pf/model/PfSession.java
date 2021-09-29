//package com.pf.model;
//
//import com.pf.util.Asserts;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.session.Session;
//import org.springframework.util.CollectionUtils;
//
//import java.io.Serializable;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.*;
//
///**
// * @ClassName : PfSession
// * @Description :
// * @Author : wangjie
// * @Date: 2021/8/21-15:58
// */
//@Slf4j
//@Data
//public class PfSession implements Session, Serializable {
//    public static final int DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS = 1800;
//    private String id;
//    private final String originalId;
//    private Map<String, Object> sessionAttrs;
//    private Instant creationTime;
//    private Instant lastAccessedTime;
//    private Duration maxInactiveInterval;
//    private static final long serialVersionUID = 7160779239673823561L;
//
//    public PfSession() {
//        this(generateId());
//    }
//
//    public PfSession(String id) {
//        this.sessionAttrs = new HashMap();
//        this.creationTime = Instant.now();
//        this.lastAccessedTime = this.creationTime;
//        this.maxInactiveInterval = Duration.ofSeconds(1800L);
//        this.id = id;
//        this.originalId = id;
//    }
//
//    public PfSession(Session session) {
//        this.sessionAttrs = new HashMap();
//        if(!CollectionUtils.isEmpty(session.getAttributeNames())) {
//            for (String attributeName : session.getAttributeNames()) {
//                this.setAttribute(attributeName, session.getAttribute(attributeName));
//            }
//        }
//        this.creationTime = Instant.now();
//        this.lastAccessedTime = this.creationTime;
//        this.maxInactiveInterval = Duration.ofSeconds(1800L);
//        if (session == null) {
//            throw new IllegalArgumentException("session cannot be null");
//        } else {
//            this.id = session.getId();
//            this.originalId = this.id;
//            this.lastAccessedTime = session.getLastAccessedTime();
//            this.creationTime = session.getCreationTime();
//            this.maxInactiveInterval = session.getMaxInactiveInterval();
//        }
//    }
//
//    public void setLastAccessedTime(Instant lastAccessedTime) {
//        this.lastAccessedTime = lastAccessedTime;
//    }
//
//    public Instant getCreationTime() {
//        return this.creationTime;
//    }
//
//    public String getId() {
//        return this.id;
//    }
//
//    public String getOriginalId() {
//        return this.originalId;
//    }
//
//    public String changeSessionId() {
//        String changedId = generateId();
//        this.setId(changedId);
//        return changedId;
//    }
//
//    public Instant getLastAccessedTime() {
//        return this.lastAccessedTime;
//    }
//
//    public void setMaxInactiveInterval(Duration interval) {
//        this.maxInactiveInterval = interval;
//    }
//
//    public Duration getMaxInactiveInterval() {
//        return this.maxInactiveInterval;
//    }
//
//    public boolean isExpired() {
//        return this.isExpired(Instant.now());
//    }
//
//    boolean isExpired(Instant now) {
//        if (this.maxInactiveInterval.isNegative()) {
//            return false;
//        } else {
//            return now.minus(this.maxInactiveInterval).compareTo(this.lastAccessedTime) >= 0;
//        }
//    }
//
//    public <T> T getAttribute(String attributeName) {
//        return (T) this.sessionAttrs.get(attributeName);
//    }
//
//    public Set<String> getAttributeNames() {
//        return new HashSet(this.sessionAttrs.keySet());
//    }
//    public void setAttributeNames(Set<String> attributeNames) {
//        log.error("仅解决redis中session反序列化问题，非真实属性");
//    }
//
//    public void setAttribute(String attributeName, Object attributeValue) {
//        if (attributeValue == null) {
//            this.removeAttribute(attributeName);
//        } else {
//            this.sessionAttrs.put(attributeName, attributeValue);
//        }
//
//    }
//
//    public void removeAttribute(String attributeName) {
//        this.sessionAttrs.remove(attributeName);
//    }
//
//    public void setCreationTime(Instant creationTime) {
//        this.creationTime = creationTime;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public boolean equals(Object obj) {
//        return obj instanceof Session && this.id.equals(((Session)obj).getId());
//    }
//
//    public int hashCode() {
//        return this.id.hashCode();
//    }
//
//    private static String generateId() {
//        return UUID.randomUUID().toString();
//    }
//}
