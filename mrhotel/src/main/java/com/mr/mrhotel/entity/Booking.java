package com.mr.mrhotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "check_in date is required")
    private LocalDate checkInDate;
    @Future(message = "check_out date is required")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "adults must not be less than 1")
    private int numOfAdults;
    private int numOfChild;
    private int numOfGuest;

    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;


    public void totalNumberOfGuestCalculate() {
        this.numOfGuest = this.numOfAdults + this.numOfChild;
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        totalNumberOfGuestCalculate();
    }

    public void setNumOfChild(int numOfChild) {
        this.numOfChild = numOfChild;
        totalNumberOfGuestCalculate();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChild=" + numOfChild +
                ", numOfGuest=" + numOfGuest +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                '}';
    }
}
