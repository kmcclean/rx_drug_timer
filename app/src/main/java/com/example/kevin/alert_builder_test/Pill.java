package com.example.kevin.alert_builder_test;

/**
 * Created by Kevin on 12/8/2015.
 */
public class Pill {

    protected static String pillName;
    protected static String pharmacyName;
    protected static Long pharmacyNo;
    protected static String doctorName;
    protected static Long doctorNo;
    protected static String nextPillTime;
    protected static int intervalLength;
    protected static int pillCount;
    protected static String information;
    protected static Long pillID;

    public Pill (Long id, String pillName, String pharmacyName, Long pharmacyNo, String doctorName, Long doctorNo, String nextPillTime, int intervalLength, int pillCountRemaining, String information){
        this.pillName = pillName;
        this.pharmacyName = pharmacyName;
        this.pharmacyNo = pharmacyNo;
        this.doctorName = doctorName;
        this.doctorNo = doctorNo;
        this.nextPillTime = nextPillTime;
        this.intervalLength = intervalLength;
        this.pillCount = pillCountRemaining;
        this.information = information;
        this.pillID = id;
    }

    public static int getIntervalLength() {
        return intervalLength;
    }

    public static int getPillCount() {
        return pillCount;
    }

    public static Long getPillID() {
        return pillID;
    }

    public static String getPillName() {
        return pillName;
    }

    public static String getNextPillTime() {
        return nextPillTime;
    }

    public static String getInformation() {
        return information;
    }

    public static String getPharmacyName() {
        return pharmacyName;
    }

    public static Long getPharmacyNo() {
        return pharmacyNo;
    }

    public static String getDoctorName() {
        return doctorName;
    }

    public static Long getDoctorNo() {
        return doctorNo;
    }

    public static void setDoctorName(String doctorName) {
        Pill.doctorName = doctorName;
    }

    public static void setDoctorNo(Long doctorNo) {
        Pill.doctorNo = doctorNo;
    }

    public static void setInformation(String information) {
        Pill.information = information;
    }

    public static void setIntervalLength(int intervalLength) {
        Pill.intervalLength = intervalLength;
    }

    public static void setNextPillTime(String nextPillTime) {
        Pill.nextPillTime = nextPillTime;
    }

    public static void setPharmacyName(String pharmacyName) {
        Pill.pharmacyName = pharmacyName;
    }

    public static void setPharmacyNo(Long pharmacyNo) {
        Pill.pharmacyNo = pharmacyNo;
    }

    public static void setPillCount(int pillCount) {
        Pill.pillCount = pillCount;
    }

    public static void setPillID(Long pillID) {
        Pill.pillID = pillID;
    }

    public static void setPillName(String pillName) {
        Pill.pillName = pillName;
    }
}
