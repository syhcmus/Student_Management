/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


/**
 *
 * @author Sy Pham
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

    static {
        try {
            sessionFactory = (SessionFactory) new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
