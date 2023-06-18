package com.eduproject.ge.pojos;

public class Booking {

    private String firstname;
    private String lastname;
    private String additionalneeds;
    private Boolean depositpaid;
    private int totalprice;

    public Booking() {

    }

    public Booking(String fname, String lname, String aneeds,
                   Boolean dpaid, int tprice, BookingDates bdates) {

        setFirstname(fname);
        setLastname(lname);
        setAdditionalneeds(aneeds);
        setDepositpaid(dpaid);
        setTotalprice(tprice);
        setBookingdates(bdates);
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    private BookingDates bookingdates;


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }

    public Boolean getDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(Boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }



}
