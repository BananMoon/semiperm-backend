package com.project.semipermbackend.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {


    Optional<Store> getStoreByEncodedPlaceId(String encodedPlaceId);
}
