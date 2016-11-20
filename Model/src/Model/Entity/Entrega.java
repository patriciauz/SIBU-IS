package Model.Entity;

import java.io.Serializable;


import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import services.ObjEntity;

@Entity
@SequenceGenerator(name = "Entrega_Gen", sequenceName = "SEQ_ENTREGA", allocationSize = 1)
public class Entrega implements Serializable,ObjEntity {

    private static final long serialVersionUID = 4537791665513519874L;
    
    public enum Estado{ENTREGADO,SIN_ENTREGAR}

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Entrega_Gen")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaEntrega;

    @ManyToOne
    @JoinColumn(name = "ID_PRESTAMO")
    private Prestamo prestamo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Entrega.Estado estado;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setEstado(Entrega.Estado estado) {
        this.estado = estado;
    }

    public Entrega.Estado getEstado() {
        return estado;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Entrega)) {
            return false;
        }
        final Entrega other = (Entrega) object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
