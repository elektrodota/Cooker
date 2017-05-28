/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.util.List;

/**
 *
 * @author elefank
 */
public class Service {

    FullRecept fr;

    public FullRecept getFr() {
        return fr;
    }

    public void setFr(FullRecept fr) {
        this.fr = fr;
    }

    public Service() {
        fr = new FullRecept();

    }

    public void loadUp() {
        fr.loadUpAll();
    }

    public List<Receptek> search(String name, String hozzavalok) {
        if (hozzavalok == null) {
            return fr.searchJustByName(name);
        } else if (hozzavalok.equals("")) {
            return fr.searchJustByName(name);
        } else {
            return fr.searchNamebyName(name, hozzavalok);
        }
    }

    public String getBackTheRecipe(long id) {
        return fr.searchEverything(id);
    }
    public void deleteFromDatabase(long id)
    {
        fr.deleteRecipe(id);
        
    }

}
