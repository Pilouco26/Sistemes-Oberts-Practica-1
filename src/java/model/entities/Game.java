package model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@Entity
@Table(name = "Game")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "console")
    private String console;

    @Column(name = "stock")
    private int stock;

    @Column(name = "pathImage")
    private String pathImage;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "address")
    private String address;

    public String getDescription() {
        return description;
    }
    
    public int getPrice() {
        return price;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getConsole() {
        return console;
    }

    public int getStock() {
        return stock;
    }

    public String getPathImage() {
        return pathImage;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
}
