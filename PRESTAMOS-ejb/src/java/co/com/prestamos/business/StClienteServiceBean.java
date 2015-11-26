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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author egonzalm
 */
@Stateless
public class StClienteServiceBean implements StClienteServiceBeanLocal {

    @PersistenceContext(unitName = "PRESTAMOS-ejbPU")
    private EntityManager em;
    private List<Usuario> listaC;

    public StClienteServiceBean() {
    }

    @Override
    public List<Usuario> findAllClienteDescription(String description) throws Exception {
        List<Usuario> terceroResultado = new ArrayList<Usuario>();
        List<Usuario> results = new ArrayList<Usuario>();
        //List<StCliente> results = query.setParameter("terNom", description).getResultList();
        if (isNumeric(description)) {
            Query query = em.createNamedQuery("Usuario.findByUsuCedula");
            results = query.setParameter("usuCedula", description).getResultList();
        } else {
            Query query = em.createNamedQuery("Usuario.findByUserLikeNombre");
            results = query.setParameter("usuNombre", "%" + description.trim().toUpperCase() + "%").getResultList();
        }
        return results;
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Cuota> listaCoutasPorIdPrestamo(BigDecimal prestamo) {
        Query query = em.createNamedQuery("Cuota.findByIntIdPrestamo", Cuota.class).setParameter("intIdprestamo", new Prestamo(prestamo));

        if (query.getResultList().isEmpty() || query.getResultList() == null) {
            return new ArrayList<Cuota>();
        }
        return query.getResultList();
    }

//    @Override
//    public List<StCliente> findAllClienteDescription(String description) {
//        List<StCliente> terceroResultado = new ArrayList<StCliente>();
//        List<StCliente> results = new ArrayList<StCliente>();
//        //List<StCliente> results = query.setParameter("terNom", description).getResultList();
//        if (isNumeric(description)) {
//            Long nitTercero = Long.parseLong(description);
//            Query query = em.createNamedQuery("StCliente.findByCliNit");
//            results = query.setParameter("cliNit", nitTercero).getResultList();
//        } else {
//            Query query = em.createNamedQuery("StCliente.findByCliLikeNombre");
//            results = query.setParameter("cliNombre", "%" + description.trim().toUpperCase() + "%").getResultList();
//        }
//        return results;
//    }
//
//    public static boolean isNumeric(String str) {
//        for (char c : str.toCharArray()) {
//            if (!Character.isDigit(c)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
    @Override
    public boolean create(Usuario usuario) {
        List<Usuario> listU = em.createNamedQuery("Usuario.findByIntIdusuario",Usuario.class).setParameter("intIdusuario", usuario.getIntIdusuario()).getResultList();
        if(!listU.isEmpty()){
            return false;
        }
        em.persist(usuario);
        return true;
    }

    @Override
    public void create(Cuota entity) {
        em.persist(entity);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarPrestamo(Prestamo entity,Usuario usuario) throws Exception{
        em.persist(entity);
        BigDecimal idMaxPrestamo = (BigDecimal)em.createNativeQuery("select max(p.int_idprestamo) from prestamo p").getSingleResult();
        em.createNativeQuery("insert into usuario_prestamo(int_idusuario,int_idprestamo) values (?,?)").setParameter(1, usuario.getIntIdusuario()).
                setParameter(2,idMaxPrestamo).executeUpdate();
    }

    @Override
    public List<Prestamo> listarTodosLosCodigosPrestamosPorUsuario(BigDecimal idUsuario) throws Exception {
        List<Prestamo> listPrestamos = new ArrayList<Prestamo>();
        Query query = em.createNativeQuery("select up.int_idprestamo from USUARIO_PRESTAMO up where up.int_idusuario = ?1");
        query.setParameter("1", idUsuario);
        List<BigDecimal> listIdsPrestamos = query.getResultList();
        for(BigDecimal decimal : listIdsPrestamos){
            listPrestamos.add(em.find(Prestamo.class, decimal));
        }
        return listPrestamos;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuario> findRange(int[] range) {
        return em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
    }

    @Override
    public List<Usuario> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario find(Object id) {
        return em.find(Usuario.class, id);
    }

    @Override
    public void registroUsuarioPrestamo(Long intIdusuario) {
       Long idUltimoPrestamo = (Long)em.createNativeQuery("select max(p.int_idprestamo) from prestamo p").getSingleResult();
       System.out.println("*****************max id de tabla prestamo********************");
       System.out.println("*****************"+idUltimoPrestamo+"********************");
       System.out.println("*****************max id de tabla prestamo********************");
       em.createNativeQuery("insert into USUARIO_PRESTAMO(int_idusuario,int_idprestamo) values(?,?)").
               setParameter(1,intIdusuario).
               setParameter(2,idUltimoPrestamo).
               executeUpdate();
    }

    @Override
    public List<Prestamo> validarConsecutivo(BigInteger consecPrestamo) {
        List<Prestamo> listP = new ArrayList<Prestamo>();
        listP = em.createNamedQuery("Prestamo.encontrarPorConsecutivo",Prestamo.class).setParameter("consecutivo", consecPrestamo).getResultList();
        return listP;
    }

    @Override
    public void actualizarPrestamo(Prestamo prestamoActualizar) {
        em.merge(prestamoActualizar);
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorUsuario(Usuario usuario) {
        List<Prestamo> listPrestamos = new ArrayList<Prestamo>();
        List<BigDecimal> listaIdsPrestamo = new ArrayList<BigDecimal>();
                listaIdsPrestamo =  em.createNativeQuery("select up.int_idprestamo from USUARIO_PRESTAMO up where up.int_idusuario = ?1").
                setParameter(1, usuario.getIntIdusuario()).getResultList();
        if(!listaIdsPrestamo.isEmpty()){
            for(BigDecimal idPrestamo : listaIdsPrestamo){
                listPrestamos.add(em.find(Prestamo.class,idPrestamo));
            }
        }
         return listPrestamos;
    }

    @Override
    public void crearCuota(List<Cuota> list) {
        for(Cuota cuota : list){
            em.persist(cuota);
        }
    }

    @Override
    public List<ReportePrestamoDTO> obtenerDatosReporte(Usuario clienteSeleccionado) {
        List<ReportePrestamoDTO> listaReports = new ArrayList<ReportePrestamoDTO>();
        try {
            List<Prestamo> listPrestamosPorUsuario = listarTodosLosCodigosPrestamosPorUsuario(new BigDecimal(clienteSeleccionado.getIntIdusuario()));
            List<Cuota> listCuotas = new ArrayList<Cuota>();
            
            ReportePrestamoDTO reportePrestamoDTO;
            for(Prestamo prestamo : listPrestamosPorUsuario){
                reportePrestamoDTO = new ReportePrestamoDTO();
                reportePrestamoDTO.setUsuario(clienteSeleccionado);
                reportePrestamoDTO.setPrestamo(prestamo);
                reportePrestamoDTO.setListCuotas(listaCoutasPorIdPrestamo(prestamo.getIntIdprestamo()));
                listaReports.add(reportePrestamoDTO);
            }
        } catch (Exception ex) {
            Logger.getLogger(StClienteServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
         return listaReports;
    }

}
