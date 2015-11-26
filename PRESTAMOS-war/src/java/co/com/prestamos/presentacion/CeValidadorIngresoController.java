/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.prestamos.presentacion;






import co.com.prestamos.entities.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "ceValidadorIngresoController")
@SessionScoped
public class CeValidadorIngresoController implements Serializable {

    private static final long serialVersionUID = 1391804396951797431L;

//    @EJB
//    private CeValidadorIngresoServiceBeanLocal ceValUsuarioServiceBean;
//    @EJB
//    private StUsuarioServiceBeanLocal ceMaUsuariosService;
//    @EJB
//    private CeMaOficinaServiceBeanLocal ceMaOficinaService;
//    @EJB
//    private CeMaPerfilServiceBeanLocal ceMaPerfilService;
//    @EJB
//    private CeUsuarioPerfilServiceBeanLocal ceUsuarioPerfilService;

    private String username;
    private String password;
    private String usuarioEnSesion;
    private Boolean prueba;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuarioEnSesion() {
        return usuarioEnSesion;
    }

    public void setUsuarioEnSesion(String usuarioEnSesion) {
        this.usuarioEnSesion = usuarioEnSesion;
    }

    public Boolean getPrueba() {
        return prueba;
    }

    public void setPrueba(Boolean prueba) {
        this.prueba = prueba;
    }

    public void cerrarCession() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath() + "/faces/Login.xhtml");
    }

    public String getUsuarioEnSession() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        Usuario stUsuario = (Usuario) session.getAttribute("usuario");

        if (stUsuario == null) {
            return null;
        }

        return stUsuario.getUsuNombre();
    }

    public String getMenu() {
//        Se quita porque no se utiliza por el momento, menu de base de datos
//        Boolean prueba = Boolean.valueOf(MessageUtil.getValueGeneral("AplicacionEnPrueba"));
//        try {
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//
//            if (prueba) {
//                HttpSession session = request.getSession(true);
//                StUsuario usuario = ceMaUsuariosService.find(261L);
//                session.setAttribute("usuario", usuario);
//                setUsuarioEnSesion(usuario.getUsuNom());
//                setPrueba(true);
//            } else {
//                setPrueba(false);
//            }
//        } catch (Exception e) {
//        }
        return "Menu.xhtml";
    }

    public String ceValidadorIngreso() throws ServletException, IOException {
        LdapContext ldapCtx = null;
        Usuario usuario = new Usuario();
        usuario.setUsuNombre(username);

        FacesMessage msg = null;
        boolean loggedIn = false;
//        try {
//            ldapCtx = getLdapContext(this.getUsername(), this.getPassword());
//        } catch (NamingException nex) {
//            if (nex.getMessage().contains("52e")) {
//                //clave incorrecta
//                loggedIn = false;
//                JsfUtil.addErrorMessage(MessageUtil.getValueLogin("LoginErrorPass"));
//                return null;
//            }
//
//            if (nex.getMessage().contains("525")) {
//                //usuario incorrecto
//                loggedIn = false;
//                JsfUtil.addErrorMessage(MessageUtil.getValueLogin("LoginErrorUser"));
//                return null;
//            }
//
//            if (nex.getMessage().contains("775")) {
//                //usuario blockeado por intentos
//                loggedIn = false;
//                JsfUtil.addErrorMessage(MessageUtil.getValueLogin("LoginErrorBloqIntenFall"));
//                return null;
//            }
//        }
//
//        if (ldapCtx != null) {
//            usuario = validarLDAPUsua(ldapCtx, this.getUsername());
//        }

        if (usuario.getUsuNombre() != null) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuario);

            this.setUsuarioEnSesion(usuario.getUsuNombre());

            loggedIn = true;
            return "index";

        } else {
            loggedIn = false;
            JsfUtil.addErrorMessage(MessageUtil.getValueLogin("LoginErrorPass"));
            return null;
        }

    }

    public LdapContext getLdapContext(String usuario, String clave) throws NamingException {
        LdapContext ctx = null;

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "almaviva\\" + usuario);
        env.put(Context.SECURITY_CREDENTIALS, clave);
        env.put(Context.PROVIDER_URL, "ldap://DCDG01:389");
        ctx = new InitialLdapContext(env, null);

        return ctx;
    }

    public Usuario validarLDAPUsua(LdapContext ldapCtx, String nomUsuario) {

        int count = 0;
        String memberOfAttrValue;
        Usuario ceMaUsuarios = new Usuario();
        try {
            String searchBase = "OU=ALMAVIVA,DC=Almaviva,DC=loc";
            StringBuilder searchFilter = new StringBuilder("(&");
            searchFilter.append("(objectClass=person)");
            searchFilter.append("(userPrincipalName=");
            searchFilter.append(nomUsuario);
            searchFilter.append("@almaviva.loc)");
            searchFilter.append(")");
            String returnAttrs[] = {"memberOf"/*EnumAtributosLDAP.MIEMBRO.toString()*/, "displayName"/*EnumAtributosLDAP.NOMBRECOMPLETO.toString()*/,
                "mailNickName"/*EnumAtributosLDAP.CORREO.toString()*/, "physicalDeliveryOfficeName"/*EnumAtributosLDAP.OFICINA.toString()*/};
            SearchControls sCtrl = new SearchControls();
            sCtrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
            sCtrl.setReturningAttributes(returnAttrs);
            NamingEnumeration<SearchResult> answer = ldapCtx.search(searchBase, searchFilter.toString(), sCtrl);

            if (!answer.hasMoreElements()) {
                return null;
            }

            // Loop through the results and check every single value in attribute "memberOf"
            while (answer.hasMoreElements()) {
                SearchResult sr = answer.next();
                Usuario obj = null;
//                obj = ceMaUsuariosService.buscarUsuario(nomUsuario);
                //Verificacion usuario base de datos
                if (obj == null) {
                    //Si no existe en DB entra aca y lo crea en DB.
//                    ceMaUsuarios.setOfiId(null);
//                    ceMaUsuarios.setUsuNomred(nomUsuario);
                    ceMaUsuarios.setUsuCorreo(sr.getAttributes().get(returnAttrs[2]).get().toString() + "@almaviva.com.co");
                    ceMaUsuarios.setUsuNombre(sr.getAttributes().get(returnAttrs[1]).get().toString());

//                    ceMaUsuariosService.create(ceMaUsuarios);
                } else {
                    ceMaUsuarios = obj;
                }
            }

            //No se implementa porque los perfiles no se tienen todavia
//                //Verificacion Perfil
//                NamingEnumeration enumeration = sr.getAttributes().get(returnAttrs[0]).getAll();
//
//                ArrayList<String> perfiles = new ArrayList<String>();
//                while (enumeration.hasMore()) {
//                    Object vector = enumeration.next();
//                    String[] b = vector.toString().split(",");
//                    for (String c : b) {
//                        if (c.contains("CN=SEG")) {
//                            perfiles.add(c.substring(7));
//                        }
//                    }
//                }
//
//                ArrayList<CeMaPerfil> ceMaPerfils = new ArrayList<CeMaPerfil>();
//                for (String perfil : perfiles) {
//                    CeMaPerfil ceMaPerfil = ceMaPerfilService.consultaPerfilPorNombre(perfil);
//
//                    if (ceMaPerfil == null) {
//                        ceMaPerfil = new CeMaPerfil();
//                        ceMaPerfil.setPerNom(perfil);
//                        ceMaPerfilService.create(ceMaPerfil);
//                        ceMaPerfils.add(ceMaPerfil);
//                    } else {
//                        ceMaPerfils.add(ceMaPerfil);
//                    }
//                }
//
//                //Verificacion el perfil del usuario
//                for (CeMaPerfil ceMaPerfil : ceMaPerfils) {
//                    CeUsuPerfil ceUsuPerfil = ceUsuarioPerfilService.consultaUsuarioPerfil(ceMaPerfil.getPerId(), ceMaUsuarios.getUsuId());
//                    if (ceUsuPerfil == null) {
//                        ceUsuPerfil = new CeUsuPerfil();
//                        ceUsuPerfil.setPerId(ceMaPerfil);
//                        ceUsuPerfil.setUsuId(ceMaUsuarios);
//                        ceUsuarioPerfilService.create(ceUsuPerfil);
//                    }
//                }
//            }
        } catch (NamingException ex) {
        }
        return ceMaUsuarios;
    }
}
