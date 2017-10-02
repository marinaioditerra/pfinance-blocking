package it.christianb.pfinanceblocking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("TRANSFER")
public class TransferMovement extends AbstractMovement {

    @Getter @Setter
    @Column(name = "tax", nullable = true)
    private BigDecimal tax; // FIXME

    @Getter @Setter
    @OneToOne(optional = true)
    @JoinColumn(name = "fk_to_deposit")
    private Deposit toDeposit;

}
