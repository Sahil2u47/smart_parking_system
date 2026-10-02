package com.sahil.smart_parking_project.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sahil.smart_parking_project.entity.ParkingRate;
import com.sahil.smart_parking_project.enums.VehicleType;
import com.sahil.smart_parking_project.repository.ParkingRateRepository;

@Configuration
public class parkingRateDataInitializer {

	@Bean
	CommandLineRunner initializeParkingRates(ParkingRateRepository parkingRateRepository) {

		return args -> {

			if (parkingRateRepository.count() == 0) {

				ParkingRate bike = new ParkingRate();
				bike.setVehicleType(VehicleType.BIKE);
				bike.setRatePerHour(20.0);
				bike.setActive(true);

				ParkingRate car = new ParkingRate();
				car.setVehicleType(VehicleType.CAR);
				car.setRatePerHour(50.0);
				car.setActive(true);

				ParkingRate auto = new ParkingRate();
				auto.setVehicleType(VehicleType.AUTO);
				auto.setRatePerHour(30.0);
				auto.setActive(true);

				parkingRateRepository.save(bike);
				parkingRateRepository.save(car);
				parkingRateRepository.save(auto);
			}
		};
	}

}
