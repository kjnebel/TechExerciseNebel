/**
 */
package util;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import datamodel.ShoppingCart;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @since JavaSE-1.8
 */
public class UtilDBNebel {
   static SessionFactory sessionFactory = null;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory != null) {
         return sessionFactory;
      }
      Configuration configuration = new Configuration().configure();
      StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
      sessionFactory = configuration.buildSessionFactory(builder.build());
      return sessionFactory;
   }

   public static List<ShoppingCart> listItems() {
      List<ShoppingCart> resultList = new ArrayList<ShoppingCart>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;  // each process needs transaction and commit the changes in DB.

      try {
         tx = session.beginTransaction();
         List<?> carts = session.createQuery("FROM ShoppingCart").list();
         for (Iterator<?> iterator = carts.iterator(); iterator.hasNext();) {
        	 ShoppingCart cart = (ShoppingCart) iterator.next();
            resultList.add(cart);
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static List<ShoppingCart> listItems(String item) {
      List<ShoppingCart> resultList = new ArrayList<ShoppingCart>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         System.out.println((ShoppingCart)session.get(ShoppingCart.class, 1)); // use "get" to fetch data
        // Query q = session.createQuery("FROM ShoppingCart");
         List<?> carts = session.createQuery("FROM ShoppingCart").list();
         for (Iterator<?> iterator = carts.iterator(); iterator.hasNext();) {
        	 ShoppingCart cart = (ShoppingCart) iterator.next();
            if (cart.getItem().startsWith(item)) {
               resultList.add(cart);
            }
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static void addToCart(String item, int price) {
      Session session = getSessionFactory().openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         session.save(new ShoppingCart(item, price));
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }
}
