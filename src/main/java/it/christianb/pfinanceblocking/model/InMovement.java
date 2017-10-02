package it.christianb.pfinanceblocking.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("IN")
public class InMovement extends AbstractMovement {

}
