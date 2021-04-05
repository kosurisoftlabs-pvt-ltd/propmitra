package com.portal.app.model;

import com.portal.app.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "prop_material")
public class PropertyMaterial extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_MAT_PKID")
    private Long id;

    @NotNull
    @Column(name = "PROP_MAT_DOOR")
    private String doors;

    @NotNull
    @Column(name = "PROP_MAT_WINDOW")
    private String windows;

    @NotNull
    @Column(name = "PROP_MAT_CUPBOARD")
    private String cupboards;

    @NotNull
    @Column(name = "PROP_MAT_WALL_PAINT")
    private String wallPaint;

    @NotNull
    @Column(name = "PROP_MAT_FLOOR")
    private String floor;

    @Column(name = "PROP_MAT_KITCHEN")
    private String kitchen;

    @Column(name = "PROP_MAT_WIRING")
    private String wiring;

    @NotNull
    @Column(name = "PROP_MAT_TOILET")
    private String toilet;

    @NotNull
    @Column(name = "PROP_MAT_LOCKS")
    private String locks;

    @NotNull
    @Column(name = "PROP_MAT_ELE_SWTCH")
    private String electricalSwitches;

    @NotNull
    @Column(name = "PROP_MAT_WATER_PIPES")
    private String waterPipes;

    @NotNull
    @Column(name = "PROP_MAT_SINK")
    private String sink;

    @NotNull
    @Column(name = "PROP_MAT_WASH_BASIN")
    private String washBasin;

    @OneToOne(mappedBy = "propertyMaterial")
    private PropertyMaster propertyMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public String getWindows() {
        return windows;
    }

    public void setWindows(String windows) {
        this.windows = windows;
    }

    public String getCupboards() {
        return cupboards;
    }

    public void setCupboards(String cupboards) {
        this.cupboards = cupboards;
    }

    public String getWallPaint() {
        return wallPaint;
    }

    public void setWallPaint(String wallPaint) {
        this.wallPaint = wallPaint;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public String getWiring() {
        return wiring;
    }

    public void setWiring(String wiring) {
        this.wiring = wiring;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getLocks() {
        return locks;
    }

    public void setLocks(String locks) {
        this.locks = locks;
    }

    public String getElectricalSwitches() {
        return electricalSwitches;
    }

    public void setElectricalSwitches(String electricalSwitches) {
        this.electricalSwitches = electricalSwitches;
    }

    public String getWaterPipes() {
        return waterPipes;
    }

    public void setWaterPipes(String waterPipes) {
        this.waterPipes = waterPipes;
    }

    public String getSink() {
        return sink;
    }

    public void setSink(String sink) {
        this.sink = sink;
    }

    public String getWashBasin() {
        return washBasin;
    }

    public void setWashBasin(String washBasin) {
        this.washBasin = washBasin;
    }

    public PropertyMaster getPropertyMaster() {
        return propertyMaster;
    }

    public void setPropertyMaster(PropertyMaster propertyMaster) {
        this.propertyMaster = propertyMaster;
    }

    @Override
    public String toString() {
        return "PropertyMaterial{" +
                "id=" + id +
                ", doors='" + doors + '\'' +
                ", windows='" + windows + '\'' +
                ", cupboards='" + cupboards + '\'' +
                ", wallPaint='" + wallPaint + '\'' +
                ", floor='" + floor + '\'' +
                ", kitchen='" + kitchen + '\'' +
                ", wiring='" + wiring + '\'' +
                ", toilet='" + toilet + '\'' +
                ", locks='" + locks + '\'' +
                ", electricalSwitches='" + electricalSwitches + '\'' +
                ", waterPipes='" + waterPipes + '\'' +
                ", sink='" + sink + '\'' +
                ", washBasin='" + washBasin + '\'' +
                ", propertyMaster=" + propertyMaster +
                '}';
    }
}
