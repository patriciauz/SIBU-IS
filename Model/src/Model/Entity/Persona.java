package Model.Entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import javax.validation.constraints.NotNull;

import services.ObjEntity;


@Entity
@SequenceGenerator(name = "Persona_Gen", sequenceName = "SEQ_PERSONA", allocationSize = 1)
public class Persona implements Serializable{

    private static final long serialVersionUID = -4153585845478321713L;


    public enum TipoDocumento {
        CC,
        T_I,
        EXTRANJERIA
    }

    public enum Estado {
        ACTIVO,
        INACTIVO
    }

    public enum Rol {
        ESTUDIANTE,
        DOCENTE,
        EMPLEADO,
        AUX_BIBLIOTECA,
        ADMINISTRADOR,
        ESTUDIANTE_EMPLEADO
    }

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Persona_Gen")
    private Long id;


    @Column(nullable = false, length = 12)
    private String identificacion;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private Persona.TipoDocumento tipoDocumento;


    @Column(nullable = false, length = 10)
    private String nombre1;

    @Column(nullable = true, length = 10)
    private String nombre2;


    @Column(nullable = false, length = 12)
    private String apellido1;

    @Column(nullable = true, length = 12)
    private String apellido2;


    @Column(nullable = false, length = 50)
    private String email;


    @Column(nullable = false, length = 7)
    private String telefono;


    @Column(nullable = false, length = 10)
    private String celular;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Persona.Rol rol;

    @Column(nullable = false, length = 50)
    private String contrasenna;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Persona.Estado estado;

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Persona.Rol getRol() {
        return rol;
    }

    public void setRol(Persona.Rol rol) {
        this.rol = rol;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNombre2() {
        return nombre2;
    }


    public void setTipoDocumento(Persona.TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Persona.TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getContrasenna() {
        return contrasenna;
    }


    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getIdentificacion() {
        return identificacion + this.getTipoDocumento();
    }

    public void setEstado(Persona.Estado estado) {
        this.estado = estado;
    }

    public Persona.Estado getEstado() {
        return estado;
    }

    public String getIdentificacionCompleta() {

        return this.tipoDocumento + " " + this.identificacion;
    }


    public String getNombreCompleto() {
        return this.nombre1 + " " + this.nombre2 + " " + this.apellido1 + " " + this.apellido2;
    }
    
}
