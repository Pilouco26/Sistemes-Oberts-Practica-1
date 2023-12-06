/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package service;
import java.util.List;
import jakarta.json.bind.annotation.JsonbProperty;

/**
 *
 * @author peree
 */
public class IdListWrapper {
    @JsonbProperty("ids")
    private List<Long> ids;

    // Constructor sin argumentos
    public IdListWrapper() {
    }

    // Getters y setters
    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
