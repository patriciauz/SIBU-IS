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
@SequenceGenerator(name = "Prestamo_Gen", sequenceName = "SEQ_PRESTAMO", allocationSize = 1)
public class Prestamo implements Serializable,ObjEntity {

    private static final long serialVersionUID = 4334979011962235980L;

    public enum Estado {
        EN_PRESTAMO,ENTREGADO, EN_DEUDA
    }

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Prestamo_Gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_RECURSO")
    private Recurso recurso;

    @ManyToOne
    @JoinColumn(name = "ID_PERSONA")
    private Persona persona;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaLimite;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaPrestamo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Prestamo.Estado estado;


    public void setEstado(Prestamo.Estado estado) {
        this.estado = estado;
    }

    public Prestamo.Estado getEstado() {
        return estado;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }


    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }
}
