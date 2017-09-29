package it.christianb.pfinanceblocking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "DEPOSIT", uniqueConstraints = @UniqueConstraint(name = "depositNameConstraint", columnNames = {"name"}))
public class Deposit extends AbstractBaseEntity {

    @Getter @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter @Setter
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Deposit() {
    }

    public Deposit(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
    }

}
