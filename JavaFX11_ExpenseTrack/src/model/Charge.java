package model;

import java.time.LocalDate;
import javafx.scene.image.Image;

public class Charge {
    private int id;
    private String name;
    private String description;
    private Category category;
    private double cost;
    private int units;
    private LocalDate date;
    private Image scanImage;

    public Charge(int id, String name, String description, Category category, double cost, int units, LocalDate date, Image scanImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.cost = cost;
        this.units = units;
        this.date = date;
        this.scanImage = scanImage;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public double getCost() { return cost; }
    public int getUnits() { return units; }
    public LocalDate getDate() { return date; }
    public Image getScanImage() { return scanImage; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(Category category) { this.category = category; }
    public void setCost(double cost) { this.cost = cost; }
    public void setUnits(int units) { this.units = units; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setScanImage(Image scanImage) { this.scanImage = scanImage; }
}
