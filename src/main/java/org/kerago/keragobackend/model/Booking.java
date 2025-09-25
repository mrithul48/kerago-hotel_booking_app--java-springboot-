package org.kerago.keragobackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.kerago.keragobackend.enums.Status;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id",nullable = false)
    private Hotel hotel;


    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime checkIn;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime checkOut;

    @Max(value = 2, message = "Guests cannot be more than 2")
    @Column(nullable = true)
    private Integer guests;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;


}
