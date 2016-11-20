package Model.Entity;

import java.io.Serializable;


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

import services.ObjEntity;

@Entity
@SequenceGenerator(name = "Deuda_Gen", sequenceName = "SEQ_DEUDA", allocationSize = 1)
public class Deuda implements Serializable,ObjEntity {

    private static final long serialVersionUID = 3312857370149204928L;

    public enum Estado {
        ACTIVO,
        PAGADO,
        ANULADO
    }

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Deuda_Gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ENTREGA")
    private Entrega entrega;

    @Column(nullable = false)
    private Long dias;

    @Column(nullable = false, length = 10)
    private String mora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Deuda.Estado estado;

    public Long getDias() {
        return dias;
    }

    public void setDias(Long dias) {
        this.dias = dias;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEstado(Deuda.Estado estado) {
        this.estado = estado;
    }

    public Deuda.Estado getEstado() {
        return estado;
    }


    public String getMora() {
        return mora;
    }

    public void setMora(String mora) {
        this.mora = mora;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Deuda)) {
            return false;
        }
        final Deuda other = (Deuda) object;
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
