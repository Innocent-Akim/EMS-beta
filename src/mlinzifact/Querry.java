/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlinzifact;

import Models.Facturation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 *
 * @author willson
 */
public class Querry extends dbo {

    private static Querry _instance;

    public static Querry getInstance() {
        return _instance == null ? _instance = new Querry() : _instance;
    }

    public static String getID(String table) {
        String date = LocalDateTime.now().toString().replace(":", "").replace(" ", "").replace("T", "").trim().replace("-", "").replace(".", "");
        return date;
    }

    public static String getIdTable(String table) {
        return getValue("SELECT count(*)+1 FROM " + table);
    }

    public static String getIdFacture(String table) {
        Facturation.saveID(Vars.USER_ID_ENTREP);
        return getValue("SELECT MAX(code) FROM " + table + " WHERE id_entrep='" + Vars.USER_ID_ENTREP + "'");
    }

    public void SelectDataFor(Button... bt) {
        try {
            for (Button tr : bt) {
                tr.setStyle("-fx-background-color: #fff;-fx-text-fill: #000000;");
                tr.setFont(Font.font("Century Gothic", FontWeight.MEDIUM, 12));
            }
            bt[0].setStyle("-fx-background-color: #5b846d;-fx-text-fill: #fff;");
            bt[0].setFont(Font.font("Century Gothic", FontWeight.BOLD, 12));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean exist(String table, String id) {
        return getValue("select id from " + table + " where id=?", id) != null;
    }

    public static String getFirstDate() {
        return getValue("SELECT DATE_ADD(LAST_DAY(DATE_ADD(CURRENT_DATE,INTERVAL -1 MONTH)),INTERVAL 1 DAY) AS x");
    }

    public boolean deleteAgent(String codeAgent) {
        return Querry.execute("UPDATE agent set deleted_date=CURRENT_TIMESTAMP,deleted_etat=1 where code='" + codeAgent + "'");
    }

    public static String getDate() {
        return getValue("SELECT CURRENT_DATE AS x");
    }

    public static String getId() {
        int annee = LocalDate.now().getYear();
        int jour = LocalDate.now().getDayOfMonth();
        int mois = LocalDate.now().getMonthValue();
        int nano = LocalDateTime.now().getNano();
        int nano2 = LocalDateTime.now().getNano();
        String date = String.valueOf(annee).substring(2, 4) + "" + jour + "" + mois + "" + nano + nano2;
        return date.substring(0, 10);
    }

    public static String getBarcode() {
        int annee = LocalDate.now().getYear();
        int jour = LocalDate.now().getDayOfMonth();
        int mois = LocalDate.now().getMonthValue();
        int nano = LocalDateTime.now().getNano();
        int nano2 = LocalDateTime.now().getNano();
        String date = String.valueOf(annee).substring(2, 4) + "" + jour + "" + mois + "" + nano;
        return date.substring(0, 10);
    }

    public static boolean execute(String rqt) {
        try {
            pst = Con().prepareStatement(rqt);
            pst.execute();
            return true;
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
            System.err.println(rqt + "\n==>" + e);
        }
        return false;
    }

    public static int getCountBy(String rqt) {
        int x = 0;
        try {
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            while (rs.next()) {
                x++;
            }
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
            System.err.println(e);
        }
        return x;
    }

    public static String setUrl(String name, String id) {
        String ext = ".png";
        return Querry.getValue("select valeur from url where name='" + name + "'") + id + ext;
    }

    public static String getValue(String rqt) {

        String x = null;
        try {
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
            System.err.println(e);
        }
        return x;
    }

    public static ResultSet getRs(String rqt, String... param) {

        try {
            pst = Con().prepareStatement(rqt);
            int i = 1;
            if (param != null) {
                for (String string : param) {
                    pst.setString(i, string);
                    i++;
                }
            }
            return pst.executeQuery();

        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static String getValue(String rqt, String... param) {

        String x = null;
        try {
            pst = Con().prepareStatement(rqt);
            int i = 1;
            if (param != null) {
                for (String string : param) {
                    pst.setString(i, string);
                    i++;
                }
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
        }
        return x;
    }

    public static float getValueFloat(String rqt, String... param) {

        float x = 0;
        try {
            pst = Con().prepareStatement(rqt);
            int i = 1;
            if (param != null) {
                for (String string : param) {
                    pst.setString(i, string);
                    i++;
                }
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getFloat(1);
            }
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
        }
        return x;
    }

    public static int getValueInt(String rqt, String... param) {

        int x = 0;
        try {
            pst = Con().prepareStatement(rqt);
            int i = 1;
            if (param != null) {
                for (String string : param) {
                    pst.setString(i, string);
                    i++;
                }
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
        }
        return x;
    }

    public static ArrayList<String> getValueList(String rqt, String... param) {

        ArrayList<String> list = new ArrayList();
        try {
            pst = Con().prepareStatement(rqt);
            int i = 1;
            if (param != null) {
                for (String string : param) {
                    pst.setString(i, string);
                    i++;
                }
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
        }
        return list;
    }

    public static ResultSet getData(String query) {
        try {
            PreparedStatement preparedStatement = Con().prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
        } finally {
            if (Con() != null) {

            }

        }
        return null;
    }

    public static ResultSet getAll(String rqt, String... param) {
        String x = null;
        String rqt_ = "";
        try {
            pst = Con().prepareStatement(rqt);
            int i = 1;
            if (param != null) {
                for (String string : param) {
                    pst.setString(i, string);
                    rqt_ = rqt_ + "," + string;
                    i++;
                }
            }
            return pst.executeQuery();
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static boolean execute(String rqt, String... param) {
        try {
            pst = Con().prepareStatement(rqt);
            int i = 1;
            if (param != null) {
                for (String string : param) {
                    pst.setString(i, string);
                    i++;
                }
            }
            return !pst.execute();

        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
            cls.c.setAlert("Une erreur s'est produite." + e.getMessage(), Alert.AlertType.WARNING);
            System.err.println(rqt + "-->>" + e.getMessage());
        }
        return false;
    }

    public static void ImageExecute(byte[] image, String table, String colone, String refrence) {
        pst = null;
        try {
            pst = Con().prepareStatement("update " + table + " set " + colone + "=? where code=?");
            pst.setBytes(1, image);
            pst.setString(2, refrence);
            pst.executeUpdate();
        } catch (SQLException ex) {
            fichiers.error("IMG LOADER ==>Error " + ex.getMessage());
            Logger.getLogger(Querry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean exist(String table, String colone, String element) {
        boolean x = false;
        String rqt = "select * from " + table + " where " + colone + "='" + element + "'";
        try {
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
            System.err.println(e);
        }
        return x;
    }

    public static String getLastId(String table) {
        String x = null;
        try {
            pst = Con().prepareStatement("select coalesce(max(code),0)+1 from " + table);
            rs = pst.executeQuery();
            if (rs.next()) {
                x = rs.getString(1);
            }
        } catch (SQLException e) {
            fichiers.error("GET LAST ID " + "==>Error " + e.getMessage());
            System.err.println(e);
        }
        return x;
    }

    public static boolean isValidated(String codeop) {
        boolean Value = false;
        try {
            pst = Con().prepareStatement("select code from validation where codeOperation='" + codeop + "'");
            rs = pst.executeQuery();
            if (rs.next()) {
                Value = true;
            }
        } catch (SQLException ex) {
            fichiers.error("IS VALIDED" + "==>Error " + ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return Value;
    }

    public static boolean isValidated(String codeop, String codeuser) {
        boolean Value = false;
        try {
            pst = Con().prepareStatement("select * from validation where codeOperation='" + codeop + "' and codeUser='" + codeuser + "'");
            rs = pst.executeQuery();
            if (rs.next()) {
                Value = true;
            }
        } catch (SQLException ex) {
            fichiers.error("IS VALIDETED" + "==>Error " + ex.getMessage());
        }
        return Value;
    }

    public static ArrayList<String> getArray(String rqt) {
        ArrayList<String> liste = new ArrayList();
        try {
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (!liste.contains(rs.getString(1))) {
                    liste.add(rs.getString(1));
                }

            }
        } catch (SQLException e) {
        }
        return liste;
    }

    public static void loadCombo(ComboBox<String> combo, String rqt) {
        try {
            combo.getItems().clear();
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (!combo.getItems().contains(rs.getString(1))) {
                    combo.getItems().add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
        }
    }

    public static void loadCombo(ComboBox<String> combo, String rqt, String element) {
        try {
            combo.getItems().clear();
            pst = Con().prepareStatement(rqt);
            pst.setString(1, element);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (!combo.getItems().contains(rs.getString(1))) {
                    combo.getItems().add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            fichiers.error(rqt + "==>Error " + e.getMessage());
            System.err.println("||| " + e);
        }
    }

    public static ResultSet getRs(String rqt) {
        try {
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            return rs;
        } catch (SQLException ex) {
            fichiers.error(rqt + "==>Error " + ex.getMessage());
            System.err.println("Error : " + rqt + " ---->> " + ex.getMessage());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static void chargerTable(String SQL, TableView table, int taille) {
        try {
            //        System.out.println(SQL);
            ObservableList<ObservableList> oblist = FXCollections.observableArrayList();
            table.getColumns().clear();

            rs = Con().createStatement().executeQuery(SQL);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setPrefWidth((table.getPrefWidth() / rs.getMetaData().getColumnCount()) + taille);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Object>, ObservableValue<Object>>() {
                    @Override
                    public ObservableValue<Object> call(TableColumn.CellDataFeatures<ObservableList, Object> param) {
                        return new SimpleObjectProperty(param.getValue().get(j)); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                table.getColumns().addAll(col);
            }
            while (rs.next()) {

                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {

                    row.add(rs.getString(i));
                }
                oblist.add(row);
            }
            table.setItems(oblist);
        } catch (SQLException ex) {
            fichiers.error(SQL + "==>Error " + ex.getMessage());
        }
    }

    public static ObservableList<String> onTableClick(TableView maTable) {
        ObservableList<String> o = null;
        try {
            o = (ObservableList) maTable.getItems().get(maTable.getSelectionModel().getSelectedIndex());

        } catch (Exception e) {
            fichiers.error("GET OBSERVABLE VALUE" + "==>Error " + e.getMessage());
        }
        return o;
    }
}
