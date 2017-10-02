package it.christianb.pfinanceblocking.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OUT")
public class OutMovement extends AbstractMovement {
}
