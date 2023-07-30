/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Category;
import models.Product;

/**
 *
 * @author jadg13
 */
public class DaoProducts {

    private Conexion conexion = new Conexion();
    private Connection conn;
    private ArrayList<Product> listProducts;
    private PreparedStatement selectAllProducts;
    private PreparedStatement selectProductByID;
    private PreparedStatement selectProductByName;
    private PreparedStatement selectProductByCategory;
    private PreparedStatement insertProduct;
    private PreparedStatement updateProduct;
    private PreparedStatement deleteProduct;

    public DaoProducts() {
        try {
            conn = conexion.obtenerConexion();
            selectAllProducts = conn.prepareStatement("SELECT Products.IDProduct, Products.NameProduct, Products.PriceProduct, "
                    + "DAY(Products.DueDate) as N'Day', MONTH(Products.DueDate) as N'Month', YEAR(Products.DueDate) as N'Year', "
                    + "Categories.IDCategory, Categories.NameCategory FROM Products INNER JOIN"
                    + " Categories ON Products.CategoryProduct = Categories.IDCategory");
            selectProductByID = conn.prepareStatement("SELECT Products.IDProduct, Products.NameProduct, Products.PriceProduct, "
                    + "DAY(Products.DueDate) as N'Day', MONTH(Products.DueDate) as N'Month', YEAR(Products.DueDate) as N'Year', "
                    + "Categories.IDCategory, Categories.NameCategory FROM Products INNER JOIN"
                    + " Categories ON Products.CategoryProduct = Categories.IDCategory where Products.IDProduct = ?");
            selectProductByName = conn.prepareStatement("SELECT Products.IDProduct, Products.NameProduct, Products.PriceProduct, "
                    + "DAY(Products.DueDate) as N'Day', MONTH(Products.DueDate) as N'Month', YEAR(Products.DueDate) as N'Year', "
                    + "Categories.IDCategory, Categories.NameCategory FROM Products INNER JOIN"
                    + " Categories ON Products.CategoryProduct = Categories.IDCategory where Products.NameProduct like ?");
            selectProductByCategory = conn.prepareStatement("SELECT Products.IDProduct, Products.NameProduct, Products.PriceProduct, "
                    + "DAY(Products.DueDate) as N'Day', MONTH(Products.DueDate) as N'Month', YEAR(Products.DueDate) as N'Year', "
                    + "Categories.IDCategory, Categories.NameCategory FROM Products INNER JOIN"
                    + " Categories ON Products.CategoryProduct = Categories.IDCategory where Categories.NameCategory like ?");
            insertProduct = conn.prepareStatement("insert into Products(IDProduct, NameProduct, PriceProduct, DueDate, CategoryProduct) "
                    + "values(?, ?, ?, ?, ?)");
            updateProduct = conn.prepareStatement("update Products set NameProduct = ?, PriceProduct = ?, DueDate = ?, CategoryProduct = ? where IDProduct = ?");
            deleteProduct = conn.prepareStatement("Delete from Products where IDProduct = ?");

        } catch (SQLException e) {
            System.out.println("Error al instanciar Producto " + e.getMessage());
        }
    }

    public ArrayList<Product> getListProducts() {
        return listProducts;
    }

    public void setListProducts(ArrayList<Product> listProducts) {
        this.listProducts = listProducts;
    }

    public void getAllProducts() {
        try {
            listProducts = new ArrayList<>();

            ResultSet rs = selectAllProducts.executeQuery();
            while (rs.next()) {
                listProducts.add(new Product(
                        rs.getString("IDProduct"),
                        rs.getString("NameProduct"),
                        rs.getDouble("PriceProduct"),
                        LocalDate.of(rs.getInt("Year"), rs.getInt("Month"), rs.getInt("Day")),
                        new Category(rs.getInt("IDCategory"), rs.getString("NameCategory"))
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los registros: " + e.getMessage());
        }
    }

    public Product getProductByID(String id) {
        Product product = null;
        try {
            selectProductByID.setString(1, id);
            ResultSet rs = selectProductByID.executeQuery();
            if (rs.next()) {
                product = new Product(rs.getString("IDProduct"),
                        rs.getString("NameProduct"),
                        rs.getDouble("PriceProduct"),
                        LocalDate.of(rs.getInt("Year"), rs.getInt("Month"), rs.getInt("Day")),
                        new Category(rs.getInt("IDCategory"), rs.getString("NameCategory")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoProducts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return product;
    }

    public DefaultTableModel getProductByName(String name) {
        DefaultTableModel tbl = new DefaultTableModel();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            selectProductByName.setString(1, "%" + name + "%");
            ResultSet rs = selectProductByName.executeQuery();
            String titles[] = {"CODIGO", "NOMBRE", "PRECIO", "VENCE", "CATEGORIA", "IDCATEGORIA"};
            tbl.setColumnIdentifiers(titles);
            while (rs.next()) {
                Product prod = new Product(rs.getString("IDProduct"),
                        rs.getString("NameProduct"),
                        rs.getDouble("PriceProduct"),
                        LocalDate.of(rs.getInt("Year"), rs.getInt("Month"), rs.getInt("Day")),
                        new Category(rs.getInt("IDCategory"), rs.getString("NameCategory")));

                Object datos[] = new Object[6];
                datos[0] = prod.getIdProduct();
                datos[1] = prod.getNameProduct();
                datos[2] = prod.getPriceProduct();
                datos[3] = prod.getDueDate().format(formatter);
                datos[4] = prod.getCategoryProduct();
                datos[5] = prod.getCategoryProduct().getIdCategory();
                tbl.addRow(datos);
            }

        } catch (Exception e) {
            System.out.println("Error al general tabla " + e.getMessage());
        }
        return tbl;
    }

    public DefaultTableModel getProductByCategory(String category) {
        DefaultTableModel tbl = new DefaultTableModel();
        try {
            selectProductByCategory.setString(1, "%" + category + "%");
            ResultSet rs = selectProductByCategory.executeQuery();
            String titles[] = {"CODIGO", "NOMBRE", "PRECIO", "VENCE", "CATEGORIA", "IDCATEGORIA"};
            tbl.setColumnIdentifiers(titles);
            while (rs.next()) {
                Product prod = new Product(rs.getString("IDProduct"),
                        rs.getString("NameProduct"),
                        rs.getDouble("PriceProduct"),
                        LocalDate.of(rs.getInt("Year"), rs.getInt("Month"), rs.getInt("Day")),
                        new Category(rs.getInt("IDCategory"), rs.getString("NameCategory")));

                Object datos[] = new Object[6];
                datos[0] = prod.getIdProduct();
                datos[1] = prod.getNameProduct();
                datos[2] = prod.getPriceProduct();
                datos[3] = prod.getDueDate();
                datos[4] = prod.getCategoryProduct();
                datos[5] = prod.getCategoryProduct().getIdCategory();
                tbl.addRow(datos);
            }

        } catch (Exception e) {
            System.out.println("Error al general tabla " + e.getMessage());
        }
        return tbl;
    }

    public int addProduct(Product prod) {
        int flag = 0;
        try {
            insertProduct.setString(1, prod.getIdProduct());
            insertProduct.setString(2, prod.getNameProduct());
            insertProduct.setDouble(3, prod.getPriceProduct());
            insertProduct.setDate(4, Date.valueOf(prod.getDueDate()));
            insertProduct.setInt(5, prod.getCategoryProduct().getIdCategory());
            flag = insertProduct.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al ingresar Producto. " + e.getMessage());
        }
        return flag;
    }

    public int updateProducts(Product prod) {
        int flag = 0;
       
        try {
            updateProduct.setString(5, prod.getIdProduct());
            updateProduct.setString(1, prod.getNameProduct());
            updateProduct.setDouble(2, prod.getPriceProduct());
            updateProduct.setDate(3, Date.valueOf(prod.getDueDate()));
            updateProduct.setInt(4, prod.getCategoryProduct().getIdCategory());
            flag = updateProduct.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al editar Producto. " + e.getMessage());
        }
        return flag;
    }

    public int deleteProducts(String id) {
        int flag = 0;
        try {
            deleteProduct.setString(1, id);
            flag = deleteProduct.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar registro." +e.getMessage());
        }
        return flag;
    }

}
