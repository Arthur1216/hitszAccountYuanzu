package com.example.jumptonext;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bill {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Category")
    private String category;
    @ColumnInfo(name = "Subcategory")
    private String subcategory;
    @ColumnInfo(name = "Year")
    private int year;
    @ColumnInfo(name = "Month")
    private int month;
    @ColumnInfo(name = "Day")
    private int day;
    @ColumnInfo(name = "Hour")
    private int hour;
    @ColumnInfo(name = "Minute")
    private int minute;
    @ColumnInfo(name = "Cost")
    private double cost;
    @ColumnInfo(name = "Kind")
    private String kind;
    @ColumnInfo(name = "UserName")
    private String userName;
    @ColumnInfo(name = "Business")
    private String business;
    @ColumnInfo(name = "Member")
    private String member;

    public Bill(String category, String subcategory, int year, int month, int day, int hour, int minute, double cost, String kind, String userName, String business, String member) {
        this.category = category;
        this.subcategory = subcategory;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.cost = cost;
        this.kind = kind;
        this.userName = userName;
        this.business = business;
        this.member = member;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
