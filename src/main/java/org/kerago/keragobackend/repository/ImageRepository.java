package org.kerago.keragobackend.repository;

import org.kerago.keragobackend.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Images,Long> {
}
