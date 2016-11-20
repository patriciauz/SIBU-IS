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
@SequenceGenerator(name = "Recurso_Gen", sequenceName = "SEQ_RECURSO", allocationSize = 1)
public class Recurso implements Serializable,ObjEntity {

    private static final long serialVersionUID = 7420061644036972346L;

    public enum Estado {
        DISPONIBLE,
        PRESTADO,
        RESERVADO,
        DETERIORADO,
        INACTIVO
    }

    public enum Tipo {
        LIBRO,
        REVISTA,
        TESIS,
        DICCIONARIO,
        VIDEOS
    }

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Recurso_Gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA")
    private Categoria categoria;

    @Column(nullable = false, length = 7)
    private String isbn;

    @Column(nullable = false, length = 40)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Recurso.Tipo tipo;

    @Column(nullable = false)
    private Long ejemplar;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date anno;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private Recurso.Estado estado;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Long ejemplar) {
        this.ejemplar = ejemplar;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public void setTipo(Recurso.Tipo tipo) {
        this.tipo = tipo;
    }

    public Recurso.Tipo getTipo() {
        return tipo;
    }

    public void setEstado(Recurso.Estado estado) {
        this.estado = estado;
    }

    public Recurso.Estado getEstado() {
        return estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    public void setAnno(Date anno) {
        this.anno = anno;
    }

    public Date getAnno() {
        return anno;
    }
}
