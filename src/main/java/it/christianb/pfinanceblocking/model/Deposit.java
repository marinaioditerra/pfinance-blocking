package it.christianb.pfinanceblocking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

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

    @Getter @Setter
    @OneToMany(mappedBy = "deposit", fetch = FetchType.LAZY)
    @OrderBy("movement_date")
    private List<AbstractMovement> movements = new ArrayList<>();

    public Deposit() {
    }

    public Deposit(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
    }

}
