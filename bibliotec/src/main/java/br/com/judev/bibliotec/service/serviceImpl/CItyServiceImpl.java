package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.repository.CityRepository;
import br.com.judev.bibliotec.service.CityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CItyServiceImpl implements CityService{

        private final CityRepository cityRepository;

    
        @Override
        public City addCity(City city) {
            // Verificar se a entrada e o nome da cidade são válidos
            if (city == null || city.getName() == null || city.getName().isBlank()) {
                System.out.println("City name is null or blank.");
                throw new IllegalArgumentException("City name cannot be null or blank.");
            }
        
            Optional<City> existingCity = cityRepository.findByName(city.getName());
             if (existingCity.isPresent()) {
                throw new DuplicateKeyException("City already exists!");
            }

            return cityRepository.save(city);
        }

    @Override
    public List<City> getCities() {
      return cityRepository.findAll();

    }

    @Override
    public City getCity(Long cityId) {
       return cityRepository.findById(cityId)
        .orElseThrow(() -> new EntityNotFoundException("city with cityId: " + cityId + " could not be found"));
    }

    @Override
    public City deleteCity(Long cityId) {
        City city = getCity(cityId);
        cityRepository.delete(city);
        return city;
    }

    @Override
    public City editCity(Long cityId, City city) {
        City cityToEdit = cityRepository.findById(cityId)
            .orElseThrow(() -> new EntityNotFoundException("City with ID: " + cityId + " could not be found"));

        if (city.getName() == null || city.getName().isBlank()) {
            throw new IllegalArgumentException("City name cannot be null or blank!");
        }

        
       Optional<City> existingCity = cityRepository.findByName(city.getName());
       if (existingCity.isPresent() && !existingCity.get().getId().equals(cityId)) {
         throw new DuplicateKeyException("Another Zipcode with the same zipcode already exists!");
      }

        cityToEdit.setName(city.getName());  
        return cityRepository.save(cityToEdit); 
    }
    
}
