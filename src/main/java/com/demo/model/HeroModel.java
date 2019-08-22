package com.demo.model;

import com.demo.entity.Hero;
import com.demo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeroModel {
    private static Logger LOGGER = Logger.getLogger(HeroModel.class.getSimpleName());

    public boolean save(Hero hero) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(hero);
            transaction.commit();
            LOGGER.log(Level.INFO, String.format("Save student success with rollnumber %s", hero.getName()));
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, String.format("Save student error, stack trace"), e);
            return false;
        }
    }

    public List<Hero> findAll() {
        List<Hero> students = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            students = session.createQuery("from Hero", Hero.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Can not findAll student, stack trace"), e);
        }
        return students;
    }

    public Hero findByName(String name) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Hero hero = session.get(Hero.class, name);
            transaction.commit();
            return hero;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, String.format("Can not findById %s student, stack trace", name), e);
            return null;
        }
    }

    public boolean delete(String name) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Hero hero = session.get(Hero.class, name);
            if (hero != null) {
                session.delete(hero);
                LOGGER.log(Level.INFO, String.format("Delete student success with rollnumber %s", name));
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, String.format("Can not delete student with rollnumber %s, stack trace", name), e);
            return false;
        }
    }
}
