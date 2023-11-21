/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import jakarta.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author mlopes
 */
@javax.persistence.Entity
@Table(name = "User")
public class User implements Serializable{

    @Id
    private Long id;
}
