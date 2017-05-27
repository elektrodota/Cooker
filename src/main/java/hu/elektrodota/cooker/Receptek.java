/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author elefank
 */
@Entity
public class Receptek implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE1")
    @SequenceGenerator(name = "SEQUENCE1", sequenceName = "SEQUENCE1", allocationSize = 1)
    @Column(name = "RECEPT_ID")
    private long receptId;
    @Column(name = "RECEPT_NEV")
    private String receptNev;
    @Column(name = "RECEPT_LEIRAS")
    private String receptLeiras;
    @Column(name="ELKESZITESI_IDO")
    private int elkeszitesiIdo;

    public int getElkeszitesiIdo() {
        return elkeszitesiIdo;
    }

    public void setElkeszitesiIdo(int elkeszitesiIdo) {
        this.elkeszitesiIdo = elkeszitesiIdo;
    }

    public long getReceptId() {
        return receptId;
    }

    public void setReceptId(long receptId) {
        this.receptId = receptId;
    }

    public String getReceptNev() {
        return receptNev;
    }

    public void setReceptNev(String receptNev) {
        this.receptNev = receptNev;
    }

    public String getReceptLeiras() {
        return receptLeiras;
    }

    public void setReceptLeiras(String receptLeiras) {
        this.receptLeiras = receptLeiras;
    }

}
