package com.jazva.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private   int id;
    @Column
    private String name;
    @Column
    private int price;
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    Set<ProductLocation> productLocations;


}