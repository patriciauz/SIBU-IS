package Baking;


import Model.Entity.Categoria;
import Model.Entity.Deuda;
import Model.Entity.Entrega;
import Model.Entity.Persona;

import Model.Entity.Prestamo;

import Model.Entity.Recurso;

import Model.Entity.Reserva;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import javax.faces.context.FacesContext;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import services.SibuSessionEJBLocal;


@ManagedBean(name = "sibuBean")
@SessionScoped

public class SibuBean {

    @EJB
    SibuSessionEJBLocal sibuSessionEJBLocal;

    private List<Persona> personaList;
    private List<Persona.TipoDocumento> tipoIdentificacionList;
    private List<Persona.Rol> rolPersonaList;
    private List<Persona.Estado> estadoPersonaList;
    private List<Recurso> recursoList;
    private List<Reserva.Estado> estadoReservaList = new ArrayList<>();
    private List<Reserva> reservaList = new ArrayList<>();
    private List<Categoria.Estado> estadoCategoriaList = new ArrayList<>();
    private List<Categoria> categoriaList = new ArrayList<>();
    private List<Recurso.Tipo> tipoRecursoList = new ArrayList<>();
    private List<Recurso.Estado> estadoRecursoList = new ArrayList<>();
    private List<Entrega> entregaList = new ArrayList<>();
    private List<Entrega.Estado> estadoEntregaList = new ArrayList<>();
    private List<Deuda> deudaList = new ArrayList<>();
    private List<Deuda.Estado> estadoDeudaList;

    private Persona.TipoDocumento paramTipoDocumento;
    private String paramNumeroIdentificacion;
    private String paramNombres;
    private String paramApellidos;
    private String paramContrasenna;
    private Categoria paramCategoria;
    private Recurso.Tipo paramTipoRecurso;
    private Recurso.Estado paramEstadoRecurso;
    private Reserva.Estado paramEstadoReserva;
    private String paramIdentificacionPersona;
    private Recurso paramRecurso;
    private String paramNombresAutor;
    private String paramApellidosAutor;
    private Categoria.Estado paramEstadoCategoria;
    private Prestamo paramPrestamoEntrega;
    private Entrega.Estado paramEstadoEntrega;
    private Persona persona;
    private Prestamo prestamo;
    private Recurso recurso;
    private Reserva reserva;
    private Categoria categoria;
    private Entrega entrega;
    private Deuda deuda;
    
    private boolean renderedGeneral;
    private boolean renderedEspecifico;
    private boolean renderedReserva;


    private void preparateLoginAction() {

        try {
            this.persona = sibuSessionEJBLocal.validateLoginUser(this.paramNumeroIdentificacion, this.paramContrasenna);
            this.menuPage(this.persona);

        } catch (Exception e) {
            FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }

    }


    public String goLoginAction() {

        String page = null;
        try {
            this.preparateLoginAction();

            page = this.menuPage(this.persona);
            return page;

        } catch (Exception e) {
            FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return page;
    }

    private String menuPage(Persona persona) {


        if (this.persona
                .getRol()
                .equals(Persona.Rol.ADMINISTRADOR)) {
            return "SIBU/menu/administrador.xhtml";

        } else if (persona.getRol().equals(Persona.Rol.AUX_BIBLIOTECA)) {
            return "SIBU/menu/auxiliarbiblioteca.xhtml";
        } else

            return "SIBU/menu/general.xhtml";
    }

    private void preparateGoAddPersona() {

        this.persona = new Persona();
        this.tipoIdentificacionList = null;
        this.tipoIdentificacionList = ListGenerator.getTipoIdentificacionList();

        this.rolPersonaList = null;
        this.rolPersonaList = ListGenerator.getRolList();

    }

    public String pageAddPersonaAction() {

        this.preparateGoAddPersona();

        return "SIBU/persona/personaAdd.xhtml";
    }


    private void addPersona() {

        try {
            this.isValidEmailAddress(this.persona.getEmail());
            this.persona = this.sibuSessionEJBLocal.persistPersona(this.persona);

            String msg = "Ha sido registrado correctamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));


        } catch (Exception e) {
            FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }

    }

    public String indexPage() {

        this.addPersona();
        this.persona = null;
        return "SIBU/menu/index.xhtml";
    }


    public String goPersonaAction() {

        this.tipoIdentificacionList = null;
        this.tipoIdentificacionList = ListGenerator.getTipoIdentificacionList();

        return "SIBU/persona/persona.xhtml";
    }

    public void findPersonaAction() {

        try {
            this.persona =
                this.sibuSessionEJBLocal.getPersonaByParametersByIdentificacion(this.paramTipoDocumento,
                                                                                this.paramNumeroIdentificacion);
        } catch (Exception e) {
            FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }

    }

    public String showPersonaAction() {

        return "SIBU/persona/personaShow.xhtml";

    }

    private void preparateUpdPersona() {
        this.estadoPersonaList = null;
        this.estadoPersonaList = ListGenerator.getEstadoPersonaList();

        this.tipoIdentificacionList = null;
        this.tipoIdentificacionList = ListGenerator.getTipoIdentificacionList();
    }

    public String goUpdPersonaAction() {

        this.preparateUpdPersona();

        return "SIBU/persona/personaUpd.xhtml";

    }

    public String updPersonaAction() {

        this.persona = this.sibuSessionEJBLocal.mergePersona(this.persona);
        String msg = "Ha sido modificado correctamente";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));

        return "SIBU/persona/persona.xhtml";

    }

    public boolean isValidEmailAddress(String email) throws Exception {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
            throw new Exception("Email invalido");
        }
        return result;
    }

    public String pageAddPrestamoPersonaAction() {

        this.tipoIdentificacionList = null;
        this.tipoIdentificacionList = ListGenerator.getTipoIdentificacionList();

        return "SIBU/prestamo/prestamoPersona.xhtml";
    }

    public String pageAddPrestamoRecursoAction() {

        this.findPersonaAction();
        this.prestamo.setPersona(this.persona);
        return "SIBU/prestamo/prestamoRecursoAdd.xhtml";
    }

    public String pageAddPrestamoLibroAction() {
        //TODO BUSCAR RECURSO PARA PRESTAMO

        this.prestamo.setRecurso(this.recurso);
        Date fechaLimite = this.sumaDiaFecha(new Date(), 15);
        this.prestamo.setFechaLimite(fechaLimite);
        this.prestamo = sibuSessionEJBLocal.persistPrestamo(this.prestamo);
        return "SIBU/prestamo/prestamoAdd.xhtml";
    }


    private void preparateGoRecursoAction() throws Exception {
        try {
            this.recursoList = null;
            this.tipoRecursoList = ListGenerator.getTipoRecursoList();
            this.estadoRecursoList = ListGenerator.getEstadoRecursoList();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }

    public String goRecursoAction() throws Exception {
        this.preparateGoRecursoAction();
        return "/SIBU/recurso/recurso.xhtml";
    }

    public void findRecursoAction() throws Exception {
        try {
            this.recursoList = this.sibuSessionEJBLocal.getRecursoByCriteria(this.paramCategoria, this.paramTipoRecurso, this.paramEstadoRecurso);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }

    private void preparateAddRecursoAction() {
        this.recurso = new Recurso();
    }

    public String goAddRecursoAction() {
        this.preparateAddRecursoAction();
        return "SIBU/recurso/recursoAdd.xhtml";
    }


    public String addRecursoAction() throws Exception {
        try {
            this.recurso = this.sibuSessionEJBLocal.persistRecurso(this.recurso);
            this.recursoList.add(this.recurso);
            String msg = "El recurso se ha ingresado correctamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return "SIBU/recurso/recurso";
    }

    private void preparateGoUpdRecursoAction() throws Exception {
        try {
            if (this.recurso.getEstado().equals(Recurso.Estado.INACTIVO)) {
                throw new Exception("El recurso en estado: " + this.recurso.getEstado() + " no permite modificaciones");
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }

    public String goUpdRecursoAction() throws Exception {
        this.preparateGoUpdRecursoAction();
        return "SIBU/recurso/recursoUpd";
    }

    public String updRecursoAction() throws Exception {
        try {
            this.recurso = this.sibuSessionEJBLocal.mergeRecurso(this.recurso);
            String msg = "El recurso se ha actualizaco correctamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return "SIBU/recurso/recurso";
    }

    public String backRecursoAction() {
        return "/SIBU/recurso/recurso.xhtml";
    }

    public String goShowRecursoAction() {
        return "SIBU/recurso/recursoShow";
    }

    //TODO VALIDAR RESERVA

    private void preparateGoReservaAction() throws Exception {
        try {
            this.renderedReserva = false;
            this.reservaList = null;
            this.tipoIdentificacionList = ListGenerator.getTipoIdentificacionList();
            this.estadoReservaList = ListGenerator.getEstadoReservaList();

            if ((this.persona.getRol().equals(Persona.Rol.DOCENTE)) || (this.persona.getRol().equals(Persona.Rol.EMPLEADO)) || (this.persona.getRol().equals(Persona.Rol.ESTUDIANTE)) ||
                (this.persona.getRol().equals(Persona.Rol.ESTUDIANTE_EMPLEADO))) {
                this.renderedReserva = false;
                this.reservaList = this.sibuSessionEJBLocal.getReservaByPersona(this.persona);
                if (reservaList.isEmpty()) {
                    String msg = "Usted no tiene reservas registradas";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,msg,msg));
                }
            }
            if ((this.persona.getRol().equals(Persona.Rol.ADMINISTRADOR)) || (this.persona.getRol().equals(Persona.Rol.AUX_BIBLIOTECA))) {
                this.renderedReserva = true;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }

    public String goReservaAction() throws Exception {
        this.preparateGoReservaAction();
        return "SIBU/reserva/reserva";
    }

    public void findReservaAction() throws Exception {
        try {
            if ((this.persona.getRol().equals(Persona.Rol.ADMINISTRADOR)) || (this.persona.getRol().equals(Persona.Rol.AUX_BIBLIOTECA))) {
                this.reservaList = this.sibuSessionEJBLocal.getReservaByCriteria(this.paramTipoDocumento, this.paramNumeroIdentificacion, this.paramEstadoReserva);
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }

    private void preparateGoAddReservaAction() {
        this.reserva = new Reserva();
    }

    public String goAddReservaAction() throws Exception {
        this.preparateGoAddReservaAction();
        return "SIBU/reserva/reservaAdd";
    }

    public String addReservaAction() {
        try {
            this.reserva.setPersona(this.persona);
            this.reserva = this.sibuSessionEJBLocal.persistReserva(this.reserva);
            this.reservaList.add(this.reserva);
            String msg = "La reserva se ha ingresado correctamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return "SIBU/reserva/reserva";
    }

    private void preparateGoUpdReservaAction() throws Exception {
        try {
            if (!this.reserva.getEstado().equals(Reserva.Estado.RESERVADO)) {
                throw new Exception("La reserva en estado: " + reserva.getEstado() + " no permite modificaciones");
            }
            this.estadoReservaList = ListGenerator.getEstadoReservaList();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }

    public String goUpdReservaAction() throws Exception {
        this.preparateGoUpdReservaAction();
        return "SIBU/reserva/reservaUpd";
    }

    public String updReservaAction() throws Exception {
        try {
            this.reserva = this.sibuSessionEJBLocal.mergeReserva(this.reserva);
            String msg = "La reserva se ha actualizado correctamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return "SIBU/reserva/reserva";
    }
   

    //TODO VALIDAR CATEGORIA

    private void preparateGoCategoria() {
        this.categoriaList = null;
        this.estadoCategoriaList = ListGenerator.getEstadoCategoria();
    }

    public String goCategoriaAction() {
        this.preparateGoCategoria();
        return "SIBU/categoria/categoria.xhtml";
    }

    public void findCategoriaAction() throws Exception {
        try {
            this.categoriaList = this.sibuSessionEJBLocal.getCategoriaByEstado(this.paramEstadoCategoria);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }

    private void preparateGoAddCategoria() {
        this.categoria = new Categoria();
    }

    public String goAddCateogoriaAction() {
        this.preparateGoAddCategoria();
        return "SIBU/categoria/categoriaAdd.xhtml";
    }

    public String addCategoriaAction() {
        try {
            this.categoria = this.sibuSessionEJBLocal.persistCategoria(this.categoria);
            this.categoriaList.add(this.categoria);
            String msg = "La cateogoria se ha inresado correctamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return "SIBU/categoria/categoria.xhtml";
    }

    private void preparateGoUpdCategoria() throws Exception {
        try {
            if (!this.categoria.getEstado().equals(Categoria.Estado.ACTIVO)) {
                throw new Exception("La categoria en estado " + this.categoria.getEstado() + " no permite modificaciones");
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }
    
    public String goUpdCategoriaAction()throws Exception{
        this.preparateGoUpdCategoria();
        return "SIBU/categoria/categoriaUpd.xhtml";
    }
    
    public String updCategoriaAction()throws Exception{
        try{
            this.categoria = this.sibuSessionEJBLocal.mergeCategoria(this.categoria);
            String msg = "La categoria se ha actualizado correctamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return "SIBU/categoria/categoria.xhtml";
    }

    //TODO VALIDAR ENTREGA
    
    private void preparateGoEntrega(){
        this.estadoEntregaList = ListGenerator.getEstadoEntregaList();
    }
    
    public String goEntregaAction(){
        this.preparateGoEntrega();
        return "SIBU/entrega/entrega.xhtml";
    }
    
    public void findEntregaAction() throws Exception{
        try{
            this.entregaList = this.sibuSessionEJBLocal.getEntregaByCriteria(this.paramPrestamoEntrega, this.paramEstadoEntrega);
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }
    
    private void preparateGoAddEntrega(){
        this.entrega = new Entrega();
    }
    
    public String goAddEntregaAction(){
        this.preparateGoAddEntrega();
        return "SIBU/entrega/entregaAdd.xhtml";
    }
    
    public String AddEntregaAcion()throws Exception{
        try{
            this.entrega = this.sibuSessionEJBLocal.persistEntrega(this.entrega);
            this.entregaList.add(this.entrega);
            String msg = "La entrega se ha ingresado correctamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return "SIBU/entrega/entrega.xhtml";
    }
    
    private void preparateGoUpdEntrega(){
        try {
            if (!this.entrega.getEstado().equals(Entrega.Estado.ENTREGADO)) {
                throw new Exception("La entrega en estado " + this.entrega.getEstado() + " no permite modificaciones");
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }
    
    public String goUpdEntregaAction(){
        this.preparateGoUpdEntrega();
        return "SIBU/entrega/entregaUpd.xhtml";
    }
    
    public String updEntregaAction()throws Exception{
        try{
            this.entrega = this.sibuSessionEJBLocal.mergeEntrega(this.entrega);
            String msg = "La entrega se ha modificado correctamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return "SIBU/entrega/entrega.xhtml";
    }
    
    //TODO HACER  DEUDA
    
    private void preparateGoDeuda() {

        this.tipoIdentificacionList = null;
        this.tipoIdentificacionList = ListGenerator.getTipoIdentificacionList();
    }
    
    public String goDeudaAction(){
        this.preparateGoDeuda();
        return "SIBU/entrega/deuda.xhtml";
    }
    
    
    public String findDeudaAction(){
        try {
            this.deudaList =
                this.sibuSessionEJBLocal.getDeudaByCriteria(this.paramTipoDocumento,
                                                            this.paramNumeroIdentificacion);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return "SIBU/entrega/deuda.xhtml";
    }
    
    public String goPayDeudaAction(){
        this.estadoDeudaList=ListGenerator.getEstadoDeudaList();
        return "SIBU/deuda/deudaUpd.xhtml";
    }
    
    public String payDeudaAction() {
        
        try{
        this.deuda= this.sibuSessionEJBLocal.persistDeuda(this.deuda);
        
        String msg = "La entrega se ha modificado correctamente";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg)); 
        
        }  catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
                }
        return "SIBU/entrega/index.xhtml";
    }

    public void setSibuSessionEJBLocal(SibuSessionEJBLocal sibuSessionEJBLocal) {
        this.sibuSessionEJBLocal = sibuSessionEJBLocal;
    }

    private Date sumaDiaFecha(Date fecha, int dia) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DATE, dia);

        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.MILLISECOND, 00);
        cal.set(Calendar.SECOND, 00);

        return cal.getTime();
    }



    public SibuSessionEJBLocal getSibuSessionEJBLocal() {
        return sibuSessionEJBLocal;
    }


    public void setParamTipoDocumento(Persona.TipoDocumento paramTipoDocumento) {
        this.paramTipoDocumento = paramTipoDocumento;
    }

    public Persona.TipoDocumento getParamTipoDocumento() {
        return paramTipoDocumento;
    }

    public void setParamContrasenna(String paramContrasenna) {
        this.paramContrasenna = paramContrasenna;
    }

    public String getParamContrasenna() {
        return paramContrasenna;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setTipoIdentificacionList(List<Persona.TipoDocumento> tipoIdentificacionList) {
        this.tipoIdentificacionList = tipoIdentificacionList;
    }

    public void setRolPersonaList(List<Persona.Rol> rolPersonaList) {
        this.rolPersonaList = rolPersonaList;
    }

    public List<Persona.TipoDocumento> getTipoIdentificacionList() {
        return tipoIdentificacionList;
    }

    public List<Persona.Rol> getRolPersonaList() {
        return rolPersonaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setParamNumeroIdentificacion(String paramNumeroIdentificacion) {
        this.paramNumeroIdentificacion = paramNumeroIdentificacion;
    }

    public String getParamNumeroIdentificacion() {
        return paramNumeroIdentificacion;
    }

    public void setParamNombres(String paramNombres) {
        this.paramNombres = paramNombres;
    }

    public String getParamNombres() {
        return paramNombres;
    }

    public void setParamApellidos(String paramApellidos) {
        this.paramApellidos = paramApellidos;
    }

    public String getParamApellidos() {
        return paramApellidos;
    }

    public void setEstadoPersonaList(List<Persona.Estado> estadoPersonaList) {
        this.estadoPersonaList = estadoPersonaList;
    }

    public List<Persona.Estado> getEstadoPersonaList() {
        return estadoPersonaList;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecursoList(List<Recurso> recursoList) {
        this.recursoList = recursoList;
    }

    public List<Recurso> getRecursoList() {
        return recursoList;
    }

    public void setEstadoReservaList(List<Reserva.Estado> estadoReservaList) {
        this.estadoReservaList = estadoReservaList;
    }

    public List<Reserva.Estado> getEstadoReservaList() {
        return estadoReservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    public List<Reserva> getReservaList() {
        return reservaList;
    }


    public void setEstadoCategoriaList(List<Categoria.Estado> estadoCategoriaList) {
        this.estadoCategoriaList = estadoCategoriaList;
    }

    public List<Categoria.Estado> getEstadoCategoriaList() {
        return estadoCategoriaList;
    }

    public void setEntregaList(List<Entrega> entregaList) {
        this.entregaList = entregaList;
    }

    public List<Entrega> getEntregaList() {
        return entregaList;
    }

    public void setEstadoEntregaList(List<Entrega.Estado> estadoEntregaList) {
        this.estadoEntregaList = estadoEntregaList;
    }

    public List<Entrega.Estado> getEstadoEntregaList() {
        return estadoEntregaList;
    }

    public void setDeudaList(List<Deuda> deudaList) {
        this.deudaList = deudaList;
    }

    public List<Deuda> getDeudaList() {
        return deudaList;
    }

    public void setParamCategoria(Categoria paramCategoria) {
        this.paramCategoria = paramCategoria;
    }

    public Categoria getParamCategoria() {
        return paramCategoria;
    }

    public void setParamTipoRecurso(Recurso.Tipo paramTipoRecurso) {
        this.paramTipoRecurso = paramTipoRecurso;
    }

    public Recurso.Tipo getParamTipoRecurso() {
        return paramTipoRecurso;
    }

    public void setParamEstadoRecurso(Recurso.Estado paramEstadoRecurso) {
        this.paramEstadoRecurso = paramEstadoRecurso;
    }

    public Recurso.Estado getParamEstadoRecurso() {
        return paramEstadoRecurso;
    }

    public void setParamEstadoReserva(Reserva.Estado paramEstadoReserva) {
        this.paramEstadoReserva = paramEstadoReserva;
    }

    public Reserva.Estado getParamEstadoReserva() {
        return paramEstadoReserva;
    }

    public void setParamIdentificacionPersona(String paramIdentificacionPersona) {
        this.paramIdentificacionPersona = paramIdentificacionPersona;
    }

    public String getParamIdentificacionPersona() {
        return paramIdentificacionPersona;
    }

    
    public void setParamRecurso(Recurso paramRecurso) {
        this.paramRecurso = paramRecurso;
    }

    public Recurso getParamRecurso() {
        return paramRecurso;
    }

    public void setParamNombresAutor(String paramNombresAutor) {
        this.paramNombresAutor = paramNombresAutor;
    }

    public String getParamNombresAutor() {
        return paramNombresAutor;
    }

    public void setParamApellidosAutor(String paramApellidosAutor) {
        this.paramApellidosAutor = paramApellidosAutor;
    }

    public String getParamApellidosAutor() {
        return paramApellidosAutor;
    }

    public void setParamEstadoCategoria(Categoria.Estado paramEstadoCategoria) {
        this.paramEstadoCategoria = paramEstadoCategoria;
    }

    public Categoria.Estado getParamEstadoCategoria() {
        return paramEstadoCategoria;
    }

    public void setParamPrestamoEntrega(Prestamo paramPrestamoEntrega) {
        this.paramPrestamoEntrega = paramPrestamoEntrega;
    }

    public Prestamo getParamPrestamoEntrega() {
        return paramPrestamoEntrega;
    }

    public void setParamEstadoEntrega(Entrega.Estado paramEstadoEntrega) {
        this.paramEstadoEntrega = paramEstadoEntrega;
    }

    public Entrega.Estado getParamEstadoEntrega() {
        return paramEstadoEntrega;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setRenderedGeneral(boolean renderedGeneral) {
        this.renderedGeneral = renderedGeneral;
    }

    public boolean isRenderedGeneral() {
        return renderedGeneral;
    }

    public void setRenderedEspecifico(boolean renderedEspecifico) {
        this.renderedEspecifico = renderedEspecifico;
    }

    public boolean isRenderedEspecifico() {
        return renderedEspecifico;
    }

    public void setRenderedReserva(boolean renderedReserva) {
        this.renderedReserva = renderedReserva;
    }

    public boolean isRenderedReserva() {
        return renderedReserva;
    }


    public void setCategoriaList(List<Categoria> categoriaList) {
        this.categoriaList = categoriaList;
    }

    public List<Categoria> getCategoriaList() {
        return categoriaList;
    }

    public void setTipoRecursoList(List<Recurso.Tipo> tipoRecursoList) {
        this.tipoRecursoList = tipoRecursoList;
    }

    public List<Recurso.Tipo> getTipoRecursoList() {
        return tipoRecursoList;
    }

    public void setEstadoRecursoList(List<Recurso.Estado> estadoRecursoList) {
        this.estadoRecursoList = estadoRecursoList;
    }

    public List<Recurso.Estado> getEstadoRecursoList() {
        return estadoRecursoList;
    }

    public void setEstadoDeudaList(List<Deuda.Estado> estadoDeudaList) {
        this.estadoDeudaList = estadoDeudaList;
    }

    public List<Deuda.Estado> getEstadoDeudaList() {
        return estadoDeudaList;
    }

    public void setDeuda(Deuda deuda) {
        this.deuda = deuda;
    }

    public Deuda getDeuda() {
        return deuda;
    }
}
