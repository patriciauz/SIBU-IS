package Baking;


import Model.Entity.Categoria;
import Model.Entity.Deuda;
import Model.Entity.Entrega;
import Model.Entity.Persona;

import Model.Entity.Prestamo;
import Model.Entity.Recurso;
import Model.Entity.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ListGenerator {
    
     public static List<Persona.Rol> getRolList(){
        List<Persona.Rol> list= new ArrayList<>();
        
        list.add(Persona.Rol.ESTUDIANTE);
        list.add(Persona.Rol.EMPLEADO);
        list.add(Persona.Rol.DOCENTE);
        list.add(Persona.Rol.AUX_BIBLIOTECA);
        list.add(Persona.Rol.ESTUDIANTE_EMPLEADO);
        list.add(Persona.Rol.ADMINISTRADOR);
         
         return list;
    }
    
    public static List<Persona.TipoDocumento> getTipoIdentificacionList(){
        
        List<Persona.TipoDocumento> list= new ArrayList<>();
        
        list.add(Persona.TipoDocumento.T_I);
        list.add(Persona.TipoDocumento.CC);
        list.add(Persona.TipoDocumento.EXTRANJERIA);
        
        return list;
    }
    
    public static List<Persona.Estado> getEstadoPersonaList(){
        
        List<Persona.Estado> list= new ArrayList<>();
        
        list.add(Persona.Estado.ACTIVO);
        list.add(Persona.Estado.INACTIVO);
        
        return list;
    }
    
    public static List<Recurso.Tipo> getTipoRecursoList(){
        
        List<Recurso.Tipo> tipoRecursoList= new ArrayList<>();
        
        tipoRecursoList.add(Recurso.Tipo.DICCIONARIO);
        tipoRecursoList.add(Recurso.Tipo.LIBRO);
        tipoRecursoList.add(Recurso.Tipo.REVISTA);
        tipoRecursoList.add(Recurso.Tipo.TESIS);
        tipoRecursoList.add(Recurso.Tipo.VIDEOS);
        
        return tipoRecursoList;
    }
    
    
    public static List<Reserva.Estado> getEstadoReservaList(){
        
        List<Reserva.Estado> estadoReservaList= new ArrayList<>();
        
        estadoReservaList.add(Reserva.Estado.REALIZADO);
        estadoReservaList.add(Reserva.Estado.RESERVADO);
        estadoReservaList.add(Reserva.Estado.CANCELADO);

        return estadoReservaList;
    }
    
    
    
    public static List<Categoria.Estado> getEstadoCategoria(){
        List<Categoria.Estado> estadoCategoriaList = new ArrayList<>();
        
        estadoCategoriaList.add(Categoria.Estado.ACTIVO);
        estadoCategoriaList.add(Categoria.Estado.INACTIVO);
        
        return estadoCategoriaList;
    }
    
    public static List<Prestamo.Estado> getEstadoPrestamo(){
        List<Prestamo.Estado> estadoPrestamoList = new ArrayList<>();
        
        estadoPrestamoList.add(Prestamo.Estado.ENTREGADO);
        estadoPrestamoList.add(Prestamo.Estado.EN_DEUDA);
        estadoPrestamoList.add(Prestamo.Estado.EN_PRESTAMO);
        
        return estadoPrestamoList;
    }
    
    public static List<Entrega.Estado> getEstadoEntregaList(){
       List<Entrega.Estado> estadoEntregaList= new ArrayList<>();
       
       estadoEntregaList.add(Entrega.Estado.ENTREGADO);
       estadoEntregaList.add(Entrega.Estado.SIN_ENTREGAR);
        
        return estadoEntregaList;
    }
    
    public static List<Recurso.Estado> getEstadoRecursoList(){
        
        List<Recurso.Estado> tipoEstadoList= new ArrayList<>();
        
        tipoEstadoList.add(Recurso.Estado.DISPONIBLE);
        tipoEstadoList.add(Recurso.Estado.RESERVADO);
        tipoEstadoList.add(Recurso.Estado.DETERIORADO);
        tipoEstadoList.add(Recurso.Estado.PRESTADO);
        tipoEstadoList.add(Recurso.Estado.INACTIVO);
        
        return tipoEstadoList;
    }  
      
    public static List<Deuda.Estado> getEstadoDeudaList(){
        
        List<Deuda.Estado> list= new ArrayList<>();
        
        list.add(Deuda.Estado.ACTIVO);
        list.add(Deuda.Estado.ANULADO);
        list.add(Deuda.Estado.PAGADO);
        
        return list;
    }
}

