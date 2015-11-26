/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.prestamos.presentacion;

import co.com.prestamos.business.StClienteServiceBeanLocal;
import co.com.prestamos.entities.Cuota;
import co.com.prestamos.entities.Prestamo;
import co.com.prestamos.entities.ReportePrestamoDTO;
import co.com.prestamos.entities.Usuario;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author egonzalm
 */
@ManagedBean(name = "stClienteController")
@SessionScoped
public class StClienteController implements Serializable {

    private static final long serialVersionUID = -3142112831903388235L;

    @EJB
    private StClienteServiceBeanLocal persistenciaClienteControllerEJB;
    private List<Cuota> listaCoutasTable;
    //Variable de TabView
    private boolean flagPrepareEdit = false;
    //Panel de busqueda

    private Usuario clienteSeleccionado;

    
    private boolean activadorComponentesAsociarLog;
    private boolean activadorBotonActualizarLog;

  
    private BigDecimal lprestamo;

    //variable que se inicializa con valor cuando se selecciona un prestamo del combo
    private BigDecimal idPrestamo;

    //variable en funcion de registro de nuevo prestamo
    private Prestamo nuevoPrestamo;
    private BigDecimal valNumCuotas;
    private Date fechaInicialPrestamo;
    private BigInteger consecPrestamo;
    private BigDecimal lvalorNuevoPrestamo;
    private Prestamo prestamoSeleccionado;
    // variables en funcion de crecion de cuotas por prestamo
    private boolean activadorPanelTablePrestamos;
    boolean activadorPanelRegistroPrestamo;
    private boolean activadorPanelRegistroPrestamoPadre;
    private boolean activadorPanelPrestamosRegistrados;
    
    //variables en funcion de registro nuevo prestamo
    private BigInteger consecutivoPrestamoRegistrado;
    private List<Prestamo> listaPrestamosRegistrados;
    private boolean activadorPanelAsociarCuotasAprestamo;
    
    //variable contexto creacio nuevo usuario;
    private Usuario nuevoUsuario;
    private String usuNombre;
    private String usuCedula;
    private String usuMovil;
    private String usuDireccion;
    private String usuCorreo;
    private String usuTelefono;
    private String usuCiudad;
    private List<Usuario> listaProductosXcliente;

    public StClienteController() {
        this.activadorPanelAsociarCuotasAprestamo = false;
        this.flagPrepareEdit = false;
     
        //variebles de contexto nuevo prestamo
        this.nuevoPrestamo = new Prestamo();
    }

    public void init() {
        clienteSeleccionado = null;
        flagPrepareEdit = false;
        nuevoUsuario = new Usuario();
        System.err.println("init");
    }

    public List<Usuario> findAllClienteDescription(String description) throws Exception {
        List<Usuario> results = new ArrayList<Usuario>();
        try {
            results = (List<Usuario>) (persistenciaClienteControllerEJB.findAllClienteDescription(description));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public void cambioTab(TabChangeEvent e) {
        String tabActual = e.getTab().getId();
        if (tabActual.equals("tab1")) {

        }
        if(tabActual.equals("tab3")){
             this.activadorPanelRegistroPrestamoPadre = true;
             this.activadorPanelPrestamosRegistrados = true;
        }
//        else if (tabActual.equals("tab2")) {
//            this.activadorPanelAsociacionEdicionProveePro = false;
//            this.activadorPanelAsociacionEdicionLog = false;
//        } else if (tabActual.equals("tab3")) {
//            this.activadorPanelCreacionEdicionProducto = false;
//
//        } else if (tabActual.equals("tab4")) {
//            //variables contexto tiempos
//            this.longProveelog = null;
//            this.tiempoTransporte = null;
//            this.tipoDias = null;
//            this.ltipoServ = null;
//            this.activadorItemsEdicionTiempos = false;
//            this.activadorItemsCreacionTiempos = false;
//            this.activadorPanelEdicionCreacionTiempos = false;
//            //variables contexto tiempos generales
//            this.horasPreinspeccion = null;
//            this.horasAduana = null;
//            this.activadorPanelEdicionCreacionTiemposGenerales = false;
//            this.activadorItemsCreacionTiemposGenerales = false;
//            this.longProveeGeneral = null;
//            this.tipoDiasGeneral = null;
//        } else if (tabActual.equals("tab5")) {
//            this.horaInicio = null;
//            this.horaFin = null;
//            this.tipoDiaHora = null;
//            this.lproveeHorario = null;
//            this.activadorPanelEdicionCreacionHorarios = false;
//            this.activadorProveedorHorario = false;
//
//        } else if (tabActual.equals("tab6")) {
//            this.activadorPanelEdicionCreacionDepositos = false;
//            this.longDeposito = null;
//        }

    }

    public void obtenerCuotasPorPrestamo(AjaxBehaviorEvent event) {
        listaCoutasTable = persistenciaClienteControllerEJB.listaCoutasPorIdPrestamo(lprestamo);
    }

    public void guardarPrestamo() {
        try {
            nuevoPrestamo = new Prestamo();
            List<Prestamo> listP = persistenciaClienteControllerEJB.validarConsecutivo(consecPrestamo);
            if (listP.isEmpty()) {
                nuevoPrestamo.setConsecutivo(consecPrestamo);
//                nuevoPrestamo.setCuotasList(calcularCuotas());
                nuevoPrestamo.setMonto(lvalorNuevoPrestamo.toBigInteger());
                nuevoPrestamo.setUsuCedula(clienteSeleccionado.getUsuCedula());
                nuevoPrestamo.setUsuCorreo(clienteSeleccionado.getUsuCorreo());
                nuevoPrestamo.setUsuDireccion(clienteSeleccionado.getUsuDireccion());
                nuevoPrestamo.setUsuMovil(clienteSeleccionado.getUsuMovil());
                persistenciaClienteControllerEJB.registrarPrestamo(nuevoPrestamo,clienteSeleccionado);
//                List<Prestamo> listPrestamosActualizar = persistenciaClienteControllerEJB.validarConsecutivo(consecPrestamo);
//                Prestamo prestamoActualizar = listPrestamosActualizar.get(0);
//                prestamoActualizar.setCuotasList(calcularCuotas());
//                persistenciaClienteControllerEJB.actualizarPrestamo(prestamoActualizar);
//        persistenciaClienteControllerEJB.registroUsuarioPrestamo(clienteSeleccionado.getIntIdusuario());
            }else{
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El consecutivo ya existe.!", ""));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void activarPanelComboPrestamos(){
        this.activadorPanelTablePrestamos = true;
    }
    public void handleSelect(ValueChangeEvent valueChangeEvent) {
        System.err.println("handleSelect");
//        this.flagPrepareEdit = false;
//        this.current = this.clienteSeleccionado;
//        this.visMesOk = "true";
//        StCliente ceTerceroTemp = new StCliente();
//        ceTerceroTemp = (StCliente) valueChangeEvent.getNewValue();
//        if (ceTerceroTemp != null && ceTerceroTemp.getTerNit() != null) {
//            this.flagPrepareEdit = true;
//        }
    }

    public void probando() {
        this.flagPrepareEdit = false;
        if (this.clienteSeleccionado != null) {
            this.flagPrepareEdit = true;

//            this.clienteEncontrado = null;
//            this.clienteEncontrado = new StCliente();
//            this.clienteEncontrado = this.clienteSeleccionado;
//            prepareEdit(this.clienteSeleccionado);
            System.out.println("prueba commit");
        }
    }

    public List<Cuota> listaCoutasPorIdPrestamo() {
        return persistenciaClienteControllerEJB.listaCoutasPorIdPrestamo(lprestamo);
    }
    
    public void asignarCuotasAPrestamo(Prestamo prestamo){
        this.prestamoSeleccionado = prestamo;
         this.activadorPanelRegistroPrestamoPadre = false;
        this.activadorPanelPrestamosRegistrados = false;
        this.activadorPanelAsociarCuotasAprestamo = true;
        
    }

    public SelectItem[] getListaCodigosPrestamos() {

        SelectItem[] items = null;
        if (this.clienteSeleccionado != null) {
            try {
                List<Prestamo> entities = persistenciaClienteControllerEJB.listarTodosLosCodigosPrestamosPorUsuario(new BigDecimal(clienteSeleccionado.getIntIdusuario()));
                int size = true ? entities.size() + 1 : entities.size();
                items = new SelectItem[size];
                int i = 0;
                if (true) {
                    items[0] = new SelectItem("", "-seleccione uno-");
                    i++;
                }
                for (Prestamo prestamo : entities) {
                    items[i++] = new SelectItem(prestamo.getIntIdprestamo(),prestamo.getConsecutivo().toString());
                }
            } catch (Exception ex) {
                Logger.getLogger(StClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return items;

    }

    public SelectItem[] getListaNumeroCoutas() {

        SelectItem[] items = null;
        if (this.clienteSeleccionado != null) {

            int i = 0;
            items = new SelectItem[72];

            if (true) {
                items[0] = new SelectItem("", "-seleccione uno-");
                i++;
            }
            for (i = 1; i < items.length; i++) {
                items[i] = new SelectItem(i);
            }
        }
        return items;

    }

    public boolean isActivadorComponentesAsociarLog() {
        return activadorComponentesAsociarLog;
    }

    public void setActivadorComponentesAsociarLog(boolean activadorComponentesAsociarLog) {
        this.activadorComponentesAsociarLog = activadorComponentesAsociarLog;
    }

    public boolean isActivadorBotonActualizarLog() {
        return activadorBotonActualizarLog;
    }

    public void setActivadorBotonActualizarLog(boolean activadorBotonActualizarLog) {
        this.activadorBotonActualizarLog = activadorBotonActualizarLog;
    }


    public BigDecimal getLprestamo() {
        return lprestamo;
    }

    public void setLprestamo(BigDecimal lprestamo) {
        this.lprestamo = lprestamo;
    }

    public Usuario getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Usuario clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public BigDecimal getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(BigDecimal idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public List<Cuota> getListaCoutasTable() {
        return listaCoutasTable;
    }

    public void setListaCoutasTable(List<Cuota> listaCoutasTable) {
        this.listaCoutasTable = listaCoutasTable;
    }

    public List<Usuario> getListaProductosXcliente() {
        return listaProductosXcliente;
    }

    public void setListaProductosXcliente(List<Usuario> listaProductosXcliente) {
        this.listaProductosXcliente = listaProductosXcliente;
    }

    public Prestamo getNuevoPrestamo() {
        return nuevoPrestamo;
    }

    public void setNuevoPrestamo(Prestamo nuevoPrestamo) {
        this.nuevoPrestamo = nuevoPrestamo;
    }

    public BigDecimal getLvalorNuevoPrestamo() {
        return lvalorNuevoPrestamo;
    }

    public void setLvalorNuevoPrestamo(BigDecimal lvalorNuevoPrestamo) {
        this.lvalorNuevoPrestamo = lvalorNuevoPrestamo;
    }

    public BigDecimal getValNumCuotas() {
        return valNumCuotas;
    }

    public void setValNumCuotas(BigDecimal valNumCuotas) {
        this.valNumCuotas = valNumCuotas;
    }

    public void dividirCuotasAPrestamo(){
        this.activadorPanelAsociarCuotasAprestamo = false;
        this.activadorPanelRegistroPrestamoPadre = true;
        this.activadorPanelPrestamosRegistrados = true;
        List<Cuota> listaCuotas = new ArrayList<Cuota>();
        Cuota cuota;
        String montoPrestamoCadena = prestamoSeleccionado.getMonto().toString();
        BigDecimal montoPrestamoBigdecimal = new BigDecimal(montoPrestamoCadena);
        BigDecimal valorCuotasMes = montoPrestamoBigdecimal.divide(valNumCuotas);
        for (int i = 0; i < this.valNumCuotas.intValue(); i++) {
            cuota = new Cuota();
            cuota.setIntIdprestamo(prestamoSeleccionado);
            cuota.setValor(valorCuotasMes.toBigInteger());
            if (i != 0) {
                fechaInicialPrestamo = aumentarUnDia(fechaInicialPrestamo);
            }
            cuota.setFechaInicial(fechaInicialPrestamo);
            fechaInicialPrestamo = obtenerFechaFutura(fechaInicialPrestamo, 1);
            cuota.setFechaVencimiento(fechaInicialPrestamo);
            listaCuotas.add(cuota);
        }
        persistenciaClienteControllerEJB.crearCuota(listaCuotas);
    }
    
    public void cancelarDividirCuotasAPrestamo(){
        this.activadorPanelAsociarCuotasAprestamo = false;
        this.activadorPanelRegistroPrestamoPadre = true;
        this.activadorPanelPrestamosRegistrados = true;
    }
    
    public void crearNuevoUsuario(){
        if(!persistenciaClienteControllerEJB.create(nuevoUsuario)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El usuario ya existe.!", ""));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario registrado exitosamente.!", ""));
        }
    }

    private Date obtenerFechaFutura(Date fechaInicial, int numMes) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fechaInicial); // Configuramos la fecha que se recibe

        calendar.add(Calendar.MONTH, numMes);  // numero de días a añadir, o restar en caso de días<0

        return calendar.getTime(); // Devuelve el dato date
    }

    public Date getFechaInicialPrestamo() {
        return fechaInicialPrestamo;
    }

    public void setFechaInicialPrestamo(Date fechaInicialPrestamo) {
        this.fechaInicialPrestamo = fechaInicialPrestamo;
    }

    private Date aumentarUnDia(Date fechaInicialPrestamo) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fechaInicialPrestamo); // Configuramos la fecha que se recibe

        calendar.add(Calendar.DAY_OF_MONTH, 1);  // numero de días a añadir, o restar en caso de días<0

        return calendar.getTime(); // Devuelve el dato date
    }


    public BigInteger getConsecutivoPrestamoRegistrado() {
        return consecutivoPrestamoRegistrado;
    }

    public void setConsecutivoPrestamoRegistrado(BigInteger consecutivoPrestamoRegistrado) {
        this.consecutivoPrestamoRegistrado = consecutivoPrestamoRegistrado;
    }

    public boolean isActivadorPanelTablePrestamos() {
        return activadorPanelTablePrestamos;
    }

    public void setActivadorPanelTablePrestamos(boolean activadorPanelTablePrestamos) {
        this.activadorPanelTablePrestamos = activadorPanelTablePrestamos;
    }

    public List<Prestamo> getListaPrestamosRegistrados() {
        listaPrestamosRegistrados = persistenciaClienteControllerEJB.obtenerPrestamosPorUsuario(clienteSeleccionado);
        return listaPrestamosRegistrados;
    }

    public void setListaPrestamosRegistrados(List<Prestamo> listaPrestamosRegistrados) {
        this.listaPrestamosRegistrados = listaPrestamosRegistrados;
    }

    public boolean isActivadorPanelAsociarCuotasAprestamo() {
        return activadorPanelAsociarCuotasAprestamo;
    }

    public void setActivadorPanelAsociarCuotasAprestamo(boolean activadorPanelAsociarCuotasAprestamo) {
        this.activadorPanelAsociarCuotasAprestamo = activadorPanelAsociarCuotasAprestamo;
    }

    public Prestamo getPrestamoSeleccionado() {
        return prestamoSeleccionado;
    }

    public void setPrestamoSeleccionado(Prestamo prestamoSeleccionado) {
        this.prestamoSeleccionado = prestamoSeleccionado;
    }

    public boolean isActivadorPanelRegistroPrestamoPadre() {
        return activadorPanelRegistroPrestamoPadre;
    }

    public void setActivadorPanelRegistroPrestamoPadre(boolean activadorPanelRegistroPrestamoPadre) {
        this.activadorPanelRegistroPrestamoPadre = activadorPanelRegistroPrestamoPadre;
    }

    public boolean isActivadorPanelPrestamosRegistrados() {
        return activadorPanelPrestamosRegistrados;
    }

    public void setActivadorPanelPrestamosRegistrados(boolean activadorPanelPrestamosRegistrados) {
        this.activadorPanelPrestamosRegistrados = activadorPanelPrestamosRegistrados;
    }

    public Usuario getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(Usuario nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }

    @FacesConverter(value = "stClienteConverter")
    public static class StClienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            } else {
                if (value != null && !value.equals("null")) {
                    StClienteController controller = (StClienteController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(), null, "stClienteController");

                    return controller.persistenciaClienteControllerEJB.find(getKey(value));
                } else {
                    return null;
                }

            }
        }

        java.lang.Long getKey(String value) {
            if (value == null || value.length() == 0) {
                return null;
            } else {
                if (value != null) {
                    java.lang.Long key;
                    key = Long.valueOf(value);
                    return key;
                } else {
                    return null;
                }

            }
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                if (o.getIntIdusuario() == null) {
                    return null;
                }
                return getStringKey(o.getIntIdusuario());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuario.class.getName());
            }
        }
    }

    @FacesConverter(value = "rutaConverter")
    public static class RutaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            } else {
                if (value != null && !value.equals("null")) {
                    StClienteController controller = (StClienteController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(), null, "stClienteController");

                    return controller.persistenciaClienteControllerEJB.find(getKey(value));
                } else {
                    return null;
                }

            }
        }

        java.lang.Long getKey(String value) {
            if (value == null || value.length() == 0) {
                return null;
            } else {
                if (value != null) {
                    java.lang.Long key;
                    key = Long.valueOf(value);
                    return key;
                } else {
                    return null;
                }

            }
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                if (o.getIntIdusuario() == null) {
                    return null;
                }
                return getStringKey(o.getIntIdusuario());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuario.class.getName());
            }
        }
    }
    
    public void generarReporteGeneralPDF() throws IOException/*, JRException*/ {

        try {
            List<ReportePrestamoDTO> lista = persistenciaClienteControllerEJB.obtenerDatosReporte(clienteSeleccionado);
            
            for(ReportePrestamoDTO reporte : lista){
                System.out.println("**********nombre usuario: "+reporte.getUsuario().getUsuNombre()+"**********");
                System.out.println("**********cedula usuario: "+reporte.getUsuario().getUsuCedula()+"**********");
                  System.out.println("**********Prestamo Nº: "+reporte.getPrestamo().getConsecutivo()+"**********");
                for(Cuota cuota : reporte.getListCuotas()){
                    System.out.println("*********valor cuota: "+cuota.getValor()+"**********");
                }
            }
            
            
            byte[] bytes = null;
            String ruta;
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            Map<String, Object> parametros = new HashMap<String, Object>();

//            ruta = this.getClass().getResource("../reportesJasper/logo.png").toString();
//            System.out.println("Ruta: "+ ruta);
//            parametros.put("pLogo", ruta);

            InputStream archivoJasper = this.getClass().getResourceAsStream("../reportesJasper/reporteCrossdocking.jasper");

            String nombreArchivo = "reportePrestamos.pdf";

//            JasperPrint jasperPrint = JasperFillManager.fillReport(archivoJasper, parametros, new JRBeanCollectionDataSource(lista));
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            JRXlsxExporter exporter = new JRXlsxExporter();
//            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, nombreArchivo);
//            exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
//            exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);
//            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
//            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
//            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
//            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);

//            exporter.exportReport();
//
//            ExternalContext ext = context.getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) ext.getResponse();
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            response.setHeader("Content-Disposition", "inline; filename=" + nombreArchivo);
//            ServletOutputStream servletOutputStream = response.getOutputStream();
//            servletOutputStream.write(byteArrayOutputStream.toByteArray());
//            servletOutputStream.flush();
//            servletOutputStream.close();
//            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public boolean isFlagPrepareEdit() {
        return flagPrepareEdit;
    }

    public void setFlagPrepareEdit(boolean flagPrepareEdit) {
        this.flagPrepareEdit = flagPrepareEdit;
    }
    
      public BigInteger getConsecPrestamo() {
        return consecPrestamo;
    }

    public void setConsecPrestamo(BigInteger consecPrestamo) {
        this.consecPrestamo = consecPrestamo;
    }
}
