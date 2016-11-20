package services;

import Model.Entity.Categoria;
import Model.Entity.Deuda;
import Model.Entity.Entrega;
import Model.Entity.Persona;
import Model.Entity.Prestamo;
import Model.Entity.Recurso;
import Model.Entity.Reserva;

import java.util.List;

import javax.ejb.Local;

@Local
public interface SibuSessionEJBLocal {
    
    
    Reserva persistReserva(Reserva reserva) throws Exception;

    Reserva mergeReserva(Reserva reserva) throws Exception;

    Persona persistPersona(Persona persona)throws Exception;

    Persona mergePersona(Persona persona);

    Categoria persistCategoria(Categoria categoria) throws Exception;

    Categoria mergeCategoria(Categoria categoria) throws Exception;

    Prestamo persistPrestamo(Prestamo prestamo);

    Prestamo mergePrestamo(Prestamo prestamo) throws Exception;

    Recurso persistRecurso(Recurso recurso) throws Exception;

    Recurso mergeRecurso(Recurso recurso) throws Exception;

    Deuda persistDeuda(Deuda deuda);

    Deuda mergeDeuda(Deuda deuda);

    Entrega persistEntrega(Entrega entrega) throws Exception;

    Entrega mergeEntrega(Entrega entrega) throws Exception;
    
    Persona validateLoginUser(String paramUsuario,String paramContrasenna) throws Exception;
    
    List<Persona> getPersonaByParameters(String paramNombres, Persona.TipoDocumento paramTipoIdentificacion,
                                                String paramNumeroIdentificacion,
                                                String paramApellidos) throws Exception;
    
    Persona getPersonaByParametersByIdentificacion(Persona.TipoDocumento paramTipoIdentificacion,
                                                String paramNumeroIdentificacion
                                               ) throws Exception;
    
    void findPersona(Persona persona);

    
    List<Recurso> getRecursoByCriteria(Categoria  paramCategoria, Recurso.Tipo paramTipoRecurso, Recurso.Estado paramEstadoRecurso) throws Exception;

    List<Categoria> getCategoriass() throws Exception;
    
    List<Reserva> getReservaByCriteria(Persona.TipoDocumento paramTipoDocumento, String paramIdentificacionPersona, Reserva.Estado paramEstadoReserva) throws Exception;
    
    List<Reserva> getReservaByPersona(Persona persona);
    
    List<Recurso> getRecursoFindAllActive() throws Exception;
    
    List<Categoria> getCategoriaByEstado(Categoria.Estado paramEstadoCategoria) throws Exception;
    
    List<Entrega> getEntregaByCriteria(Prestamo paramPrestamoEntrega, Entrega.Estado paramEstadoEntrega) throws Exception;
    
    List<Deuda> getDeudaByCriteria(Persona.TipoDocumento tipoIdentificacion, String numeroIdentificacion) throws Exception;
    
}
