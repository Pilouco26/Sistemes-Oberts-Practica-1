package model.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

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

    @JsonbTransient
    @OneToMany(mappedBy = "game")
    private List<GameShop> shops;


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

     public String getDescription() {
        return description;
    }
    
    public int getPrice() {
        return price;
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
    
    public List<GameShop> getShops() {
        return shops;
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
    
    public void setShops(List<GameShop> shops) {
        this.shops = shops;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
