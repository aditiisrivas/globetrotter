package com.example.globetrotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.globetrotter.Model.Destination;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DestinationRepository  extends JpaRepository<Destination, UUID> {

}
