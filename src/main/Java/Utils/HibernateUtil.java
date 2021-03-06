package Utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.RollbackException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

public class HibernateUtil
{
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory()
    {
        try
        {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
            //return new AnnotationConfiguration().configure(new File("hibernate.cgf.xml")).buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        sessionFactory.close();
    }

    public static HibernateResult executeSelect(String queryString, boolean isResultList, Map<String, Object> params) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        HibernateResult results = new HibernateResult();

        Transaction tr = session.beginTransaction();
        Query query = session.createQuery(queryString);
        if ((params == null && !query.getParameters().isEmpty()) ||
                params != null && query.getParameters().isEmpty())
            throw new IllegalArgumentException();
        if (params != null)
            for (Map.Entry<String, Object> entry : params.entrySet())
                query.setParameter(entry.getKey(), entry.getValue());

        if (isResultList)
            results.setResponse(query.getResultList());
        else
            results.setResponse(query.getSingleResult());
        try {
            tr.commit();
            results.setMsg(baos.toString());
        } catch (RollbackException e) {
            results.setMsg(e.getMessage());
            System.err.println(e.getMessage());
            tr.rollback();
        } finally {
            session.close();
        }
        System.out.flush();
        System.setOut(old);
        return results;
    }

    public static HibernateResult executeSelect(String queryString, boolean isResultList) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        Transaction tr = session.beginTransaction();
        Query query = session.createQuery(queryString);
        HibernateResult results = new HibernateResult();
        if (isResultList)
            results.setResponse(query.list());
        else
            results.setResponse(query.getSingleResult());

        try {
            tr.commit();
            results.setMsg(baos.toString());
        } catch (RollbackException e) {
            results.setMsg(e.getMessage());
            System.err.println(e.getMessage());
            tr.rollback();
        } finally {
            session.close();
        }
        System.out.flush();
        System.setOut(old);
        return results;
    }

    public static HibernateResult executeInsert(Collection<?> data) {
        if (!data.iterator().next().getClass().getPackage().getName().equals("State.Model"))
            throw new IllegalArgumentException();
        Session session = HibernateUtil.getSessionFactory().openSession();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        HibernateResult result = new HibernateResult(null);

        Transaction tr = session.beginTransaction();
        int i = 0;
        int batchSize = Integer.parseInt(new Configuration().configure().getProperty("hibernate.jdbc.batch_size"));
        for (Object datum : data) {
            i++;
            session.save(datum);
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        try {
            tr.commit();
            result.setMsg(baos.toString());
        } catch (RollbackException e) {
            result.setMsg(e.getMessage());
            System.err.println(e.getMessage());
            tr.rollback();
        } finally {
            session.close();
        }
        System.out.flush();
        System.setOut(old);
        return result;
    }

    public static HibernateResult executeInsert(Object data) {
        if (!data.getClass().getPackage().getName().equals("State.Model"))
            throw new IllegalArgumentException();
        Session session = HibernateUtil.getSessionFactory().openSession();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        HibernateResult result = new HibernateResult(null);

        Transaction tr = session.beginTransaction();

        session.save(data);

        try {
            tr.commit();
            result.setMsg(baos.toString());
        } catch (RollbackException e) {
            result.setMsg(e.getMessage());
            tr.rollback();
        } finally {
            session.close();
        }
        System.out.flush();
        System.setOut(old);
        return result;
    }

}