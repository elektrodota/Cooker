/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author elefank
 */
//@RunWith(MockitoJUnitRunner.class)
public class TestFull {

    
    FullRecept fr;
    static Service service;

    @BeforeClass
    public static void setup()
    {
        service=new Service();
    }

    @Test
    public void testSearchJustByName() {
        fr = Mockito.mock(FullRecept.class);
        
        service.setFr(fr);
        service.search("", null);
        Mockito.verify(fr).searchJustByName("");

    }

    @Test
    public void testSearchByNameByName() {
        fr = Mockito.mock(FullRecept.class);
        service.setFr(fr);
        service.search("", "Víz");
        Mockito.verify(fr).searchNamebyName("", "Víz");
      
    }
    @Test
    public void testFullReceptSearchByEverything()
    {
        fr = Mockito.mock(FullRecept.class);
        service.setFr(fr);
        List<Receptek> search = service.search("", "%");
        if(search.size()>0)
        {
            long id=search.get(0).getReceptId();
            service.getBackTheRecipe(id);
            Mockito.verify(fr).searchEverything(id);
        }
    }
    @Test
    public void testFullReceptInsertAll()
    {
        fr = Mockito.mock(FullRecept.class);
        
        
        ArrayList<ReceptHozzavalok> rh=new ArrayList<>();
        ReceptHozzavalok r=new ReceptHozzavalok();
        r.setHozzavaloNeve("Vaj");
        r.setMennyiseg("50 g");
        fr.setReceptHozzavalok(rh);
        Receptek receptek = new Receptek();
        receptek.setElkeszitesiIdo(50);
        receptek.setReceptLeiras("asd");
        receptek.setReceptNev("ASD");
        fr.setRecept(receptek);
        ReceptLepesek rl=new ReceptLepesek();
        rl.setLepesSzam(1);
        rl.setLepesLeiras("Lepes leiras");
        ArrayList<ReceptLepesek> rlList=new ArrayList<ReceptLepesek>();
        rlList.add(rl);
        fr.setReceptLepesek(rlList);
        
        service.setFr(fr);
        service.loadUp();
        
        Mockito.verify(fr).loadUpAll();
        List<Receptek> searchNamebyName = service.search(receptek.getReceptNev(), null);
       for(Receptek  rp:searchNamebyName)
       {
           service.deleteFromDatabase(rp.getReceptId());
       }
    }
}
