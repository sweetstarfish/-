package code6.entity;

import java.util.Date;

public class Rent {
    Integer rid;
    String btime;
    String days;
    Integer uid;
    Integer bid;
    public Rent() {
    }

    public Rent(Integer rid, String btime, String days, Integer uid, Integer bid) {
        this.rid = rid;
        this.btime = btime;
        this.days = days;
        this.uid = uid;
        this.bid = bid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "rid=" + rid +
                ", btime='" + btime + '\'' +
                ", days='" + days + '\'' +
                ", uid=" + uid +
                ", bid=" + bid +
                '}';
    }
}
