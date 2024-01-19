package model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@Entity
@Table(name = "GameShop")
public class GameShop implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock")
    private int stock;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "game")
    private Game game;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "shop")
    private Shop shop;

    // Getters
    public long getId() {
        return id;
    }
    
    public Game getGame() {
        return game;
    }

    public int getStock() {
        return stock;
    }
    
    public Shop getShop() {
        return shop;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void setShop(Shop shop) {
        this.shop = shop;
    }

}
