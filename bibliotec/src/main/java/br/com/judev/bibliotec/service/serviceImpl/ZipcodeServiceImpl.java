package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.requestDto.ZipcodeRequestDto;
import br.com.judev.bibliotec.entity.Author;
import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.entity.Zipcode;
import br.com.judev.bibliotec.repository.CityRepository;
import br.com.judev.bibliotec.repository.ZipcodeRepository;
import br.com.judev.bibliotec.service.CityService;
import br.com.judev.bibliotec.service.ZipcodeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;

@Service
@Data
public class ZipcodeServiceImpl implements ZipcodeService{

    private final ZipcodeRepository zipcodeRepository;
    private final CityService cityService;
    private final CityRepository cityRepository;

    @Transactional  
    public Zipcode addZipcode(ZipcodeRequestDto zipcodeRequestDto) {
        // Validação do nome do Zipcode
        if (zipcodeRequestDto == null || zipcodeRequestDto.getName() == null || zipcodeRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("Zipcode name cannot be null or blank.");
        }
    
        // Verificação de duplicidade pelo nome do Zipcode
        Optional<Zipcode> existingZipcode = zipcodeRepository.findByName(zipcodeRequestDto.getName());
        if (existingZipcode.isPresent()) {
            throw new DuplicateKeyException("Zipcode with this name already exists!");
        }
    
        // Validação e associação da cidade
        if (zipcodeRequestDto.getCityId() == null) {
            throw new IllegalArgumentException("City ID must be provided.");
        }
    
        Optional<City> cityOptional = cityRepository.findById(zipcodeRequestDto.getCityId());
        if (!cityOptional.isPresent()) {
            throw new IllegalArgumentException("City not found with ID: " + zipcodeRequestDto.getCityId());
        }
    
        City city = cityOptional.get();
        // Verificação se outro Zipcode já usa a mesma cidade
        Optional<Zipcode> zipcodeWithSameCity = zipcodeRepository.findByCityId(zipcodeRequestDto.getCityId());
        if (zipcodeWithSameCity.isPresent()) {
            throw new IllegalArgumentException("This City ID is already associated with another Zipcode!");
        }
    
        // Instanciar o Zipcode após validação
        Zipcode newZipcode = new Zipcode();
        newZipcode.setName(zipcodeRequestDto.getName());
        newZipcode.setCity(city);
    
        // Salvar no repositório com tratamento de exceções
        return zipcodeRepository.save(newZipcode); // Salvar após validação e instância
    }
    
    
    @Override
    public List<Zipcode> getZipcodes() {
        return zipcodeRepository.findAll();
        
    }

    @Override
    public Zipcode getZipcode(Long zipcodeId) {
        return zipcodeRepository.findById(zipcodeId)
        .orElseThrow(() -> new EntityNotFoundException("zipcode with zipcodeId: " + zipcodeId + " could not be found"));
    }

    @Override
    public Zipcode deleteZipcode(Long zipcodeId) {
        Zipcode zipcode = getZipcode(zipcodeId);
        
        zipcodeRepository.delete(zipcode);
        return zipcode;
    }

    @Override
    public Zipcode editZipcode(Long id, ZipcodeRequestDto zipcodeRequestDto) {
        Zipcode zipToEdit = zipcodeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("zipcode with ID: " + id + " could not be found"));

        if (zipcodeRequestDto.getName() == null || zipcodeRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("zipcode name cannot be null or blank!");
        }

       Optional<Zipcode> existingZipcode = zipcodeRepository.findByName(zipcodeRequestDto.getName());
       if (existingZipcode.isPresent() && !existingZipcode.get().getId().equals(id)) {
         throw new DuplicateKeyException("Another Zipcode with the same zipcode already exists!");
      }

        zipToEdit.setName(zipcodeRequestDto.getName());  
        return zipcodeRepository.save(zipToEdit); 
    }

    @Override
    public Zipcode addCityToZipcode(Long zipcodeId, Long cityId) {
        Zipcode zipcode = getZipcode(zipcodeId);
        if (zipcode == null) {
            throw new EntityNotFoundException("Zipcode with ID " + zipcodeId + " not found.");
        }
    
        City city = cityService.getCity(cityId);
        if (city == null) {
            throw new EntityNotFoundException("City with ID " + cityId + " not found.");
        }
    
        if (zipcode.getCity() != null) {
            throw new IllegalArgumentException("Zipcode already has a city.");
        }
    
        zipcode.setCity(city);
    
        return zipcodeRepository.save(zipcode);
    }
    
    @Override
    public Zipcode removeCityFromZipcode(Long zipcodeId) {
        Zipcode zipcode = getZipcode(zipcodeId);
        if (zipcode == null) {
            throw new EntityNotFoundException("Zipcode with ID " + zipcodeId + " not found.");
        }
    
        if (zipcode.getCity() == null) { 
            throw new IllegalArgumentException("Zipcode does not have a city to remove.");
        }

        zipcode.setCity(null);
    
        return zipcodeRepository.save(zipcode);
    }

}
