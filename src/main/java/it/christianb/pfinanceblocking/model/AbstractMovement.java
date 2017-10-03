package it.christianb.pfinanceblocking.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Table(name = "MOVEMENT")
@JsonSubTypes({
        @JsonSubTypes.Type(value = InMovement.class, name = "IN"),
        @JsonSubTypes.Type(value = OutMovement.class, name = "OUT"),
        @JsonSubTypes.Type(value = TransferMovement.class, name = "TRANSFER")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
abstract class AbstractMovement extends AbstractBaseEntity {

    @Getter @Setter
    @ManyToOne(optional = false)
    @JoinTable(name = "DEPOSIT_MOVEMENTS", joinColumns = @JoinColumn(name = "fk_movement", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "fk_deposit", nullable = false))
    protected Deposit deposit;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    protected MovementType type;

    @Getter @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "movement_date", nullable = false)
    protected Date date;

    @Getter @Setter
    @Column(name = "amount", nullable = false)
    protected BigDecimal amount;

}
