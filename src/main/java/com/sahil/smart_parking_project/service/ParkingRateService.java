package com.sahil.smart_parking_project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.entity.ParkingRate;
import com.sahil.smart_parking_project.enums.VehicleType;
import com.sahil.smart_parking_project.globalException.ResourceNotFoundException;
import com.sahil.smart_parking_project.repository.ParkingRateRepository;

@Service
public class ParkingRateService {

    private final ParkingRateRepository parkingRateRepository;

    public ParkingRateService(ParkingRateRepository parkingRateRepository) {
        this.parkingRateRepository = parkingRateRepository;
    }

    public double getRatePerHour(VehicleType vehicleType) {

        ParkingRate parkingRate = parkingRateRepository
                .findByVehicleTypeAndActiveTrue(vehicleType)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Active parking rate not found for " + vehicleType
                        ));

        return parkingRate.getRatePerHour();
    }
    
    public List<ParkingRate> getAllRates() {
        return parkingRateRepository.findAll();
    }
    
    public ParkingRate updateRate(VehicleType vehicleType, double rate) {

        ParkingRate parkingRate = parkingRateRepository
                .findByVehicleTypeAndActiveTrue(vehicleType)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Parking rate not found for " + vehicleType));

        parkingRate.setRatePerHour(rate);

        return parkingRateRepository.save(parkingRate);
    }
    
}
