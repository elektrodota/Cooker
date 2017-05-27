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

@Entity
public class Hozzavalok implements Serializable {
    @Id
    @Column(name = "HOZZAVALO_NEVE")
    private String hozzavaloNeve;

    public String getHozzavaloNeve() {
        return hozzavaloNeve;
    }

    public void setHozzavaloNeve(String hozzavaloNeve) {
        this.hozzavaloNeve = hozzavaloNeve;
    }
}

