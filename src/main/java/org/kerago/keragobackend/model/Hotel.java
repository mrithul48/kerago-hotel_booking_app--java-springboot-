package org.kerago.keragobackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private String description;


    @OneToMany(mappedBy = "hotel",orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<Rooms> rooms = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "hotel",orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<Booking> bookingList = new HashSet<>();

    @OneToMany(mappedBy = "hotel",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Images> imagesList = new ArrayList<>();

    @OneToMany(mappedBy = "hotel",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();
}
