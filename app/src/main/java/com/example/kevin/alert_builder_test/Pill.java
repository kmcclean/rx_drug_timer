package com.example.kevin.alert_builder_test;

/**
 * Created by Kevin on 12/8/2015.
 */
public class Pill {

    protected String pillName;
    protected String pharmacyName;
    protected Long pharmacyNo;
    protected String doctorName;
    protected Long doctorNo;
    protected int nextPillHour;
    protected int nextPillMinute;
    protected int intervalLength;
    protected int pillCount;
    protected String information;
    protected Long pillID;

    public Pill (Long id, String pillName, String pharmacyName, Long pharmacyNo, String doctorName, Long doctorNo, int nextPillHour, int nextPillMinute, int intervalLength, int pillCountRemaining, String information){
        this.pillName = pillName;
        this.pharmacyName = pharmacyName;
        this.pharmacyNo = pharmacyNo;
        this.doctorName = doctorName;
        this.doctorNo = doctorNo;
        this.nextPillHour = nextPillHour;
        this.nextPillMinute = nextPillMinute;
        this.intervalLength = intervalLength;
        this.pillCount = pillCountRemaining;
        this.information = information;
        this.pillID = id;
    }

    public int getIntervalLength() {
        return intervalLength;
    }

    public int getPillCount() {
        return pillCount;
    }

    public Long getPillID() {
        return pillID;
    }

    public String getPillName() {
        return pillName;
    }

    public int getNextPillHour() {
        return nextPillHour;
    }

    public String getInformation() {
        return information;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public Long getPharmacyNo() {
        return pharmacyNo;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Long getDoctorNo() {
        return doctorNo;
    }

    public int getNextPillMinute() {
        return nextPillMinute;
    }

    public void setDoctorName(String doctorName) {

        this.doctorName = doctorName;
    }

    public void setDoctorNo(Long doctorNo) {
        this.doctorNo = doctorNo;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setIntervalLength(int intervalLength) {
        this.intervalLength = intervalLength;
    }

    public void setNextPillHour(int nextPillHour) {
        this.nextPillHour = nextPillHour;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public void setPharmacyNo(Long pharmacyNo) {
        this.pharmacyNo = pharmacyNo;
    }

    public void setPillCount(int pillCount) {
        this.pillCount = pillCount;
    }

    public void setPillID(Long pillID) {
        this.pillID = pillID;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public void setNextPillMinute(int nextPillMinute) {
        this.nextPillMinute = nextPillMinute;
    }
}
