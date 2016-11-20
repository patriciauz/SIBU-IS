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
@SequenceGenerator(name = "Reserva_Gen", sequenceName = "SEQ_RESRVA", allocationSize = 1)
public class Reserva implements Serializable,ObjEntity {

    private static final long serialVersionUID = -6837091775122105170L;

    public enum Estado {
        RESERVADO,
        CANCELADO,
        REALIZADO
    }

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Reserva_Gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_RECURSO")
    private Recurso recurso;
    
    @ManyToOne
    @JoinColumn(name = "ID_PERSONA")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "ID_PRESTAMO")
    private Prestamo prestamo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaLimite;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaReserva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private Reserva.Estado estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setEstado(Reserva.Estado estado) {
        this.estado = estado;
    }

    public Reserva.Estado getEstado() {
        return estado;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

}
