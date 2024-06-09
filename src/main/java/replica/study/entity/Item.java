package replica.study.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;

    private String name;

    protected Item(){}

    @Builder
    public Item(long id, String name){
        this.id = id;
        this.name = name;
    }
}
