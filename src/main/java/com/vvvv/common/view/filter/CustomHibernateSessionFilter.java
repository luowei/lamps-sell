package com.vvvv.common.view.filter;
/**
 * @className:CustomHibernateSessionFilter.java
 * @classDescription:用于设置会话的权限
 * @author:xiayingjie
 * @createTime:2011-5-25
 */
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.hibernate.*;
import org.springframework.dao.*;

public class CustomHibernateSessionFilter extends OpenSessionInViewFilter {

    /**
     * 覆盖父类的获取Session操作
     */
    protected Session getSession(SessionFactory sessionFactory)
                        throws DataAccessResourceFailureException {
        Session session = super.getSession(sessionFactory);
        //设置成动态
        session.setFlushMode(FlushMode.AUTO);   
        return session;
    }
   
    /**
     * 关闭Session
     */
	protected void closeSession(Session session, SessionFactory sessionFactory){
        session.flush(); 
        super.closeSession(session, sessionFactory); 
    }
}