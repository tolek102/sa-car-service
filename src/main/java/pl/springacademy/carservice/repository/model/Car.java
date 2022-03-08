package pl.springacademy.carservice.repository.model;

import pl.springacademy.carservice.model.Color;

public class Car {

    private Long id;
    private String mark;
    private String model;
    private Integer productionYear;
    private Color color;


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(final String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(final Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color color) {
        this.color = color;
    }
}
