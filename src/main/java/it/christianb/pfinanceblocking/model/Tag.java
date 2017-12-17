package it.christianb.pfinanceblocking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAGS")
public class Tag extends AbstractBaseEntity {

    @Getter @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    @Getter @Setter
    private String name;

}
