package it.christianb.pfinanceblocking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MOVEMENTS")
public class Movement extends AbstractBaseEntity {

    @Getter @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_deposit", nullable = false)
    private Deposit deposit;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MovementType type;

    @Getter @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "movement_date", nullable = false)
    private Date date;

    @Getter @Setter
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Getter @Setter
    @Column(name = "tax", nullable = true)
    private BigDecimal tax; // FIXME

    /* Used in transfer from a deposit to another one */
    @Getter @Setter
    @OneToOne(optional = true)
    @JoinColumn(name = "fk_to_deposit")
    private Deposit toDeposit;

    @Getter @Setter
    @ManyToMany
    @JoinTable(
            name = "MOVEMENTS_TAGS",
            joinColumns = {@JoinColumn(name = "fk_movement")},
            inverseJoinColumns = {@JoinColumn(name = "fk_tag")}
    )
    @OrderBy("id")
    private List<Tag> tags;

}
