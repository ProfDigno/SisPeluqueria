package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import Config_JSON.json_producto;
import FORMULARIO.ENTIDAD.producto;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_producto {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private json_producto jsonp = new json_producto();
    private String mensaje_insert = "PRODUCTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto(idproducto,fecha_creado,creado_por,orden,activo,cod_barra,nombre,"
            + "precio_venta,precio_compra,stock_actual,stock_minimo,stock_maximo,control_stock,"
            + "ult_venta,ult_compra,insumo,cantidad_uso,precio_por_uso,porcentaje_comision,"
            + "fk_idproducto_categoria,fk_idproducto_unidad) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto SET fecha_creado=?,creado_por=?,orden=?,activo=?,cod_barra=?,nombre=?,"
            + "precio_venta=?,precio_compra=?,stock_actual=?,stock_minimo=?,stock_maximo=?,control_stock=?,"
            + "ult_venta=?,ult_compra=?,insumo=?,cantidad_uso=?,precio_por_uso=?,porcentaje_comision=?,"
            + "fk_idproducto_categoria=?,fk_idproducto_unidad=? WHERE idproducto=?;";
    private String sql_select = "select p.idproducto as idp,p.cod_barra,pc.nombre as categoria,p.nombre as producto,pu.nombre as unid,\n"
            + "TRIM(to_char(p.precio_venta,'999G999G999')) as pventa,TRIM(to_char(p.precio_compra,'999G999G999')) as pcompra,\n"
            + "p.stock_actual as stock,(p.porcentaje_comision||'%') as comi \n"
            + "from producto p,producto_categoria pc,producto_unidad pu\n"
            + "where p.fk_idproducto_categoria=pc.idproducto_categoria\n"
            + "and p.fk_idproducto_unidad=pu.idproducto_unidad \n"
            + "";
    private String sql_cargar = "SELECT idproducto,fecha_creado,creado_por,orden,activo,cod_barra,nombre,precio_venta,precio_compra,"
            + "stock_actual,stock_minimo,stock_maximo,control_stock,"
            + "ult_venta,ult_compra,insumo,cantidad_uso,precio_por_uso,porcentaje_comision,"
            + "fk_idproducto_categoria,fk_idproducto_unidad "
            + "FROM producto WHERE idproducto=";
private String sql_select_venta = "select s.idproducto as idp,(sc.nombre||'-'||s.nombre) as producto,"
            + "TRIM(to_char(s.stock_actual,'999999')) as sto,"
            + "TRIM(to_char(s.precio_venta,'999G999G999')) as precio,s.precio_compra \n"
            + "from producto s,producto_categoria sc  \n"
            + "where s.fk_idproducto_categoria=sc.idproducto_categoria \n"
            + "and s.activo=true\n";

    private String sql_select_ord = " order by s.orden desc;";
    public void insertar_producto(Connection conn, producto prod) {
        prod.setC1idproducto(eveconn.getInt_ultimoID_mas_uno(conn, prod.getTb_producto(), prod.getId_idproducto()));
        String titulo = "insertar_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prod.getC1idproducto());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, prod.getC3creado_por());
            pst.setInt(4, prod.getC4orden());
            pst.setBoolean(5, prod.getC5activo());
            pst.setString(6, prod.getC6cod_barra());
            pst.setString(7, prod.getC7nombre());
            pst.setDouble(8, prod.getC8precio_venta());
            pst.setDouble(9, prod.getC9precio_compra());
            pst.setDouble(10, prod.getC10stock_actual());
            pst.setDouble(11, prod.getC11stock_minimo());
            pst.setDouble(12, prod.getC12stock_maximo());
            pst.setBoolean(13, prod.getC13control_stock());
            pst.setTimestamp(14, evefec.getTimestamp_sistema());
            pst.setTimestamp(15, evefec.getTimestamp_sistema());
            pst.setBoolean(16, prod.getC16insumo());
            pst.setDouble(17, prod.getC17cantidad_uso());
            pst.setDouble(18, prod.getC18precio_por_uso());
            pst.setDouble(19, prod.getC19porcentaje_comision());
            pst.setInt(20, prod.getC20fk_idproducto_categoria());
            pst.setInt(21, prod.getC21fk_idproducto_unidad());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prod.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prod.toString(), titulo);
        }
    }

    public void update_producto(Connection conn, producto prod) {
        String titulo = "update_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, prod.getC3creado_por());
            pst.setInt(3, prod.getC4orden());
            pst.setBoolean(4, prod.getC5activo());
            pst.setString(5, prod.getC6cod_barra());
            pst.setString(6, prod.getC7nombre());
            pst.setDouble(7, prod.getC8precio_venta());
            pst.setDouble(8, prod.getC9precio_compra());
            pst.setDouble(9, prod.getC10stock_actual());
            pst.setDouble(10, prod.getC11stock_minimo());
            pst.setDouble(11, prod.getC12stock_maximo());
            pst.setBoolean(12, prod.getC13control_stock());
            pst.setTimestamp(13, evefec.getTimestamp_sistema());
            pst.setTimestamp(14, evefec.getTimestamp_sistema());
            pst.setBoolean(15, prod.getC16insumo());
            pst.setDouble(16, prod.getC17cantidad_uso());
            pst.setDouble(17, prod.getC18precio_por_uso());
            pst.setDouble(18, prod.getC19porcentaje_comision());
            pst.setInt(19, prod.getC20fk_idproducto_categoria());
            pst.setInt(20, prod.getC21fk_idproducto_unidad());
            pst.setInt(21, prod.getC1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prod.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prod.toString(), titulo);
        }
    }

    public void cargar_producto(Connection conn, producto prod, int idproducto) {
        String titulo = "Cargar_producto";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto, titulo);
            if (rs.next()) {
                prod.setC1idproducto(rs.getInt(1));
                prod.setC2fecha_creado(rs.getString(2));
                prod.setC3creado_por(rs.getString(3));
                prod.setC4orden(rs.getInt(4));
                prod.setC5activo(rs.getBoolean(5));
                prod.setC6cod_barra(rs.getString(6));
                prod.setC7nombre(rs.getString(7));
                prod.setC8precio_venta(rs.getDouble(8));
                prod.setC9precio_compra(rs.getDouble(9));
                prod.setC10stock_actual(rs.getDouble(10));
                prod.setC11stock_minimo(rs.getDouble(11));
                prod.setC12stock_maximo(rs.getDouble(12));
                prod.setC13control_stock(rs.getBoolean(13));
                prod.setC14ult_venta(rs.getString(14));
                prod.setC15ult_compra(rs.getString(15));
                prod.setC16insumo(rs.getBoolean(16));
                prod.setC17cantidad_uso(rs.getDouble(17));
                prod.setC18precio_por_uso(rs.getDouble(18));
                prod.setC19porcentaje_comision(rs.getDouble(19));
                prod.setC20fk_idproducto_categoria(rs.getInt(20));
                prod.setC21fk_idproducto_unidad(rs.getInt(21));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + prod.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prod.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select+"order by 1 "+ jsonp.getOrder_select_prod(), tbltabla);
        ancho_tabla_producto(tbltabla);
    }

    public void ancho_tabla_producto(JTable tbltabla) {
        int Ancho[] = {5,15,10,25,10, 8, 8, 8, 8};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void buscar_tabla_producto(Connection conn, JTable tbltabla, JTextField txtbuscar,String columna) {
        if (txtbuscar.getText().trim().length() > 1) {
            String buscar = txtbuscar.getText();
            String sql = sql_select
                    + " and "+columna+" ilike'%" + buscar + "%' "
                    + "order by "+columna+jsonp.getOrder_select_prod();
            eveconn.Select_cargar_jtable(conn, sql, tbltabla);
            ancho_tabla_producto(tbltabla);
        }
    }
    public void actualizar_tabla_producto_venta(Connection conn, JTable tbltabla,String filtro) {
        String sql = sql_select_venta
                + filtro
                + sql_select_ord;
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_producto_venta(tbltabla);
    }

    public void ancho_tabla_producto_venta(JTable tbltabla) {
        int Ancho[] = {5, 65,15, 15,1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 4);
    }
    public void update_stock_actual_producto(Connection conn,String cantidad,String idproducto){
        String sql="update producto set stock_actual=(stock_actual-"+cantidad+") where idproducto="+idproducto+";";
        eveconn.SQL_execute_libre(conn, sql);
    }
}
