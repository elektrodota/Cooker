/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author elefank
 */
public class FullRecept {

    ArrayList<Hozzavalok> hozzavalok = new ArrayList<Hozzavalok>();
    ArrayList<ReceptHozzavalok> receptHozzavalok = new ArrayList<ReceptHozzavalok>();

  
    
    
    Receptek recept;
    ArrayList<ReceptLepesek> receptLepesek = new ArrayList<ReceptLepesek>();

    EntityManagerFactory factory=Persistence.createEntityManagerFactory("Hozzavalok");

    public EntityManagerFactory getFactory() {
        return factory;
    }
    EntityManager em;
    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }
    public FullRecept() {
        
      
    }
    public List<Receptek>searchJustByName(String name)
    {
        em=factory.createEntityManager();
        Query q=em.createQuery("select r from Receptek r where r.receptNev like :name");
        q.setParameter("name", '%' + name + '%');
        List<Receptek> resultList = q.getResultList();
        em.close();
        return resultList;
        
    }

 
    public List<Receptek> searchNamebyName(String data, String hozzavalok) {
        em=factory.createEntityManager();
        String[] split = hozzavalok.split(",");
        ArrayList<String> that = new ArrayList<>();
        Query q;
        List<Long> resultList = new ArrayList<>();
       q = em.createQuery("select h.receptId from ReceptHozzavalok as h where h.hozzavaloNeve in :hv ");

           
            for (String split1 : split) {
                that.add(split1.substring(0,1).toUpperCase()+split1.substring(1).toLowerCase());
            }
            q.setParameter("hv", that);
            resultList = q.getResultList();

       
       
        List<Receptek> resultLista = searchJustByName(data);
        List<Receptek> resultList1 = new ArrayList<>();
        if(resultList.size()==0)
            return new ArrayList<Receptek>();
       for (int i = 0; i < resultLista.size(); i++) {
                if (resultList.contains(resultLista.get(i).getReceptId())) {
                    resultList1.add(resultLista.get(i));
                }

            }
      

       

        em.close();

        return resultList1;
    }

    public String searchEverything(long id) {
        em=factory.createEntityManager();
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
        sb.append("Preparation time:\n");
        sb.append(r.getElkeszitesiIdo() + "\n");

        sb.append("Ingredients: \n");
        for (int i = 0; i < resultList.size(); i++) {
            sb.append(resultList.get(i).getHozzavaloNeve() + " " + resultList.get(i).getMennyiseg() + "\n");
        }
        sb.append("Steps: \n");
        for (int i = 0; i < resultList1.size(); i++) {
            sb.append(resultList1.get(i).getLepesSzam() + " " + resultList1.get(i).getLepesLeiras() + "\n");
        }
        em.close();
        return sb.toString();
    }

   

    public void deleteRecipe(long id) {
        em=factory.createEntityManager();
        Query q = em.createQuery("Delete from Receptek r where r.receptId=:id ");
        q.setParameter("id", id);
        int rows = q.executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin();
        q = em.createQuery("Delete from ReceptLepesek r where r.receptId=:id");
        q.setParameter("id", id);
        rows = q.executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin();
        q = em.createQuery("Delete from ReceptHozzavalok r where r.receptId=:id");
        q.setParameter("id", id);
        rows = q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public void loadUpAll() {
        em=factory.createEntityManager();

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
