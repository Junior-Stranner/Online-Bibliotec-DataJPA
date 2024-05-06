package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.requestDto.ZipcodeRequestDto;
import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.entity.Zipcode;
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

   @Transactional  
   public Zipcode addZipcode(ZipcodeRequestDto zipcodeRequestDto) {

    if (zipcodeRequestDto == null || zipcodeRequestDto.getName() == null || zipcodeRequestDto.getName().isBlank()) {
        throw new IllegalArgumentException("Zipcode name cannot be null or blank.");
    }

      // Verificar duplicidade
      Optional<Zipcode> existingZipcode = zipcodeRepository.findByName(zipcodeRequestDto.getName());
      if (existingZipcode.isPresent()) {
        throw new DuplicateKeyException("Zipcode already exists!");
      }

    // Criar o novo Zipcode
     Zipcode newZipcode = new Zipcode();
    newZipcode.setName(zipcodeRequestDto.getName());

    // Salvar no repositório
    return zipcodeRepository.save(newZipcode);
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
    public Zipcode editZipcode(Long zipcodeId, Zipcode zipcode) {
        Zipcode zipToEdit = zipcodeRepository.findById(zipcodeId)
            .orElseThrow(() -> new EntityNotFoundException("zipcode with ID: " + zipcodeId + " could not be found"));

        if (zipcode.getName() == null || zipcode.getName().isBlank()) {
            throw new IllegalArgumentException("zipcode name cannot be null or blank!");
        }

        zipToEdit.setName(zipcode.getName());  
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
