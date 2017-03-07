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

import services.ObjEntity;

@Entity
@SequenceGenerator(name = "Categoria_Gen", sequenceName = "SEQ_CATEGORIA", allocationSize = 1)
public class Categoria implements Serializable,ObjEntity {

    private static final long serialVersionUID = -7019785360634991462L;
///DE NADA SIRVIO
    public enum Estado {
        ACTIVO,
        INACTIVO
    }
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Categoria_Gen")
    private Long id;

    @Column(nullable = false, length = 10)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Categoria.Estado estado;

    public Categoria.Estado getEstado() {
        return estado;
    }

    public void setEstado(Categoria.Estado estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Categoria)) {
            return false;
        }
        final Categoria other = (Categoria) object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        if (!(nombre == null ? other.nombre == null : nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        result = PRIME * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }
}
