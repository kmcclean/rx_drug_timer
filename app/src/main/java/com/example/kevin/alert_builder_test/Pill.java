package com.example.kevin.alert_builder_test;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

//the pill object is the specific object that is used to track individual drugs to and fro throughout the system.
public class Pill implements Parcelable{

    private static final String NAME = "Name";
    private static final String PHARM_NAME = "Pharmacy name";
    private static final String PHARM_NO = "pharmacy number";
    private static final String DR_NAME = "doctor name";
    private static final String DR_NO = "dr number";
    private static final String NEXT_TIME_IN_MILLIS = "next pill time";
    private static final String I_LEN = "interval len";
    private static final String PILL_CT = "pill count";
    private static final String INFO = "information";
    private static final String P_ID = "pill id";

    protected String pillName;
    protected String pharmacyName;
    protected Long pharmacyNo;
    protected String doctorName;
    protected Long doctorNo;
    protected Long nextTimeInMillis;
    protected int intervalLength;
    protected int pillCount;
    protected String information;
    protected Long pillID;


    public Pill (Long id, String pillName, String pharmacyName, Long pharmacyNo, String doctorName, Long doctorNo, long pillInMillis, int pillCountRemaining, int intervalLength, String information){
        this.pillName = pillName;
        this.pharmacyName = pharmacyName;
        this.pharmacyNo = pharmacyNo;
        this.doctorName = doctorName;
        this.doctorNo = doctorNo;
        this.nextTimeInMillis = pillInMillis;
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

    public Long getNextTimeInMillis() {
        return nextTimeInMillis;
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

    public void setNextTimeInMillis(long nextTimeInMillis) {
        this.nextTimeInMillis = nextTimeInMillis;
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

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        Bundle dest = new Bundle();
        dest.putString(NAME, pillName);
        dest.putString(PHARM_NAME, pharmacyName);
        dest.putLong(PHARM_NO, pharmacyNo);
        dest.putString(DR_NAME, doctorName);
        dest.putLong(DR_NO, doctorNo);
        dest.putLong(NEXT_TIME_IN_MILLIS, nextTimeInMillis);
        dest.putInt(I_LEN, intervalLength);
        dest.putInt(PILL_CT, pillCount);
        dest.putString(INFO, information);
        dest.putLong(P_ID, pillID);

        out.writeBundle(dest);
    }


    public static final Parcelable.Creator<Pill> CREATOR = new Creator<Pill>() {
        @Override
        public Pill createFromParcel(Parcel source) {
            Bundle in = source.readBundle();

            //Create and return a new Pill
            return new Pill(in.getLong(P_ID),
                    in.getString(NAME),
                    in.getString(PHARM_NAME),
                    in.getLong(PHARM_NO),
                    in.getString(DR_NAME),
                    in.getLong(DR_NO),
                    in.getLong(NEXT_TIME_IN_MILLIS),
                    in.getInt(I_LEN),
                    in.getInt(PILL_CT),
                    in.getString(INFO));
        }
        @Override
        public Pill[] newArray(int size) {
            return new Pill[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
