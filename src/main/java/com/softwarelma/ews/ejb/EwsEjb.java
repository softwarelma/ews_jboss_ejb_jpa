package com.softwarelma.ews.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@EJB(beanInterface = EwsEjb.class, name = "EjbEws")
@LocalBean
public class EwsEjb implements EwsEjbInterface {

    // @Inject
    @Produces
    @PersistenceContext(unitName = "nettunoPU")
    private static EntityManager entityManager;

    private Logger logger = Logger.getLogger(EwsEjb.class.getName());

    @Override
    public String getText() {
        this.exec();
        return "text";
    }

    @Override
    public void exec() {
        String testoQuery = "select * from dent where rownum < 10";
        List<List<String>> listListCol = new ArrayList<List<String>>();
        Query query = entityManager.createNativeQuery(testoQuery);
        @SuppressWarnings("unchecked")
        List<Object[]> listRecord = (List<Object[]>) query.getResultList();
        List<String> listCol;

        for (Object[] record : listRecord) {
            listCol = new ArrayList<String>();
            for (Object col : record)
                listCol.add(col == null ? null : col + "");
            listListCol.add(listCol);
        }

        this.logger.info("exec - fine");
    }

}
