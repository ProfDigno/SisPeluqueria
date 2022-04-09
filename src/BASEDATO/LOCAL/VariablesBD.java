/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BASEDATO.LOCAL;

/**
 *
 * @author Digno Tala
 */
public class VariablesBD {  

    /**
     * @return the PsNombreConexion
     */
    public static String getPsNombreConexion() {
        return PsNombreConexion;
    }

    /**
     * @param aPsNombreConexion the PsNombreConexion to set
     */
    public static void setPsNombreConexion(String aPsNombreConexion) {
        PsNombreConexion = aPsNombreConexion;
    }

    private static String PsNombreConexion;
    public static String PsLocalhost;
    public static String PsPort;
    public static String PsNomBD;
    public static String PsUsuario;
    public static String PsContrasena;
    public static String Psdirec_dump;
    public static String Psdirec_backup;
    public static String Psnombre_backup;
    public static String PsCrea_backup;
    public static String avilitar;


    /**
     * @return the PsLocalhost
     */
    public static String getPsLocalhost() {
        return PsLocalhost;
    }

    /**
     * @param aPsLocalhost the PsLocalhost to set
     */
    public static void setPsLocalhost(String aPsLocalhost) {
        PsLocalhost = aPsLocalhost;
    }

    /**
     * @return the PsPort
     */
    public static String getPsPort() {
        return PsPort;
    }

    /**
     * @param aPsPort the PsPort to set
     */
    public static void setPsPort(String aPsPort) {
        PsPort = aPsPort;
    }

    /**
     * @return the PsNomBD
     */
    public static String getPsNomBD() {
        return PsNomBD;
    }

    /**
     * @param aPsNomBD the PsNomBD to set
     */
    public static void setPsNomBD(String aPsNomBD) {
        PsNomBD = aPsNomBD;
    }

    /**
     * @return the PsUsuario
     */
    public static String getPsUsuario() {
        return PsUsuario;
    }

    /**
     * @param aPsUsuario the PsUsuario to set
     */
    public static void setPsUsuario(String aPsUsuario) {
        PsUsuario = aPsUsuario;
    }

    /**
     * @return the PsContrasena
     */
    public static String getPsContrasena() {
        return PsContrasena;
    }

    /**
     * @param aPsContrasena the PsContrasena to set
     */
    public static void setPsContrasena(String aPsContrasena) {
        PsContrasena = aPsContrasena;
    }

    /**
     * @return the avilitar
     */
    public static String getAvilitar() {
        return avilitar;
    }

    /**
     * @param aAvilitar the avilitar to set
     */
    public static void setAvilitar(String aAvilitar) {
        avilitar = aAvilitar;
    }

    public static String getPsdirec_dump() {
        return Psdirec_dump;
    }

    public static void setPsdirec_dump(String Psdirec_dump) {
        VariablesBD.Psdirec_dump = Psdirec_dump;
    }

    public static String getPsdirec_backup() {
        return Psdirec_backup;
    }

    public static void setPsdirec_backup(String Psdirec_backup) {
        VariablesBD.Psdirec_backup = Psdirec_backup;
    }

    public static String getPsnombre_backup() {
        return Psnombre_backup;
    }

    public static void setPsnombre_backup(String Psnombre_backup) {
        VariablesBD.Psnombre_backup = Psnombre_backup;
    }

    public static String getPsCrea_backup() {
        return PsCrea_backup;
    }

    public static void setPsCrea_backup(String PsCrea_backup) {
        VariablesBD.PsCrea_backup = PsCrea_backup;
    }
    
}
