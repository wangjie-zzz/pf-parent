//package com.pf.auth.component.session;
//
//import com.pf.model.PfSession;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.session.FindByIndexNameSessionRepository;
//import org.springframework.session.MapSession;
//import org.springframework.session.security.SpringSessionBackedSessionRegistry;
//import org.springframework.util.CollectionUtils;
//
//import java.util.Map;
//
///**
// * @ClassName : MySessionRegisterImpl
// * @Description :
// * @Author : wangjie
// * @Date: 2021/8/10-16:55
// */
//@Slf4j
//public class MySessionRegistryImpl extends SpringSessionBackedSessionRegistry<PfSession> {
//    private FindByIndexNameSessionRepository<PfSession> findByIndexNameSessionRepository;
//    public MySessionRegistryImpl(FindByIndexNameSessionRepository<PfSession> sessionRepository) {
//        super(sessionRepository);
//        this.findByIndexNameSessionRepository = sessionRepository;
//    }
//    public boolean removeSessionAndPrincipalByName(String userName) {
//        Map<String, PfSession> sessionMap = this.findByIndexNameSessionRepository.findByPrincipalName(userName);
//        if(!CollectionUtils.isEmpty(sessionMap)) {
//            for (String key : sessionMap.keySet()) {
//                this.findByIndexNameSessionRepository.deleteById(key);
//            }
//        }
//        return true;
//    }
//}