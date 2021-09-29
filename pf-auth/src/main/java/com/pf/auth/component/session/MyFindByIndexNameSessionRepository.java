//package com.pf.auth.component.session;
//
//import com.pf.constant.CommonConstants;
//import com.pf.model.PfSession;
//import com.pf.model.SecurityUser;
//import com.pf.util.HttpHeaderUtil;
//import org.springframework.data.redis.core.BoundHashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.session.FindByIndexNameSessionRepository;
//import org.springframework.util.CollectionUtils;
//
//import java.util.*;
//
///**
// * @ClassName : MyFindByIndexNameSessionRepository
// * @Description :
// * @Author : wangjie
// * @Date: 2021/8/20-18:26
// */
//public class MyFindByIndexNameSessionRepository implements FindByIndexNameSessionRepository<PfSession> {
//    private static final String SESSIONIDS = CommonConstants.CACHE_KEY.SESSIONIDS; // Map<String, SessionInformation>
//    private static final String PRINCIPALS = CommonConstants.CACHE_KEY.PRINCIPALS; // Map<SecurityUser, Set<String>>
//
//    private final RedisTemplate redisTemplate;
//    
//    public MyFindByIndexNameSessionRepository(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//    
//    @Override
//    public Map<String, PfSession> findByIndexNameAndIndexValue(String indexName, String indexValue) {
//        if(PRINCIPAL_NAME_INDEX_NAME.equals(indexName)) {
//            Set<String> sessionIds = this.getPrincipals(indexValue);
//            if(CollectionUtils.isEmpty(sessionIds)) {
//                return Collections.emptyMap();
//            }
//            Map<String, PfSession> sessions = new HashMap(sessionIds.size());
//            Iterator var6 = sessionIds.iterator();
//
//            while(var6.hasNext()) {
//                Object id = var6.next();
//                PfSession session = this.findById((String)id);
//                if (session != null) {
//                    sessions.put(session.getId(), session);
//                }
//            }
//
//            return sessions;
//        } else {
//            return Collections.emptyMap();
//        }
//    }
//
//    @Override
//    public PfSession createSession() {
//        PfSession session = new PfSession();
//        return session;
//    }
//
//    @Override
//    public void save(PfSession pfSession) {
//        if (!pfSession.getId().equals(pfSession.getOriginalId())) {
//            this.deleteById(pfSession.getOriginalId());
//        }
//        PfSession session = new PfSession(pfSession);
//        
//        // 获取session中用户信息
//        SecurityUser principal = null;
//        if(!CollectionUtils.isEmpty(pfSession.getAttributeNames()) && pfSession.getAttributeNames().contains("SPRING_SECURITY_CONTEXT")) {
//            SecurityContext securityContext = pfSession.getAttribute("SPRING_SECURITY_CONTEXT");
//            principal = (SecurityUser) securityContext.getAuthentication().getPrincipal();
//        }
//        
//        this.addSessionInfo(session.getId(), session);
//        if(principal != null) {
//            Set<String> set = this.getPrincipals(principal.getUsername());
//            if(CollectionUtils.isEmpty(set))
//                set = new HashSet<>();
//            if(set.add(session.getId())){
//                this.putPrincipals(principal.getUsername(), set);
//            }
//        }
//    }
//
//    @Override
//    public PfSession findById(String s) {
//        PfSession session = this.getSessionInfo(s);
//        if(session == null) {
//            return null;
//        }
//        if(session.isExpired()) {
//            this.deleteById(s);
//            return null;
//        }
//        return session;
//    }
//
//    @Override
//    public void deleteById(String s) {
//        PfSession session = this.getSessionInfo(s);
//        if(session != null) {
//            SecurityUser user = session.getAttribute(HttpHeaderUtil.USER_IDENTITY);
//            if(user != null) {
//                Set<String> set = this.getPrincipals(user.getUsername());
//                set.remove(s);
//                if(set.isEmpty()) {
//                    this.removePrincipal(user.getUsername());
//                }
//            }
//            this.removeSessionInfo(s);
//        }
//    }
//
//    private void addSessionInfo(final String sessionId, final PfSession pfSession) {
//        BoundHashOperations<String, String, PfSession> hashOperations = redisTemplate.boundHashOps(SESSIONIDS);
//        hashOperations.put(sessionId, pfSession);
//    }
//
//    private PfSession getSessionInfo(final String sessionId) {
//        BoundHashOperations<String, String, PfSession> hashOperations = redisTemplate.boundHashOps(SESSIONIDS);
//        return hashOperations.get(sessionId);
//    }
//
//    private void removeSessionInfo(final String sessionId) {
//        BoundHashOperations<String, String, PfSession> hashOperations = redisTemplate.boundHashOps(SESSIONIDS);
//        hashOperations.delete(sessionId);
//    }
//
//    private void putPrincipals(final String key, final Set<String> set) {
//        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
//        hashOperations.put(key,set);
//    }
//
//    private Set<String> getPrincipals(final String key) {
//        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
//        return hashOperations.get(key);
//    }
//
//    private void removePrincipal(final String key) {
//        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
//        hashOperations.delete(key);
//    }
//
//    private Set<String> getPrincipalsKeySet() {
//        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
//        return hashOperations.keys();
//    }
//}
