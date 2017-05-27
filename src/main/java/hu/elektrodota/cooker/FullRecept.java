/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author elefank
 */
public class FullRecept {

    ArrayList<Hozzavalok> hozzavalok = new ArrayList<Hozzavalok>();
    ArrayList<ReceptHozzavalok> receptHozzavalok = new ArrayList<ReceptHozzavalok>();
    Receptek recept;
    ArrayList<ReceptLepesek> receptLepesek = new ArrayList<ReceptLepesek>();
    ;

    EntityManagerFactory factory;
    EntityManager em;

    
    public List<Receptek> searchNamebyName(String data, String hozzavalok) {
        factory = Persistence.createEntityManagerFactory("Hozzavalok");
        em = factory.createEntityManager();
        Query q = em.createQuery("Select r from Receptek r where r.receptNev like :name");
        q.setParameter("name", '%' + data + '%');
        List<Receptek> resultList = q.getResultList();
        
        
       
        em.close();
        factory.close();
        return resultList;
    }

    public String searchEverything(long id) {

        factory = Persistence.createEntityManagerFactory("Hozzavalok");
        em = factory.createEntityManager();

        Query q = em.createQuery("Select r from ReceptHozzavalok r where r.receptId=:id");
        q.setParameter("id", id);
        List<ReceptHozzavalok> resultList = q.getResultList();

        q = em.createQuery("Select r from ReceptLepesek r where r.receptId=:id");
        q.setParameter("id", id);
        List<ReceptLepesek> resultList1 = q.getResultList();
        StringBuilder sb = new StringBuilder();
        q = em.createQuery("select r from Receptek r where r.receptId=:id");
        q.setParameter("id", id);
        Receptek r = (Receptek) q.getSingleResult();
        sb.append("Recipe name: \n");
        sb.append(r.getReceptNev() + "\n");

        sb.append("Ingredients: \n");
        for (int i = 0; i < resultList.size(); i++) {
            sb.append(resultList.get(i).getHozzavaloNeve() + " " + resultList.get(i).getMennyiseg() + "\n");
        }
        sb.append("Steps: \n");
        for (int i = 0; i < resultList1.size(); i++) {
            sb.append(resultList1.get(i).getLepesSzam() + " " + resultList1.get(i).getLepesLeiras() + "\n");
        }

        return sb.toString();
    }

    public void loadUpAll() {
        factory = Persistence.createEntityManagerFactory("Hozzavalok");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(recept);
        em.flush();
        em.getTransaction().commit();

        em.getTransaction().begin();
        for (int i = 0; i < hozzavalok.size(); i++) {
            Hozzavalok get = hozzavalok.get(i);
            em.merge(get);
        }
        em.getTransaction().commit();
        em.getTransaction().begin();
        for (int i = 0; i < receptLepesek.size(); i++) {
            receptLepesek.get(i).setReceptId(recept.getReceptId());
            ReceptLepesek get = receptLepesek.get(i);
            em.persist(get);
        }
        em.getTransaction().commit();
        em.getTransaction().begin();
        for (int i = 0; i < receptHozzavalok.size(); i++) {
            receptHozzavalok.get(i).setReceptId(recept.getReceptId());
            ReceptHozzavalok get = receptHozzavalok.get(i);
            em.persist(get);
        }
        em.getTransaction().commit();
        em.close();
        factory.close();

    }

    public FullRecept() {

    }

    public ArrayList<Hozzavalok> getHozzavalok() {
        return hozzavalok;
    }

    public void setHozzavalok(ArrayList<Hozzavalok> hozzavalok) {
        this.hozzavalok = hozzavalok;
    }

    public ArrayList<ReceptHozzavalok> getReceptHozzavalok() {
        return receptHozzavalok;
    }

    public void setReceptHozzavalok(ArrayList<ReceptHozzavalok> receptHozzavalok) {
        this.receptHozzavalok = receptHozzavalok;
    }

    public Receptek getRecept() {
        return recept;
    }

    public void setRecept(Receptek recept) {
        this.recept = recept;
    }

    public ArrayList<ReceptLepesek> getReceptLepesek() {
        return receptLepesek;
    }

    public void setReceptLepesek(ArrayList<ReceptLepesek> receptLepesek) {
        this.receptLepesek = receptLepesek;
    }

}
