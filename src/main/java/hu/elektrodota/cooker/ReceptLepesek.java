/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author elefank
 */
@Entity

public class ReceptLepesek implements Serializable {

    @Id
    @Column(name = "LEPES_SZAM")
    private long lepesSzam;
    @Column(name = "LEPES_LEIRAS")
    private String lepesLeiras;

    @Id
    @Column(name = "RECEPT_ID")
    private long receptId;

    public long getLepesSzam() {
        return lepesSzam;
    }

    public void setLepesSzam(long lepesSzam) {
        this.lepesSzam = lepesSzam;
    }

    @Override
    public String toString() {
        return "ReceptLepesek{" + "lepesSzam=" + lepesSzam + ", lepesLeiras=" + lepesLeiras + ", receptId=" + receptId + '}';
    }

    public String getLepesLeiras() {
        return lepesLeiras;
    }

    public void setLepesLeiras(String lepesLeiras) {
        this.lepesLeiras = lepesLeiras;
    }

    public long getReceptId() {
        return receptId;
    }

    public void setReceptId(long receptId) {
        this.receptId = receptId;
    }

}
