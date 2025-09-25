package org.kerago.keragobackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
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

    private BigDecimal rentPerNight;

    private String description;

    private Integer roomAvailability;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "hotel",orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<Booking> bookingList = new HashSet<>();

    @OneToMany(mappedBy = "hotel",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Images> imagesList = new ArrayList<>();

    @OneToMany(mappedBy = "hotel",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();
}
