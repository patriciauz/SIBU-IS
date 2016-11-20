package services;

import Model.Entity.Categoria;
import Model.Entity.Deuda;
import Model.Entity.Entrega;
import Model.Entity.Persona;
import Model.Entity.Prestamo;
import Model.Entity.Recurso;
import Model.Entity.Reserva;

import java.util.Calendar;
import java.util.Date;
import java.util.List;



import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "SibuSessionEJB", mappedName = "SIBU-Model-SibuSessionEJB")
public class SibuSessionEJBBean implements SibuSessionEJBLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "Model")
    private EntityManager em;

    public void findPersona(Persona persona) {

        this.em.find(Persona.class, persona.getId());

    }

    private void validatePersona(Persona persona) throws Exception {

        StringBuilder ejbql = new StringBuilder();

        ejbql.append("select o from Persona o ");
        ejbql.append(" where o.identificacion=:identificacion ");
        ejbql.append(" and o.estado=:estado ");

        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("identificacion", persona.getIdentificacion());
        query.setParameter("estado", Persona.Estado.ACTIVO);


        List<Persona> personaList = query.getResultList();
        if (!personaList.isEmpty()) {
            throw new Exception("Ya se encuentra registrado");
        }

    }


    public Persona persistPersona(Persona persona) throws Exception {

        this.validatePersona(persona);

        persona.setEstado(Persona.Estado.ACTIVO);

        em.persist(persona);

        return persona;
    }

    public Persona mergePersona(Persona persona) {
        return em.merge(persona);
    }

    public Prestamo persistPrestamo(Prestamo prestamo) {

        Recurso recurso;

        prestamo.setFechaPrestamo(new Date());
        recurso = prestamo.getRecurso();
        recurso.setEstado(Recurso.Estado.PRESTADO);
        em.merge(recurso);
        em.persist(prestamo);
        return prestamo;
    }

    public Persona getPersonaByParametersByIdentificacion(Persona.TipoDocumento paramTipoIdentificacion,
                                                          String paramNumeroIdentificacion) throws Exception {

        Persona persona = null;

        StringBuilder ejbql = new StringBuilder();

        ejbql.append("select o from Persona o where o.id=o.id");


        if (paramTipoIdentificacion != null) {
            ejbql.append(" and o.tipoDocumento=:paramTipoIdentificacion");
        }

        if (paramNumeroIdentificacion != null) {
            ejbql.append(" and o.identificacion=:paramNumeroIdentificacion");
        }


        Query query = this.em.createQuery(ejbql.toString());


        if (paramTipoIdentificacion != null) {
            query.setParameter("paramTipoIdentificacion", paramTipoIdentificacion);
        }

        if (paramNumeroIdentificacion != null) {
            query.setParameter("paramNumeroIdentificacion", paramNumeroIdentificacion.trim());
        }


        List<Persona> personaList = query.getResultList();
        if (personaList.isEmpty()) {
            throw new Exception("No se encontraron personas");
        }

        persona = personaList.get(0);


        return persona;
    }

    public List<Persona> getPersonaByParameters(String paramNombres, Persona.TipoDocumento paramTipoIdentificacion,
                                                String paramNumeroIdentificacion,
                                                String paramApellidos) throws Exception {

        StringBuilder ejbql = new StringBuilder();

        ejbql.append("select o from Persona o where o.id=o.id");

        if (paramNombres != null) {
            ejbql.append(" and UPPER(CONCAT(o.nombre1,o.nombre2)) LIKE :paramNombres ");
        }


        if (paramTipoIdentificacion != null) {
            ejbql.append(" and o.tipoDocumento=:paramTipoIdentificacion");
        }

        if (paramNumeroIdentificacion != null) {
            ejbql.append(" and o.identificacion=:paramNumeroIdentificacion");
        }


        if (paramApellidos != null) {
            ejbql.append(" and UPPER(CONCAT(o.apellido1,o.apellido2)) LIKE :paramApellidos ");
        }

        Query query = this.em.createQuery(ejbql.toString());


        if (paramTipoIdentificacion != null) {
            query.setParameter("paramTipoIdentificacion", paramTipoIdentificacion);
        }

        if (paramNumeroIdentificacion != null) {
            query.setParameter("paramNumeroIdentificacion", paramNumeroIdentificacion.trim());
        }

        if (paramNombres != null) {
            query.setParameter("paramNombres", "%" + (paramNombres.toLowerCase()).trim() + "%");
        }

        if (paramApellidos != null) {
            query.setParameter("paramApellidos", "%" + (paramApellidos.toLowerCase()).trim() + "%");
        }


        List<Persona> personaList = query.getResultList();
        if (personaList.isEmpty()) {
            throw new Exception("No se encontraron personas");
        }

        return personaList;
    }

    public Persona validateLoginUser(String paramUsuario, String paramContrasenna) throws Exception {

        StringBuilder ejbql = new StringBuilder();
        ejbql.append("select o from Persona o ");
        ejbql.append("  where o.identificacion =:paramUsuario");
        ejbql.append(" and  o.contrasenna=:paramContrasenna  ");
        ejbql.append(" and  o.estado=:estado  ");

        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("paramUsuario", paramUsuario);
        query.setParameter("paramContrasenna", paramContrasenna);
        query.setParameter("estado", Persona.Estado.ACTIVO);


        List<Persona> personaList = query.getResultList();
        if (personaList.isEmpty()) {
            throw new Exception("Usted no se encuentra registrado o sus datos no son validos");
        }
        Persona persona = personaList.get(0);
        return persona;
    }


    public Date sumarRestarDiasFecha(Date fecha, int dias){
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(fecha);
         calendar.add(Calendar.DAY_OF_YEAR, dias);
         return calendar.getTime();
    
    }
    
    
    /**
         * Método para comparar dos fechas.
         * @param fecha1 la fecha uno a comparar
         * @param fecha2 la fecha dos a comparar
         * @return El valor de 0 si las fechas son iguales,
         *         el valor de -1 si la fecha1 es menor a la fecha2,
         *         el valor de 1 si la fecha2 es menor a la fecha1
         */
        public int compareFechas(Date fecha1, Date fecha2) {

            Calendar calendario1 = Calendar.getInstance();
            calendario1.setTime(fecha1);
            calendario1.set(Calendar.HOUR_OF_DAY, 0);
            calendario1.set(Calendar.MINUTE, 0);
            calendario1.set(Calendar.SECOND, 0);
            calendario1.set(Calendar.MILLISECOND, 0);

            Calendar calendario2 = Calendar.getInstance();
            calendario2.setTime(fecha2);
            calendario2.set(Calendar.HOUR_OF_DAY, 0);
            calendario2.set(Calendar.MINUTE, 0);
            calendario2.set(Calendar.SECOND, 0);
            calendario2.set(Calendar.MILLISECOND, 0);

            return calendario1.compareTo(calendario2);

        }
    
    /**
         * Método para limpiar las horas, los minutos, los segundos y los milisegundos de la fecha.
         * @param fecha la fecha a limpiar
         * @return La fecha luego de limpiar las horas, los minutos, los segundos y los milisegundos
         */
        public static Date clearTime(Date fecha) {

            Calendar calHora = Calendar.getInstance();
            calHora.setTime(fecha);
            calHora.set(Calendar.HOUR_OF_DAY, 00);
            calHora.set(Calendar.MINUTE, 00);
            calHora.set(Calendar.SECOND, 00);
            calHora.set(Calendar.MILLISECOND, 00);

            return calHora.getTime();
        }
    
    /**
        * Método para restar la fecha final menos la fecha inicial sin tener en cuenta las horas.
        * @param fechaInicial la fecha inicial
        * @param fechaFinal la fecha final
        * @return El número de días de diferencia de la resta
        */
       public static Long restaFechas(Date fechaInicial, Date fechaFinal) {

           long diasDiferencia = clearTime(fechaFinal).getTime() - clearTime(fechaInicial).getTime();
           diasDiferencia = diasDiferencia / 86400000;

           return (Long) diasDiferencia;
       }
    

    private void validateReserva(Reserva reserva) throws Exception{
        
        StringBuilder ejbql = new StringBuilder() ;
        ejbql.append("select o from Reserva o ");
        ejbql.append("  where o.recurso.estado =:estadoRecurso");
        ejbql.append("  or o.persona.estado =:estadoPersona");
        
        if(reserva.getId() !=null){
            ejbql.append("  and o.id<>idReserva");
        }

        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("estadoRecurso", Recurso.Estado.DISPONIBLE);
        query.setParameter("estadoPersona", Persona.Estado.ACTIVO);
        query.setParameter("idReserva", reserva.getId());

        Long count = (Long) query.getSingleResult();
        
        if (count>0) {
            if((!reserva.getRecurso().getEstado().equals(Recurso.Estado.DISPONIBLE))&&(!reserva.getPersona().getEstado().equals(Persona.Estado.ACTIVO))){
                throw new Exception("No se puede efectuar la reserva ya que la persona con el n?mero de identificaci?n: "+reserva.getPersona().getIdentificacion()+" se encuentra en estado: "+reserva.getPersona().getEstado()+
                                    " y el: "+reserva.getRecurso().getTipo()+" se encuentra en estado: "+reserva.getRecurso().getEstado());
            }
            if(!reserva.getRecurso().getEstado().equals(Recurso.Estado.DISPONIBLE)){
                throw new Exception("No se puede efectuar la reserva ya que el "+reserva.getRecurso().getTipo()+" se encuentra en estado: "+reserva.getRecurso().getEstado());
            }
            if(!reserva.getPersona().getEstado().equals(Persona.Estado.ACTIVO)){
                throw new Exception("No se puede efectuar la reserva ya que la persona con el n?mero de identificaci?n: "+reserva.getPersona().getIdentificacion()+" se encuentra en estado: "+reserva.getPersona().getEstado());
            }
        }
    }
    
    
    public Reserva persistReserva(Reserva reserva) throws Exception{
        this.validateReserva(reserva);
        reserva.setEstado(Reserva.Estado.RESERVADO);
        reserva.setFechaReserva(new Date());
        reserva.setFechaLimite(this.sumarRestarDiasFecha(reserva.getFechaReserva(), 7));
        em.persist(reserva);
        return reserva;
    }

    public Reserva mergeReserva(Reserva reserva) throws Exception {
        this.validateReserva(reserva);
        return em.merge(reserva);
    }

    
    public void validateCategoria(Categoria categoria) throws Exception{
        
        StringBuilder ejbql = new StringBuilder() ;
        
        ejbql.append(" select o from Categoria");
        ejbql.append(" where o.nombre=:nombreCategoria");
        ejbql.append(" and o.estado=:estadoCategoria");
        if(categoria.getId()!=null){
            ejbql.append(" and o.id<>idCategoria");
        }
        Query query = this.em.createQuery(ejbql.toString());
        
        query.setParameter("nombreCategoria", categoria.getNombre());
        query.setParameter("estadoCategoria", Categoria.Estado.ACTIVO);
        query.setParameter("idCategoria", categoria.getId());
        
        Long count = (Long) query.getSingleResult();
        
        if(count>0){
            throw new Exception("La categoria "+ categoria.getNombre()+" ya se encuentra registrada y en estado "+categoria.getEstado());
        }                                              
    }

    public Categoria persistCategoria(Categoria categoria)throws Exception {
        validateCategoria(categoria);
        categoria.setEstado(Categoria.Estado.ACTIVO);
        em.persist(categoria);
        return categoria;
    }

    public Categoria mergeCategoria(Categoria categoria)throws Exception {
        validateCategoria(categoria);
        return em.merge(categoria);
    }

    public Prestamo mergePrestamo(Prestamo prestamo) throws Exception {
        return em.merge(prestamo);
    }

    private void validateRecurso(Recurso recurso) throws Exception{
        
        StringBuilder ejbql = new StringBuilder() ;
        ejbql.append("select o from Recurso o ");
        ejbql.append(" where o.isbn =:isbnRecurso");
        if(recurso.getId() !=null){
            ejbql.append("  and o.id<>idRecurso");
        }

        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("isbnRecurso", recurso.getIsbn());
        query.setParameter("idRecurso", recurso.getId());

        Long count = (Long) query.getSingleResult();
        
        if (count>0) {
            throw new Exception("El "+recurso.getTitulo()+" con el ISBN "+recurso.getIsbn()+" y en estado "+recurso.getEstado()+" ya se encuentra registrado");
        }
    }
    
    public Recurso persistRecurso(Recurso recurso)throws Exception {
        this.validateRecurso(recurso);
        recurso.setEstado(Recurso.Estado.DISPONIBLE);
        em.persist(recurso);
        return recurso;
    }
    
    public Recurso mergeRecurso(Recurso recurso)throws Exception {
        this.validateRecurso(recurso);
        return em.merge(recurso);
    }
    

    public Deuda persistDeuda(Deuda deuda) {
        em.persist(deuda);
        return deuda;
    }

    public Deuda mergeDeuda(Deuda deuda) {
        return em.merge(deuda);
    }

    private void validateEntrega(Entrega entrega) throws Exception{
        
        StringBuilder ejbql = new StringBuilder() ;
        ejbql.append("select o from Entrega o ");
        ejbql.append(" where o.prestamo.id =:idPrestamo");
        ejbql.append(" and o.estado =:estado");
        if(entrega.getId() !=null){
            ejbql.append("  and o.id<>idEntrega");
        }

        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("idPrestamo", entrega.getPrestamo().getId());
        query.setParameter("estado", entrega.getEstado());
        query.setParameter("idEntrega", entrega.getId());

        Long count = (Long) query.getSingleResult();
        
        if (count>0) {
            throw new Exception("La entrega del "+entrega.getPrestamo().getRecurso().getCategoria().getNombre()+" ya se encuenta en estado "+entrega.getEstado());
        }
    }
    
    private void executePrestamo(Entrega entrega){
        Prestamo prestamo = new Prestamo();
        prestamo = entrega.getPrestamo();
        prestamo.setEstado(Prestamo.Estado.ENTREGADO);
        em.merge(prestamo);
    }
    
    private Long validateDeudaFecha(Entrega entrega) throws Exception{
     
        Long diasDeuda=0L;
       int restFecha= this.compareFechas(entrega.getFechaEntrega(), entrega.getPrestamo().getFechaLimite());
       if(restFecha==1){
          diasDeuda = restaFechas(entrega.getFechaEntrega(), entrega.getPrestamo().getFechaLimite());
          return diasDeuda;
       }
       return diasDeuda;
    }
    
    private void createDeuda(Entrega entrega) throws Exception {
        
        Deuda deuda = new Deuda();
       deuda.setDias(this.validateDeudaFecha(entrega));
       if(deuda.getDias()<0){
           throw new Exception("Se ha generado una deuda ya que excedio la fecha limite de prestamo");
       }
        deuda.setEntrega(entrega);
        deuda.setEstado(Deuda.Estado.ACTIVO);
    }
    
    public Entrega persistEntrega(Entrega entrega) throws Exception {
        validateEntrega(entrega);
        executePrestamo(entrega);
        entrega.setEstado(Entrega.Estado.ENTREGADO);
        entrega.setFechaEntrega(new Date());
        createDeuda(entrega);
        em.persist(entrega);
        return entrega;
    }

    public Entrega mergeEntrega(Entrega entrega) throws Exception {
        validateEntrega(entrega);
        return em.merge(entrega);
    }


    public List<Recurso> getRecursoByCriteria(Categoria paramCategoria, Recurso.Tipo paramTipoRecurso, Recurso.Estado paramEstadoRecurso) throws Exception {

        StringBuilder ejbql = new StringBuilder();
        ejbql.append("select o from Recurso o where o.id=o.id");

        if (paramCategoria != null) {
            ejbql.append("  and o.categoria.id =:idCategoria");
        }
        if (paramTipoRecurso != null) {
            ejbql.append("  and o.tipo =:paramTipoRecurso");
        }
        if (paramEstadoRecurso != null) {
            ejbql.append("  and o.estado =:paramEstadoRecurso");
        }

        ejbql.append("  order by o.titulo");

        Query query = this.em.createQuery(ejbql.toString());

        if (paramCategoria != null) {
            query.setParameter("idCategoria", paramCategoria.getId());
        }
        if (paramTipoRecurso != null) {
            query.setParameter("paramTipoRecurso", paramTipoRecurso);
        }
        if (paramEstadoRecurso != null) {
            query.setParameter("paramEstadoRecurso", paramEstadoRecurso);
        }

        List<Recurso> recursoList = query.getResultList();

        if (recursoList.isEmpty()) {
            throw new Exception("No se han encontrado recursos segun los parametros asignados");
        }

        return recursoList;
    }

    public List<Categoria> getCategoriass() throws Exception {

        StringBuilder ejbql = new StringBuilder();

        ejbql.append("select o from Categoria o ");
        ejbql.append(" where o.estado=:estadoCategoria ");
        ejbql.append("  order by o.nombre");

        Query query = this.em.createQuery(ejbql.toString());
        
        query.setParameter("estadoCategoria", Categoria.Estado.ACTIVO);

        List<Categoria> categoriaList = query.getResultList();

        if (categoriaList.isEmpty()) {
            throw new Exception("No se han encontraron categorias registradas");
        }
        
        return categoriaList;
    }
    
    public List<Reserva> getReservaByCriteria(Persona.TipoDocumento paramTipoIdentificacionPersona, String paramIdentificacionPersona, Reserva.Estado paramEstadoReserva) throws Exception {

        StringBuilder ejbql = new StringBuilder();
        
        ejbql.append("select o from Reserva o where o.id=o.id");
        if (paramTipoIdentificacionPersona != null) {
            ejbql.append("  and o.prestamo.persona.tipoIdentificacion =:paramTipoIdentificacionPersona");
        }
        if(paramIdentificacionPersona != null){
            ejbql.append(" and o.prestamo.persona.cedula =:paramIdentificacionPersona");
        }
        if(paramEstadoReserva != null){
            ejbql.append(" and o.estado =:paramEstadoReserva");
        }
        ejbql.append("  order by o.fecha DESC");

        Query query = this.em.createQuery(ejbql.toString());

        if (paramTipoIdentificacionPersona != null) {
            query.setParameter("paramTipoIdentificacionPersona", paramTipoIdentificacionPersona);
        }
        if(paramIdentificacionPersona != null){
            query.setParameter("paramIdentificacionPersona", paramIdentificacionPersona);
        }
        if(paramEstadoReserva != null){
            query.setParameter("paramEstadoReserva", paramEstadoReserva);
        }

        List<Reserva> reservaList = query.getResultList();

        if (reservaList.isEmpty()) {
            throw new Exception("No se han encontrado reservas seg?n los parametros estipulados");
        }
        return reservaList;
    }
    
    public List<Reserva> getReservaByPersona(Persona persona) {

        StringBuilder ejbql = new StringBuilder();
        
        ejbql.append("select o from Reserva o where o.id=o.id");
        ejbql.append("  and o.persona.id =:idPersona");
        ejbql.append("  order by o.fechaReserva DESC");

        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("idPersona", persona.getId());
        
        List<Reserva> reservaList = query.getResultList();

        return reservaList;
    }
    
    public List<Recurso> getRecursoFindAllActive() throws Exception {

        StringBuilder ejbql = new StringBuilder();
        
        ejbql.append("select o from Recurso o where o.id=o.id");
        ejbql.append("  and o.estado =:estadoRecurso");
        ejbql.append("  order by o.titulo");

        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("estadoRecurso", Recurso.Estado.DISPONIBLE);

        List<Recurso> recursoList = query.getResultList();

        if (recursoList.isEmpty()) {
            throw new Exception("No se han encontrado recursos DISPONIBLES");
        }

        return recursoList;
    }
    
    
    public List<Categoria> getCategoriaByEstado(Categoria.Estado paramEstadoCategoria) throws Exception{
        StringBuilder ejbql = new StringBuilder();
        
        ejbql.append("select o from Categoria o where o.id=o.id");
        if (paramEstadoCategoria != null) {
            ejbql.append("  and o.estado =:paramEstadoCategoria");
        }
        
        Query query = this.em.createQuery(ejbql.toString());

        if (paramEstadoCategoria != null) {
            query.setParameter("paramEstadoCategoria", Categoria.Estado.ACTIVO);
        }

        List<Categoria> categoriaList = query.getResultList();

        if (categoriaList.isEmpty()) {
            throw new Exception("No se han encontrado cateogiras seg�n los criterios de b�squeda");
        }
        return categoriaList;
    }
    
    public List<Prestamo> getPrestamoByCriteria(Recurso paramRecurso, Persona paramPersonaPrestamo, Long paramCodigoPrestamo, Prestamo.Estado paramEstadoPrestamo) throws Exception {

        StringBuilder ejbql = new StringBuilder();
        
        ejbql.append("select o from Prestamo o where o.id=o.id");
        if (paramRecurso != null) {
            ejbql.append("  and o.recurso.id =:idRecurso");
        }
        if(paramPersonaPrestamo != null){
            ejbql.append(" and o.persona.id =:idPersona");
        }
        if(paramCodigoPrestamo != null){
            ejbql.append(" and o.codigo =:paramCodigoPrestamo");
        }
        if(paramEstadoPrestamo != null){
            ejbql.append(" and o.estado =:paramEstadoPrestamo");
        }
        ejbql.append("  order by o.fechaPrestamo DESC");

        Query query = this.em.createQuery(ejbql.toString());

        if (paramRecurso != null) {
            query.setParameter("idRecurso", paramRecurso.getId());
        }
        if(paramPersonaPrestamo != null){
            query.setParameter("idPersona", paramPersonaPrestamo.getId());
        }
        if(paramCodigoPrestamo != null){
            query.setParameter("paramCodigoPrestamo", paramCodigoPrestamo);
        }
        if (paramEstadoPrestamo != null) {
            query.setParameter("paramEstadoPrestamo", paramEstadoPrestamo);
        }

        List<Prestamo> prestamoList = query.getResultList();

        if (prestamoList.isEmpty()) {
            throw new Exception("No se han encontrado prestamos seg?n los parametros estipulados");
        }
        return prestamoList;
    }
    
    public List<Prestamo> getPrestamoByPersona(Persona persona) {

        StringBuilder ejbql = new StringBuilder();
        
        ejbql.append("select o from Prestamo o where o.id=o.id");
        ejbql.append("  and o.persona.id =:idPersona");
        ejbql.append("  order by o.fechaPrestamo DESC");

        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("idPersona", persona.getId());
        
        List<Prestamo> restamoList = query.getResultList();

        return restamoList;
    }
    
    public List<Entrega> getEntregaByCriteria(Prestamo paramPrestamoEntrega, Entrega.Estado paramEstadoEntrega) throws Exception {

        StringBuilder ejbql = new StringBuilder();
        
        ejbql.append("select o from Entrega o where o.id=o.id");
        if (paramPrestamoEntrega != null) {
            ejbql.append("  and o.prestamo.id =:idPrestamo");
        }
        if (paramEstadoEntrega != null) {
            ejbql.append("  and o.estado =:paramEstadoEntrega");
        }
    
        Query query = this.em.createQuery(ejbql.toString());

        if (paramPrestamoEntrega != null) {
            query.setParameter("idPrestamo", paramPrestamoEntrega.getId());
        }
        if (paramEstadoEntrega != null) {
            query.setParameter("paramEstadoEntrega", paramEstadoEntrega);
        }
        
        List<Entrega> entregaList = query.getResultList();

        if (entregaList.isEmpty()) {
            throw new Exception("No se han encontrado entregas seg�n los criterios de b�squeda");
        }
        return entregaList;
    }
    
    public List<Deuda> getDeudaByCriteria(Persona.TipoDocumento tipoDocumento, String paramNumeroIdentificacion) throws Exception{
       
        StringBuilder ejbql = new StringBuilder();
        
        ejbql.append("select o from Deuda o where o.id=o.id");
        ejbql.append("  and o.entrega.prestamo.persona.tipoDocumento =:tipoDocumento");
        ejbql.append("  and o.entrega.prestamo.persona.identificacion =:paramNumeroIdentificacion");
        
        
        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("tipoDocumento", tipoDocumento);
        query.setParameter("paramNumeroIdentificacion", paramNumeroIdentificacion);
        
        List<Deuda> deudalist = query.getResultList();

        if (deudalist.isEmpty()) {
            throw new Exception("La persona no tiene deudas");
        }
        return deudalist; 
    }


}
