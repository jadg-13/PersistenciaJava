/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import models.Category;

/**
 *
 * @author jadg13
 */
public class DaoCategory {

    Conexion conexion = new Conexion();
    Connection conn = null;
    ResultSet rs = null;
    private PreparedStatement ps = null;

    /**
     * Constructor de la clase DaoCategory inicia las consultas
     */
    public DaoCategory() {

    }

    public void getRecords() {
        conn = conexion.obtenerConexion();
        String tSQL = "Select * from Categories";
        try {
            ps = conn.prepareStatement(tSQL,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE,
                    ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Llena el campo listCategory de tipo arrayList con todos los registros de
     * la tabla Categories
     */
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> listCategory = new ArrayList<>();
        try {
            this.getRecords();
            while (rs.next()) {    
                listCategory.add(new Category(rs.getInt("IDCategory"),
                        rs.getString("NameCategory")));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las Categorias");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conexion.close(conn);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return listCategory;
    }

    public boolean isCategory(int id) {
        boolean flag = false;
        this.getRecords();
        try {
            rs.beforeFirst();

            while (rs.next()) {
                if (rs.getInt("IDCategory") == id) {
                    flag = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el registro por el id: " + id + " el error fue: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conexion.close(conn);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return flag;
    }

    public Category getCategoriesByID(int id) {
        Category category = new Category();
        this.getRecords();
        try {
            rs.beforeFirst();

            while (rs.next()) {
                if (rs.getInt("IDCategory") == id) {
                    category.setIdCategory(rs.getInt("IDCategory"));
                    category.setNameCategory(rs.getString("NameCategory"));
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el registro por el id: " + id + " el error fue: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conexion.close(conn);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return category;
    }

    /**
     * Obtiene el ultimo registro almacenado y le suma 1
     *
     * @return
     */
    public int getLastCategory() {
        int id = 0;
        this.getRecords();
        try {
            rs.afterLast();
            if (rs.next()) {
                id = rs.getInt("IDCategory");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el ultimo id: " + e.getMessage());
        }
        return id + 1;
    }

    /**
     * Devuelve un ComboBox
     *
     * @return
     */
    public DefaultComboBoxModel showCategoriesCombo() {
        this.getRecords();
        DefaultComboBoxModel<Category> combo = new DefaultComboBoxModel<>();
        
        try {
            while (rs.next()) {
                Category category = new Category(rs.getInt("IDCategory"),
                        rs.getString("NameCategory"));
                combo.addElement(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return combo;
    }

    public boolean addCategory(Category category) {
        boolean flag = true;
        this.getRecords();
        try {
            rs.moveToInsertRow();
            rs.updateString("NameCategory", category.getNameCategory());
            rs.insertRow();
            rs.moveToCurrentRow();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar categoria: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conexion.close(conn);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return flag;
    }

    public boolean updateCategories(Category category) {
        boolean flag = false;
        try {
            this.getRecords();
            rs.beforeFirst();
            while (rs.next()) {
                if (rs.getInt("IDCategory") == category.getIdCategory()) {
                    rs.updateString("NameCategory", category.getNameCategory());
                    rs.updateRow();
                    flag = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar categoria: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conexion.close(conn);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return flag;
    }

    public boolean deleteCategories(Category category) {
        boolean flag = false;
        try {
            this.getRecords();
            rs.beforeFirst();
            while (rs.next()) {
                if (rs.getInt("IDCategory") == category.getIdCategory()) {
                    rs.deleteRow();
                    flag = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoria: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conexion.close(conn);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return flag;
    }

}
