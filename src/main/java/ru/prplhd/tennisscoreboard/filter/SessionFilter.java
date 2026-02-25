package ru.prplhd.tennisscoreboard.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.web.ServletContextKeys;

import java.io.IOException;

@WebFilter("/*")
public class SessionFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        Session session = null;
        try {
            SessionFactory sessionFactory = (SessionFactory) req.getServletContext().getAttribute(ServletContextKeys.SESSION_FACTORY);
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            chain.doFilter(req, res);

            session.getTransaction().commit();

        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }

            throw e;
        }
    }
}
