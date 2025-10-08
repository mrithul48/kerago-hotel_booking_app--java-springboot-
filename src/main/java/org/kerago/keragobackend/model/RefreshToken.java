package org.kerago.keragobackend.model;

import com.cloudinary.AccessControlRule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",unique = true)
    private Users users;

    @Column(nullable = false)
    private Instant expiryDate;

    public void setToken(AccessControlRule.AccessType accessType) {
    }
}
