package it.christianb.pfinanceblocking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
abstract class AbstractBaseEntity implements Serializable {

    @Getter @Setter
    @Column(name = "id")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Getter @Setter
    @Column(name = "version")
    @Version
    protected Long version;

}
