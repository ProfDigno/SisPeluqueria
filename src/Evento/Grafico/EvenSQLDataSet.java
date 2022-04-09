/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Grafico;

//import ClaseUTIL.FunMensaje;
//import ClaseUTIL.SQLprepar;
import BASEDATO.EvenConexion;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JTable;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Digno
 */
public class EvenSQLDataSet {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();

    public void createDataset_caja_ingreso_egreso(Connection conn, DefaultCategoryDataset dataset, String campo_fecha, String campo, String filtro_fecha) {
        String titulo = "createDataset_caja_ingreso_egreso";
        String sql = "select " + campo_fecha + " as fecha,\n"
                + "sum(monto_venta) as INGRESO_venta,\n"
                + "sum(monto_gasto+monto_vale+monto_compra) as EGRESO_gasto_compra_vale, \n"
                + "sum(monto_venta) as VENTA,\n"
                + "sum(monto_gasto) as GASTO,\n"
                + "sum(monto_vale) as VALE,\n"
                + "sum(monto_delivery) as DELIVERY,\n"
                + "sum(monto_compra) as COMPRA_INS \n"
                + "from caja_detalle \n"
                + "where  " + filtro_fecha
                + " group by 1 order by 1 asc";
        String seriedt = campo;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(campo);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public void createDataset_cantidad_cliente(Connection conn, DefaultCategoryDataset dataset, String campo_fecha, String campo, String filtro_fecha) {
        String titulo = "createDataset_cantidad_cliente";
        String sql = "select " + campo_fecha + " as fecha,\n"
                + "count(*) as CANTIDAD_CLIENTE\n"
                + "from cliente \n"
                + "where  " + filtro_fecha
                + " group by 1 order by 1 asc";
        String seriedt = campo;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(campo);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public DefaultPieDataset createDataset_precio_insumo(Connection conn, int idproducto) {
        String titulo = "createDataset_producto";
        DefaultPieDataset dataset = new DefaultPieDataset();
        String sql = "select (ip.nombre) as insumo,\n"
                + "((((ip.precio+((ip.precio*ip.merma)/100))/iu.conversion_unidad)*iip.cantidad)) as precio_insumo \n"
                + "from insumo_item_producto iip,insumo_producto ip,insumo_unidad iu\n"
                + "where iip.fk_idinsumo_producto=ip.idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad \n"
                + "and iip.fk_idproducto=" + idproducto;
        String seriedt = "INSUMOS";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(2);
                dataset.setValue(nombre, valor);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }

    public void createDataset_INSUMO_PRODUCTO(Connection conn, DefaultCategoryDataset dataset,
            String campo_fecha, String campo, String filtro_fecha, int idinsumo_producto) {
        String titulo = "createDataset_cantidad_cliente";
        String sql = "select " + campo_fecha + ",\n"
                + "sum(ii.cantidad*iv.cantidad) as TOTAL_CANTIDAD,\n"
                + "sum(ii.precio*iv.cantidad) as TOTAL_MONTO\n"
                + "from insumo_producto ip,insumo_item_producto iip,itemven_insumo ii,"
                + "item_venta iv,venta v,insumo_categoria ic,insumo_unidad iu\n"
                + "where ip.idinsumo_producto=iip.fk_idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and iip.idinsumo_item_producto=ii.fk_idinsumo_item_producto\n"
                + "and ii.fk_iditem_venta=iv.iditem_venta\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO')\n"
                + "and ip.idinsumo_producto=" + idinsumo_producto + " " + filtro_fecha
                + " group by 1 "
                + "ORDER BY 1 ASC";
        String seriedt = campo;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(campo);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public void createDataset_producto_mas_comprado(Connection conn, DefaultCategoryDataset dataset, String filtro_fecha, String idcategoria) {
        String titulo = "createDataset_caja_ingreso_egreso";
        String sql = "select \n"
                + "(ip.nombre||'-'||iu.nom_compra) as producto_insumo,\n"
                + "sum(ici.cantidad*ici.precio)  as subtotal\n"
                + "from compra_insumo ci,item_compra_insumo ici,insumo_producto ip,\n"
                + "insumo_categoria ic,insumo_unidad iu\n"
                + "where  ci.idcompra_insumo=ici.fk_idcompra_insumo\n"
                + "and ici.fk_idinsumo_producto=ip.idinsumo_producto\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and ci.estado='CONFIRMADO' \n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n " + filtro_fecha + idcategoria
                + " group by 1 \n"
                + " order by sum(ici.cantidad*ici.precio) desc";
        String seriedt = "COMPRA INSUMO";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(2);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public void createDataset_producto_mas_vendido(Connection conn, DefaultCategoryDataset dataset, String filtro_fecha, String idcategoria) {
        String titulo = "createDataset_producto_mas_vendido";
        String sql = "select \n"
                + "(ic.nombre||'-'||ip.nombre||'-'||iu.nom_compra) as producto_insumo,\n"
                + "(sum(ii.precio*iv.cantidad)) as TOTAL_MONTO\n"
                + "from insumo_producto ip,\n"
                + "insumo_item_producto iip,\n"
                + "itemven_insumo ii,item_venta iv,venta v,insumo_categoria ic,insumo_unidad iu\n"
                + "where ip.idinsumo_producto=iip.fk_idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and iip.idinsumo_item_producto=ii.fk_idinsumo_item_producto\n"
                + "and ii.fk_iditem_venta=iv.iditem_venta\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n" + filtro_fecha + idcategoria
                + " group by 1 "
                + "ORDER BY 2 desc";
        String seriedt = "VENTA INSUMO";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(2);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public DefaultCategoryDataset createDataset_venta_semana1(Connection conn, int ano, int semana, JTable tabla) {
        String titulo = "createDataset_venta_semana";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "select date(fecha_emision) as fecha,\n"
                + "CASE  \n"
                + "WHEN date_part('dow', fecha_emision)=0 THEN date_part('day',fecha_emision)||'-DOM' \n"
                + "WHEN date_part('dow', fecha_emision)=1 THEN date_part('day',fecha_emision)||'-LUN' \n"
                + "WHEN date_part('dow', fecha_emision)=2 THEN date_part('day',fecha_emision)||'-MAR' \n"
                + "WHEN date_part('dow', fecha_emision)=3 THEN date_part('day',fecha_emision)||'-MIE' \n"
                + "WHEN date_part('dow', fecha_emision)=4 THEN date_part('day',fecha_emision)||'-JUE' \n"
                + "WHEN date_part('dow', fecha_emision)=5 THEN date_part('day',fecha_emision)||'-VIE' \n"
                + "WHEN date_part('dow', fecha_emision)=6 THEN date_part('day',fecha_emision)||'-SAB' \n"
                + "ELSE 'sin dia' END as dia,\n"
                + "sum(total_venta_efectivo+total_venta_tarjeta) as s_venta \n"
                + "from caja_detalle \n"
                + "where  date_part('year',fecha_emision)=" + ano
                + " and date_part('week',fecha_emision)=" + semana
                + " group by 1,2 order by 1 asc";
        String sql_1 = "select date(fecha_emision) as fecha,\n"
                + "CASE  \n"
                + "WHEN date_part('dow', fecha_emision)=0 THEN date_part('day',fecha_emision)||'-DOM' \n"
                + "WHEN date_part('dow', fecha_emision)=1 THEN date_part('day',fecha_emision)||'-LUN' \n"
                + "WHEN date_part('dow', fecha_emision)=2 THEN date_part('day',fecha_emision)||'-MAR' \n"
                + "WHEN date_part('dow', fecha_emision)=3 THEN date_part('day',fecha_emision)||'-MIE' \n"
                + "WHEN date_part('dow', fecha_emision)=4 THEN date_part('day',fecha_emision)||'-JUE' \n"
                + "WHEN date_part('dow', fecha_emision)=5 THEN date_part('day',fecha_emision)||'-VIE' \n"
                + "WHEN date_part('dow', fecha_emision)=6 THEN date_part('day',fecha_emision)||'-SAB' \n"
                + "ELSE 'sin dia' END as dia,\n"
                + "TRIM(to_char(sum(total_venta_efectivo+total_venta_tarjeta),'999G999G999')) as s_venta \n"
                + "from caja_detalle \n"
                + "where  date_part('year',fecha_emision)=" + ano
                + " and date_part('week',fecha_emision)=" + semana
                + " group by 1,2 order by 1 asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            String seriedt = "VENTA SEMANA";
            while (rs.next()) {
                String nombre = (rs.getString(2));
                double valor = rs.getDouble(3);
                dataset.addValue(valor, seriedt, nombre);
            }
            eveconn.Select_cargar_jtable(conn, sql_1, tabla);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }

    public DefaultCategoryDataset createDataset_venta_tres_semana(Connection conn, int ano, int semana) {
        String titulo = "createDataset_venta_semana";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "select date(fecha_emision) as fecha,\n"
                + "CASE  \n"
                + "WHEN date_part('dow', fecha_emision)=0 THEN 'DOM' \n"
                + "WHEN date_part('dow', fecha_emision)=1 THEN 'LUN' \n"
                + "WHEN date_part('dow', fecha_emision)=2 THEN 'MAR' \n"
                + "WHEN date_part('dow', fecha_emision)=3 THEN 'MIE' \n"
                + "WHEN date_part('dow', fecha_emision)=4 THEN 'JUE' \n"
                + "WHEN date_part('dow', fecha_emision)=5 THEN 'VIE' \n"
                + "WHEN date_part('dow', fecha_emision)=6 THEN 'SAB' \n"
                + "ELSE 'sin dia' END as dia,\n"
                + "sum(total_venta_efectivo+total_venta_tarjeta) as s_venta \n"
                + "from caja_detalle \n"
                + "where  date_part('year',fecha_emision)=" + ano
                + " and date_part('week',fecha_emision)=" + semana
                + " group by 1,2 order by 1 asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            String seriedt = "ESTA SEMANA";
            while (rs.next()) {
                String nombre = (rs.getString(2));
                double valor = rs.getDouble(3);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        String sql1 = "select date(fecha_emision) as fecha,\n"
                + "CASE  \n"
                + "WHEN date_part('dow', fecha_emision)=0 THEN 'DOM' \n"
                + "WHEN date_part('dow', fecha_emision)=1 THEN 'LUN' \n"
                + "WHEN date_part('dow', fecha_emision)=2 THEN 'MAR' \n"
                + "WHEN date_part('dow', fecha_emision)=3 THEN 'MIE' \n"
                + "WHEN date_part('dow', fecha_emision)=4 THEN 'JUE' \n"
                + "WHEN date_part('dow', fecha_emision)=5 THEN 'VIE' \n"
                + "WHEN date_part('dow', fecha_emision)=6 THEN 'SAB' \n"
                + "ELSE 'sin dia' END as dia,\n"
                + "sum(total_venta_efectivo+total_venta_tarjeta) as s_venta \n"
                + "from caja_detalle \n"
                + "where  date_part('year',fecha_emision)=" + ano
                + " and date_part('week',fecha_emision)=" + (semana - 1)
                + " group by 1,2 order by 1 asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql1, titulo);
            String seriedt = "UNA SEMANA ATRAS";
            while (rs.next()) {
                String nombre = (rs.getString(2));
                double valor = rs.getDouble(3);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql1, titulo);
        }
        String sql2 = "select date(fecha_emision) as fecha,\n"
                + "CASE  \n"
                + "WHEN date_part('dow', fecha_emision)=0 THEN 'DOM' \n"
                + "WHEN date_part('dow', fecha_emision)=1 THEN 'LUN' \n"
                + "WHEN date_part('dow', fecha_emision)=2 THEN 'MAR' \n"
                + "WHEN date_part('dow', fecha_emision)=3 THEN 'MIE' \n"
                + "WHEN date_part('dow', fecha_emision)=4 THEN 'JUE' \n"
                + "WHEN date_part('dow', fecha_emision)=5 THEN 'VIE' \n"
                + "WHEN date_part('dow', fecha_emision)=6 THEN 'SAB' \n"
                + "ELSE 'sin dia' END as dia,\n"
                + "sum(total_venta_efectivo+total_venta_tarjeta) as s_venta \n"
                + "from caja_detalle \n"
                + "where  date_part('year',fecha_emision)=" + ano
                + " and date_part('week',fecha_emision)=" + (semana - 2)
                + " group by 1,2 order by 1 asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql2, titulo);
            String seriedt = "DOS SEMANA ATRAS";
            while (rs.next()) {
                String nombre = (rs.getString(2));
                double valor = rs.getDouble(3);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql2, titulo);
        }
        return dataset;
    }

    public DefaultCategoryDataset createDataset_venta_mes_dia(Connection conn, int ano, int mes, JTable tabla) {
        String titulo = "createDataset_venta_mes";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "select date(fecha_emision) as fecha,\n"
                + "CASE  \n"
                + "WHEN date_part('dow', fecha_emision)=0 THEN date_part('day',fecha_emision)||'-DOM' \n"
                + "WHEN date_part('dow', fecha_emision)=1 THEN date_part('day',fecha_emision)||'-LUN' \n"
                + "WHEN date_part('dow', fecha_emision)=2 THEN date_part('day',fecha_emision)||'-MAR' \n"
                + "WHEN date_part('dow', fecha_emision)=3 THEN date_part('day',fecha_emision)||'-MIE' \n"
                + "WHEN date_part('dow', fecha_emision)=4 THEN date_part('day',fecha_emision)||'-JUE' \n"
                + "WHEN date_part('dow', fecha_emision)=5 THEN date_part('day',fecha_emision)||'-VIE' \n"
                + "WHEN date_part('dow', fecha_emision)=6 THEN date_part('day',fecha_emision)||'-SAB' \n"
                + "ELSE 'sin dia' END as dia,\n"
                + "sum(total_venta_efectivo+total_venta_tarjeta) as s_venta \n"
                + "from caja_detalle \n"
                + "where  date_part('year',fecha_emision)=" + ano
                + " and date_part('month',fecha_emision)=" + mes
                + " group by 1,2 order by 1 asc";
        String sql_1 = "select date(fecha_emision) as fecha,\n"
                + "CASE  \n"
                + "WHEN date_part('dow', fecha_emision)=0 THEN date_part('day',fecha_emision)||'-DOM' \n"
                + "WHEN date_part('dow', fecha_emision)=1 THEN date_part('day',fecha_emision)||'-LUN' \n"
                + "WHEN date_part('dow', fecha_emision)=2 THEN date_part('day',fecha_emision)||'-MAR' \n"
                + "WHEN date_part('dow', fecha_emision)=3 THEN date_part('day',fecha_emision)||'-MIE' \n"
                + "WHEN date_part('dow', fecha_emision)=4 THEN date_part('day',fecha_emision)||'-JUE' \n"
                + "WHEN date_part('dow', fecha_emision)=5 THEN date_part('day',fecha_emision)||'-VIE' \n"
                + "WHEN date_part('dow', fecha_emision)=6 THEN date_part('day',fecha_emision)||'-SAB' \n"
                + "ELSE 'sin dia' END as dia,\n"
                + "TRIM(to_char(sum(total_venta_efectivo+total_venta_tarjeta),'999G999G999')) as s_venta \n"
                + "from caja_detalle \n"
                + "where  date_part('year',fecha_emision)=" + ano
                + " and date_part('month',fecha_emision)=" + mes
                + " group by 1,2 order by 1 asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            String seriedt = "VENTA MES";
            while (rs.next()) {
                String nombre = (rs.getString(2));
                double valor = rs.getDouble(3);
                dataset.addValue(valor, seriedt, nombre);
            }
            eveconn.Select_cargar_jtable(conn, sql_1, tabla);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }

    public DefaultCategoryDataset createDataset_venta_mes_trimestrales(Connection conn, int ano, int mes1, int mes2, int mes3, JTable tabla) {
        String titulo = "createDataset_venta_mes_trimestrales";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "select date_part('year',fecha_emision) as ano,\n"
                + "CASE  \n"
                + "WHEN date_part('month', fecha_emision)=1 THEN 'ENERO' \n"
                + "WHEN date_part('month', fecha_emision)=2 THEN 'FEBRERO' \n"
                + "WHEN date_part('month', fecha_emision)=3 THEN 'MARZO' \n"
                + "WHEN date_part('month', fecha_emision)=4 THEN 'ABRIL' \n"
                + "WHEN date_part('month', fecha_emision)=5 THEN 'MAYO' \n"
                + "WHEN date_part('month', fecha_emision)=6 THEN 'JUNIO' \n"
                + "WHEN date_part('month', fecha_emision)=7 THEN 'JULIO'\n"
                + "WHEN date_part('month', fecha_emision)=8 THEN 'AGOSTO'\n"
                + "WHEN date_part('month', fecha_emision)=9 THEN 'SETIEMBRE'\n"
                + "WHEN date_part('month', fecha_emision)=10 THEN 'OCTUBRE'\n"
                + "WHEN date_part('month', fecha_emision)=11 THEN 'NOVIEMBRE'\n"
                + "WHEN date_part('month', fecha_emision)=12 THEN 'DICIEMBRE' \n"
                + "ELSE 'sin mes' END as mes,"
                + "sum(total_venta_efectivo+total_venta_tarjeta) as s_venta \n"
                + "from caja_detalle \n"
                + "where  (date_part('year',fecha_emision)=" + (ano - 1) + " or date_part('year',fecha_emision)=" + ano + " ) \n"
                + "and ( date_part('month',fecha_emision)=" + mes1 + " "
                + "or date_part('month',fecha_emision)=" + mes2 + " "
                + "or date_part('month',fecha_emision)=" + mes3 + " )\n"
                + "group by 1,2;";
        String sql_1 = "select date_part('year',fecha_emision) as ano,\n"
                + "CASE  \n"
                + "WHEN date_part('month', fecha_emision)=1 THEN 'ENERO' \n"
                + "WHEN date_part('month', fecha_emision)=2 THEN 'FEBRERO' \n"
                + "WHEN date_part('month', fecha_emision)=3 THEN 'MARZO' \n"
                + "WHEN date_part('month', fecha_emision)=4 THEN 'ABRIL' \n"
                + "WHEN date_part('month', fecha_emision)=5 THEN 'MAYO' \n"
                + "WHEN date_part('month', fecha_emision)=6 THEN 'JUNIO' \n"
                + "WHEN date_part('month', fecha_emision)=7 THEN 'JULIO'\n"
                + "WHEN date_part('month', fecha_emision)=8 THEN 'AGOSTO'\n"
                + "WHEN date_part('month', fecha_emision)=9 THEN 'SETIEMBRE'\n"
                + "WHEN date_part('month', fecha_emision)=10 THEN 'OCTUBRE'\n"
                + "WHEN date_part('month', fecha_emision)=11 THEN 'NOVIEMBRE'\n"
                + "WHEN date_part('month', fecha_emision)=12 THEN 'DICIEMBRE' \n"
                + "ELSE 'sin mes' END as mes,"
                + "TRIM(to_char(sum(total_venta_efectivo+total_venta_tarjeta),'999G999G999')) as s_venta \n"
                + "from caja_detalle \n"
                + "where  (date_part('year',fecha_emision)=" + (ano - 1) + " or date_part('year',fecha_emision)=" + ano + " ) \n"
                + "and ( date_part('month',fecha_emision)=" + mes1 + " "
                + "or date_part('month',fecha_emision)=" + mes2 + " "
                + "or date_part('month',fecha_emision)=" + mes3 + " )\n"
                + "group by 1,2;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String seriedt = (rs.getString(1));
                String nombre = (rs.getString(2));
                double valor = rs.getDouble(3);
                dataset.addValue(valor, seriedt, nombre);
            }
            eveconn.Select_cargar_jtable(conn, sql_1, tabla);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }

    public DefaultCategoryDataset createDataset_venta_mes_trimestrales(Connection conn, int ano, int trimestre, JTable tabla) {
        String titulo = "createDataset_venta_mes_trimestrales";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "select date_part('year',fecha_emision) as ano,\n"
                + "CASE  \n"
                + "WHEN date_part('quarter', fecha_emision)=1 THEN '1ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=2 THEN '2ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=3 THEN '3ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=4 THEN '4ºTRIMESTRE' \n"
                + "ELSE 'sin mes' END as mes,"
                + "sum(total_venta_efectivo+total_venta_tarjeta) as s_venta \n"
                + "from caja_detalle \n"
                + "where  (date_part('year',fecha_emision)=" + (ano - 1) + " or date_part('year',fecha_emision)=" + (ano) + " ) \n"
                + "and date_part('quarter',fecha_emision)=" + trimestre + " \n"
                + "group by 1,2;";
        String sql_1 = "select date_part('year',fecha_emision) as ano,\n"
                + "CASE  \n"
                + "WHEN date_part('quarter', fecha_emision)=1 THEN '1ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=2 THEN '2ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=3 THEN '3ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=4 THEN '4ºTRIMESTRE' \n"
                + "ELSE 'sin mes' END as mes,"
                + "TRIM(to_char(sum(total_venta_efectivo+total_venta_tarjeta),'999G999G999')) as s_venta \n"
                + "from caja_detalle \n"
                + "where  (date_part('year',fecha_emision)=" + (ano - 1) + " or date_part('year',fecha_emision)=" + (ano) + " ) \n"
                + "and date_part('quarter',fecha_emision)=" + trimestre + " \n"
                + "group by 1,2;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String seriedt = (rs.getString(1));
                String nombre = (rs.getString(2));
                double valor = rs.getDouble(3);
                dataset.addValue(valor, seriedt, nombre);
            }
            eveconn.Select_cargar_jtable(conn, sql_1, tabla);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }

    public DefaultCategoryDataset createDataset_venta_mes_entero(Connection conn, int ano, int mes, JTable tabla) {
        String titulo = "createDataset_venta_mes_trimestrales";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "select date_part('year',fecha_emision) as ano,\n"
                + "CASE  \n"
                + "WHEN date_part('month', fecha_emision)=1 THEN 'ENERO' \n"
                + "WHEN date_part('month', fecha_emision)=2 THEN 'FEBRERO' \n"
                + "WHEN date_part('month', fecha_emision)=3 THEN 'MARZO' \n"
                + "WHEN date_part('month', fecha_emision)=4 THEN 'ABRIL' \n"
                + "WHEN date_part('month', fecha_emision)=5 THEN 'MAYO' \n"
                + "WHEN date_part('month', fecha_emision)=6 THEN 'JUNIO' \n"
                + "WHEN date_part('month', fecha_emision)=7 THEN 'JULIO'\n"
                + "WHEN date_part('month', fecha_emision)=8 THEN 'AGOSTO'\n"
                + "WHEN date_part('month', fecha_emision)=9 THEN 'SETIEMBRE'\n"
                + "WHEN date_part('month', fecha_emision)=10 THEN 'OCTUBRE'\n"
                + "WHEN date_part('month', fecha_emision)=11 THEN 'NOVIEMBRE'\n"
                + "WHEN date_part('month', fecha_emision)=12 THEN 'DICIEMBRE' \n"
                + "ELSE 'sin mes' END as mes,"
                + "sum(total_venta_efectivo+total_venta_tarjeta) as s_venta \n"
                + "from caja_detalle \n"
                + "where  (date_part('year',fecha_emision)=" + (ano - 1) + " or date_part('year',fecha_emision)=" + ano + " ) \n"
                + "and ( date_part('month',fecha_emision)=" + mes + " )\n"
                + "group by 1,2;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String seriedt = (rs.getString(1));
                String nombre = (rs.getString(2));
                double valor = rs.getDouble(3);
                dataset.addValue(valor, seriedt, nombre);
            }
            eveconn.Select_cargar_jtable(conn, sql, tabla);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }

    public DefaultCategoryDataset createDataset_venta_ano_mes(Connection conn, int ano, JTable tabla) {
        String titulo = "createDataset_venta_mes_trimestrales";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "select date_part('year',fecha_emision) as ano,date_part('month',fecha_emision) as imes,\n"
                + "CASE  \n"
                + "WHEN date_part('month', fecha_emision)=1 THEN 'ENERO' \n"
                + "WHEN date_part('month', fecha_emision)=2 THEN 'FEBRERO' \n"
                + "WHEN date_part('month', fecha_emision)=3 THEN 'MARZO' \n"
                + "WHEN date_part('month', fecha_emision)=4 THEN 'ABRIL' \n"
                + "WHEN date_part('month', fecha_emision)=5 THEN 'MAYO' \n"
                + "WHEN date_part('month', fecha_emision)=6 THEN 'JUNIO' \n"
                + "WHEN date_part('month', fecha_emision)=7 THEN 'JULIO'\n"
                + "WHEN date_part('month', fecha_emision)=8 THEN 'AGOSTO'\n"
                + "WHEN date_part('month', fecha_emision)=9 THEN 'SETIEMBRE'\n"
                + "WHEN date_part('month', fecha_emision)=10 THEN 'OCTUBRE'\n"
                + "WHEN date_part('month', fecha_emision)=11 THEN 'NOVIEMBRE'\n"
                + "WHEN date_part('month', fecha_emision)=12 THEN 'DICIEMBRE' \n"
                + "ELSE 'sin mes' END as mes,"
                + "sum(total_venta_efectivo+total_venta_tarjeta) as s_venta \n"
                + "from caja_detalle \n"
                + "where  (date_part('year',fecha_emision)=" + (ano - 1) + " or date_part('year',fecha_emision)=" + (ano) + " ) \n"
                + "group by 1,2,3 order by 2 asc;";
        String sql_1 = "select date_part('year',fecha_emision) as ano,date_part('month',fecha_emision) as imes,\n"
                + "CASE  \n"
                + "WHEN date_part('month', fecha_emision)=1 THEN 'ENERO' \n"
                + "WHEN date_part('month', fecha_emision)=2 THEN 'FEBRERO' \n"
                + "WHEN date_part('month', fecha_emision)=3 THEN 'MARZO' \n"
                + "WHEN date_part('month', fecha_emision)=4 THEN 'ABRIL' \n"
                + "WHEN date_part('month', fecha_emision)=5 THEN 'MAYO' \n"
                + "WHEN date_part('month', fecha_emision)=6 THEN 'JUNIO' \n"
                + "WHEN date_part('month', fecha_emision)=7 THEN 'JULIO'\n"
                + "WHEN date_part('month', fecha_emision)=8 THEN 'AGOSTO'\n"
                + "WHEN date_part('month', fecha_emision)=9 THEN 'SETIEMBRE'\n"
                + "WHEN date_part('month', fecha_emision)=10 THEN 'OCTUBRE'\n"
                + "WHEN date_part('month', fecha_emision)=11 THEN 'NOVIEMBRE'\n"
                + "WHEN date_part('month', fecha_emision)=12 THEN 'DICIEMBRE' \n"
                + "ELSE 'sin mes' END as mes,"
                + "TRIM(to_char(sum(total_venta_efectivo+total_venta_tarjeta),'999G999G999')) as s_venta \n"
                + "from caja_detalle \n"
                + "where  (date_part('year',fecha_emision)=" + (ano - 1) + " or date_part('year',fecha_emision)=" + (ano) + " ) \n"
                + "group by 1,2,3 order by 2 asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String seriedt = (rs.getString(1));
                String nombre = (rs.getString(3));
                double valor = rs.getDouble(4);
                dataset.addValue(valor, seriedt, nombre);
            }
            eveconn.Select_cargar_jtable(conn, sql_1, tabla);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }
    public DefaultCategoryDataset createDataset_venta_ano_trimestrales(Connection conn, int ano, JTable tabla) {
        String titulo = "createDataset_venta_mes_trimestrales";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "select date_part('year',fecha_emision) as ano,\n"
                + "CASE  \n"
                + "WHEN date_part('quarter', fecha_emision)=1 THEN '1ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=2 THEN '2ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=3 THEN '3ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=4 THEN '4ºTRIMESTRE' \n"
                + "ELSE 'sin mes' END as mes,"
                + "sum(total_venta_efectivo+total_venta_tarjeta) as s_venta \n"
                + "from caja_detalle \n"
                + "where  (date_part('year',fecha_emision)=" + (ano - 1) + " or date_part('year',fecha_emision)=" + (ano) + " ) \n"
                + "group by 1,2 order by 2 asc";
        String sql_1 = "select date_part('year',fecha_emision) as ano,\n"
                + "CASE  \n"
                + "WHEN date_part('quarter', fecha_emision)=1 THEN '1ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=2 THEN '2ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=3 THEN '3ºTRIMESTRE' \n"
                + "WHEN date_part('quarter', fecha_emision)=4 THEN '4ºTRIMESTRE' \n"
                + "ELSE 'sin mes' END as mes,"
                + "TRIM(to_char(sum(total_venta_efectivo+total_venta_tarjeta),'999G999G999')) as s_venta \n"
                + "from caja_detalle \n"
                + "where  (date_part('year',fecha_emision)=" + (ano - 1) + " or date_part('year',fecha_emision)=" + (ano) + " ) \n"
                + "group by 1,2 order by 2 asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String seriedt = (rs.getString(1));
                String nombre = (rs.getString(2));
                double valor = rs.getDouble(3);
                dataset.addValue(valor, seriedt, nombre);
            }
            eveconn.Select_cargar_jtable(conn, sql_1, tabla);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }
}
