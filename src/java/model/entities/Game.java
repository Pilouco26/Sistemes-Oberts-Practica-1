package model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
@XmlRootElement
@Entity
@Table(name = "Game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "Game_Gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Game_Gen")
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
