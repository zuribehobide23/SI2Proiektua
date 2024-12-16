package eredua;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure().addAnnotatedClass(eredua.domeinua.DriverHibernate.class)
					.addAnnotatedClass(eredua.domeinua.RideHibernate.class)
					.addAnnotatedClass(eredua.domeinua.TravelerHibernate.class)
					.addAnnotatedClass(eredua.domeinua.UserHibernate.class).buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Error al crear la SessionFactory." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
