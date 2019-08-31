package net.zoltancsaszi.actionmonitor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class to represent a Wallet record from the database.
 *
 * @author Zoltan Csaszi
 */
@Entity
@Data
@ToString
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;
    private double amount;
    private String name;

    public Wallet(double amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    public Wallet(long id, double amount, String name) {
        this(amount, name);
        this.id = id;
    }
}
