package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Customer {
    //Customer entity
    //entity class
    @Id
    private String id;
    private String name;
    private String address;
    private double salary;
}
