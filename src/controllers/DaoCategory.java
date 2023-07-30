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
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import models.Category;


/**
 *
 * @author jadg13
 */
public class DaoCategory {
    Conexion conexion = new Conexion();
    Connection conn ;
    private ArrayList<Category> listCategory ;
    private PreparedStatement selectAllCategory;
    private PreparedStatement selectCategoryById;
    private PreparedStatement selectCategoryByName;
    private PreparedStatement selectLastCategory;
    private PreparedStatement insertCategory;
    private PreparedStatement updateCategory;
    private PreparedStatement deleteCategory;
    
/**
 * Constructor de la clase DaoCategory
 * inicia las consultas
 */
    public DaoCategory() {
        try {
            conn = conexion.obtenerConexion();
            selectAllCategory = conn.prepareStatement("SELECT Categories.* FROM Categories order by NameCategory");
            selectCategoryById = conn.prepareStatement("SELECT Categories.* FROM Categories where IDCategory = ?");
            selectCategoryByName = conn.prepareStatement("SELECT Categories.* FROM Categories where NameCategory like ? order by NameCategory");
            selectLastCategory = conn.prepareStatement("select top 1 * from Categories order by IDCategory desc");
            insertCategory = conn.prepareStatement("Insert into Categories(NameCategory) values(?)");
            updateCategory = conn.prepareStatement("Update Categories set NameCategory = ? where IDCategory = ?");
            deleteCategory = conn.prepareStatement("Delete from Categories where IDCategory = ?");
            getAllCategories();
        } catch (SQLException e) {
            System.out.println("Error al instanciar la categoria: "+ e.getMessage());
        }
    }
    
    /**
     * Llena el campo listCategory de tipo arrayList con todos los registros de la
     * tabla Categories
     */
    public void getAllCategories(){
        ResultSet rs = null;
        listCategory = new ArrayList<>();
        try {
            rs = selectAllCategory.executeQuery();
            
            while(rs.next()){
                listCategory.add(new Category(rs.getInt("IDCategory"), 
                        rs.getString("NameCategory")));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las Categorias");
        }
    }
    
    /**
     * LLena una lista con el registro que tenga el id 
     * ingresado por el usuario
     * @param id 
     */
    public void getCategoriesByID(int id){
        ResultSet rs = null;
        listCategory = new ArrayList<>();
        try {
            selectCategoryById.setInt(1, id);
            rs = selectCategoryById.executeQuery();
            while(rs.next()){
                listCategory.add(new Category(rs.getInt("IDCategory"), 
                rs.getString("NameCategory")));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el registro por el id: "+ id+ " el error fue: "+ e.getMessage());
        }
    }
    
    /**
     * Obtiene el ultimo registro almacenado y le suma 1
     * @return 
     */
    public int getLastCategory(){
        int id=0;
        ResultSet rs = null;
        try {
            rs = selectLastCategory.executeQuery();
            if(rs.next()) id = rs.getInt("IDCategory");
        } catch (Exception e) {
            System.out.println("Error al obtener el ultimo id: "+ e.getMessage());
        }
        return id+1;
    }
    
    /**
     * Obtiene una tabla filtrando por nombre 
     * de categoria
     * @param name
     * @return 
     */
    public DefaultTableModel getCategoriesByName(String name){
        DefaultTableModel tbl = new DefaultTableModel();
        ResultSet rs = null;
        String titulos[]={"CÓDIGO", "CATEGORÍA"};
        tbl.setColumnIdentifiers(titulos);
        try {
            selectCategoryByName.setString(1, name);
            rs = selectCategoryByName.executeQuery();
            while(rs.next()){
                Object datos[] = new Object[2];
                datos[0] = rs.getInt("IDCategory");
                datos[1] = rs.getString("NameCategory");
                tbl.addRow(datos);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el registro por nombre: "+ name + " el error fue: "+ e.getMessage());
        }
        return tbl;
    }
    
    
//    Fue mi primera opcion y funciona pero encontre otra mejor 😍
//    public JComboBox<Category> showCategoriesCombo(){
//        JComboBox combo = new JComboBox<Category>();
//        this.getAllCategories();
//        for(Category category: this.listCategory){
//            combo.addItem(category);
//        }
//        return combo;
//    }
    
    /**
     * Devuelve un ComboBox
     * @return 
     */
    public DefaultComboBoxModel showCategoriesCombo(){
        DefaultComboBoxModel<Category> combo = new DefaultComboBoxModel<>();
        this.getAllCategories();
        for(Category category: this.listCategory){
            combo.addElement(category);
        }
        return combo;
    }
    
    public int addCategory(String nameCategory){
        int result =0;
        try {
            insertCategory.setString(1, nameCategory);
            result = insertCategory.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar categoria: "+e.getMessage());
        }
        return result;
    }
    
    public int updateCategories(String nameCategory, int idCategory){
        int result =0;
        try {
            updateCategory.setString(1, nameCategory);
            updateCategory.setInt(2, idCategory);
            result = updateCategory.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar categoria: "+ e.getMessage());
        }
        return result;
    }
    
    public int deleteCategories(int idCategory){
        int result = 0;
        try {
            deleteCategory.setInt(1, idCategory);
            result = deleteCategory.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoria: "+ e.getMessage());
        }
        return result;
    }
   

    public ArrayList<Category> getListCategory() {
        return listCategory;
    }

    public void setListCategory(ArrayList<Category> listCategory) {
        this.listCategory = listCategory;
    }
    
    
}
