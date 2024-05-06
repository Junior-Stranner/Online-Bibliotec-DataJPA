package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.entity.Zipcode;
import br.com.judev.bibliotec.repository.ZipcodeRepository;
import br.com.judev.bibliotec.service.ZipcodeService;
import lombok.Data;

@Service
@Data
public class ZipcodeServiceImpl implements ZipcodeService{

    private final ZipcodeRepository zipcodeRepository;
  //  private final CityService cityService;

    @Override
    public Zipcode addZipcode(Zipcode zipcode) {
       // TODO Auto-generated method stub
       throw new UnsupportedOperationException("Unimplemented method 'getZipcodes'");
    }

    @Override
    public List<Zipcode> getZipcodes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getZipcodes'");
    }

    @Override
    public Zipcode getZipcode(Zipcode zipcode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getZipcode'");
    }

    @Override
    public Zipcode deleteZipcode(Long zipcodeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteZipcode'");
    }

    @Override
    public Zipcode editZipcode(Long zipcodeId, Zipcode zipcode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editZipcode'");
    }

    @Override
    public Zipcode addCityToZipcode(Long zipcodeId, Long cityId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCityToZipcode'");
    }

    @Override
    public Zipcode removeCityFromZipcode(Long zipcodeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeCityFromZipcode'");
    }
    
}
