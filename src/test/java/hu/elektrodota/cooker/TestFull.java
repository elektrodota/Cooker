/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
/**
 *
 * @author elefank
 */
@RunWith(MockitoJUnitRunner.class)
public class TestFull {
    @Spy
    FullRecept fr=new FullRecept();
   
    @Test
    public void testFullReceptSearchNameByName() {
     fr.searchNamebyName("a", "b");
     Mockito.verify(fr).searchNamebyName("a","b");
    }
    @Test
    public void testFullReceptSearchByEverything()
    {
        if(fr.searchNamebyName("", "").size()>0)
        {
            long id=fr.searchNamebyName("", "").get(0).getReceptId();
            fr.searchEverything(id);
            Mockito.verify(fr).searchEverything(id);
        }
    }
    @Test
    public void testFullReceptInsertAll()
    {
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
        fr.loadUpAll();
        Mockito.verify(fr).loadUpAll();
        List<Receptek> searchNamebyName = fr.searchNamebyName(receptek.getReceptNev(),"");
       for(Receptek  rp:searchNamebyName)
       {
           fr.deleteRecipe(rp.getReceptId());
       }
    }
}
