/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDate;

/**
 *
 * @author jadg13
 * Clase Product correspondiente a la tabla Products
 */
public class Product {
    private String idProduct;
    private String nameProduct;
    private double priceProduct;
    private LocalDate dueDate;
    private Category categoryProduct;

    public Product() {
    }

    public Product(String idProduct, String nameProduct, double priceProduct, LocalDate dueDate, Category categoryProduct) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.dueDate = dueDate;
        this.categoryProduct = categoryProduct;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Category getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(Category categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    @Override
    public String toString() {
        return "Product{" + "idProduct=" + idProduct + ", nameProduct=" + nameProduct + ", priceProduct=" + priceProduct + ", dueDate=" + dueDate + ", categoryProduct=" + categoryProduct + '}';
    }
    
    
}
