package com.portal.app.payload.response;

import java.io.Serializable;

public class PropertyMaterial implements Serializable {

    private static final long serialVersionUID = 8019668722780696758L;

    private Long id;

    private Long pId;

    private String propId;

    private String doors;

    private String windows;

    private String cupboards;

    private String wallPaint;

    private String floor;

    private String kitchen;

    private String wiring;

    private String toilet;

    private String locks;

    private String electricalSwitches;

    private String waterPipes;

    private String sink;

    private String washBasin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
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

    @Override
    public String toString() {
        return "PropertyMaterial{" +
                "id=" + id +
                ", pId='" + pId + '\'' +
                ", propId='" + propId + '\'' +
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
                '}';
    }
}
