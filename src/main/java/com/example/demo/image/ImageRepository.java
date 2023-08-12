package com.example.demo.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    public Optional<Image> findByName(String imageName);

    @Query(value = "SELECT *  FROM _image WHERE _image.status=0 ORDER BY random() LIMIT 2", nativeQuery = true)
    public Optional<Image> findRandomAwaitingImage();

    public List<Image> findAllByStatus(Status status);
}
