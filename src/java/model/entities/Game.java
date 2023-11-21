package model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Game")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "console")
    private String console;

    @Column(name = "stock")
    private int stock;

    @Column(name = "imatge")
    private String pathImage;

    
    @ElementCollection
    @CollectionTable(name = "stores", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "store")
    private List<String> stores;
    
    

    // getters and setters
}
