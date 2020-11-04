package com.example.jumptonext;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserDefine {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Category")
    private String category;

    @ColumnInfo(name = "Subcategory")
    private String subcategory;

    @ColumnInfo(name = "Business")
    private String business;

    public UserDefine(String category, String subcategory, String business) {
        this.category = category;
        this.subcategory = subcategory;
        this.business = business;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
}
