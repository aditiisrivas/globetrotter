package com.example.globetrotter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.globetrotter.Model.Destination;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends MongoRepository<Destination, String> {
}
