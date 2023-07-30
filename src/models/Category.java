/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author jadg13
 * Clase correspondiente a la tabla Categories
 */
public class Category {
    private int idCategory;
    private String nameCategory;

    public Category() {
    }

    public Category(int idCategory, String nameCategory) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
    }

    public Category(int idCategory) {
        this.idCategory = idCategory;
    }
    
    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return nameCategory;
    }

    
    @Override
    public boolean equals(Object obj) {
        final Category other = (Category) obj;
        return this.idCategory == other.idCategory;
    }
    
    
}
