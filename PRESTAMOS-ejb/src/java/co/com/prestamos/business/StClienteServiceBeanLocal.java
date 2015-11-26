/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.prestamos.business;


import co.com.prestamos.entities.Cuota;
import co.com.prestamos.entities.Prestamo;
import co.com.prestamos.entities.ReportePrestamoDTO;
import co.com.prestamos.entities.Usuario;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author egonzalm
 */
@Local
public interface StClienteServiceBeanLocal {

    public int count();

    public List<Usuario> findRange(int[] range);

    public List<Usuario> findAll();

    public Usuario find(Object id);
//
//    public void remove(StProduct entity);
//    
//    public void edit(StCliente entity);
//
    public void registrarPrestamo(Prestamo entity,Usuario usuario) throws Exception;
//
    public boolean create(Usuario entity);
//
    public void create(Cuota entity);
    
    public List<Prestamo> obtenerPrestamosPorUsuario(Usuario usuario);
//
//    public void updateProductCurrent(StProduct entity);
//
//    public void updateTimeCurrent(StTiempos stTiempos);
//    
//    public void updateHoraryCurrent(StHorario stHorario);
//
//    public boolean isValidTerceroByNit(Long Nit);
//
//    public boolean editTerceroFacade(StCliente ceTercero);
//
//    public List<StCliente> findAllCustomersByNit(String NitCustomer);
//
//    public List<StCliente> findAllCustomersByName(String NameCustomer);
//
//    public List<StCliente> findAllCustomersByName2(String NameCustomer);
//
//    public List<StCliente> findAllCustomersByNit2(String NitCustomer);
//
//    public List<StCliente> findAllCustomersByNameX(String NameCustomer);
//
//    public List<StCliente> findAllCustomersByNitX(String NitCustomer);
//
//    public List<StCliente> findAllClienteDescription(String description);
//
//    public StCliente obtenerTerceroPorNit(Long nit);
//
//    public StCliente getStClienteAlmaviva();
//
//    public List<StTippro> findAllTipProd();
//
//    public List<StProveed> findAllProveed();
//
//    public List<StTipprod> findAllTipoProducto();
//
//    public List<StTipserv> findAllServ();
//    
//    public List<StTipserv> findAllServExcepGeneral();
//
//    public List<StCliprov> findAllCliProvLogBytipServ(Long tipoServ, StCliente cliente);
//    
//    public List<StCliprov> findAllProvByServGeneral(StCliente cliente);
//    
//    public List<StCliprov> findAllProvOnlyServGeneral(StCliente cliente);
//    
//    public List<StCliprov> findAllCliProvLog( StCliente cliente);
//
//    public List<StTipserv> findService(Long l);
//
//    public List<StTipserv> findService();
//
//    public void updateProveedCurrent(StCliprov cliprov);
//
//    public void remove(StCliprov entity);
//    
//    public void remove(StHorario entity);
//
//    public List<StCarga> findCarga(StCliprov stCliprov);
//
//    public List<StCliprov> findAllCliProveePro(StCliente clienteSeleccionado);
//
//    public List<StProduct> findAllProductosXcliente(StCliente stCliente);
//
//    public List<StCategor> findAllCategorByClient(StCliente stCliente);
//
//    public List<StModelo> findAllModeloByClient(StCliente stCliente);
//
//    public List<StTipprod> findAllTipProByClient(StCliente stCliente);
//
//    public List<StDetfact> findDetfactXproduct(StProduct stProduct);
//
//    public List<StTiempos> findAlltimesNormal();
//
//    public List<StTiempos> findAlltimesGeneral();
//
//    public List<StRuta> findAllRutas();
//
//    public List<StRutprov> findRutasByparams(Long l, String s);
//
//    public List<StTipdia> findAllTypesDays();
//
//    public StCliprov findIdCliProveeXtiempo(Long ltipoServ, Long cli_tpcconsec);
//
//    public List<StHorario> findAllHorarios(); 
//    
//    public List<StDiasema> findAllDaysWeek();
//
//    public List<StDepclie> findAllDepositByClient(StCliente clienteSeleccionado);
//
//    public List<StDeposit> findAllDepositUnassociated();

    public List<Usuario> findAllClienteDescription(String description) throws Exception;
    
    public List<Prestamo> listarTodosLosCodigosPrestamosPorUsuario(BigDecimal idUsuario) throws Exception;

    public List<Cuota> listaCoutasPorIdPrestamo(BigDecimal prestamo);

    public void registroUsuarioPrestamo(Long intIdusuario);

    public List<Prestamo> validarConsecutivo(BigInteger consecPrestamo);

    public void actualizarPrestamo(Prestamo prestamoActualizar);

    public void crearCuota(List<Cuota> list);

    public List<ReportePrestamoDTO> obtenerDatosReporte(Usuario clienteSeleccionado);

}
