/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author elefank
 */
@Entity
public class ReceptHozzavalok implements Serializable {

    @Id
    @Column(name = "RECEPT_ID")
    private long receptId;
    @Id
    @Column(name = "HOZZAVALO_NEVE")
    private String hozzavaloNeve;
    @Column(name = "MENNYISEG")
    private String mennyiseg;

    public long getReceptId() {
        return receptId;
    }

    public void setReceptId(long receptId) {
        this.receptId = receptId;
    }

    public String getHozzavaloNeve() {
        return hozzavaloNeve;
    }

    public void setHozzavaloNeve(String hozzavaloNeve) {
        this.hozzavaloNeve = hozzavaloNeve;
    }

    public String getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(String mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

}
