package br.com.judev.bibliotec.service.serviceImpl;

import br.com.judev.bibliotec.dtos.AddressDTO;
import br.com.judev.bibliotec.dtos.mapper.AddressMapper;
import br.com.judev.bibliotec.entity.Address;
import br.com.judev.bibliotec.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public AddressDTO getAddressByPostalCode(String postalCode) {
        String url = "https://viacep.com.br/ws/" + postalCode + "/json/";
        try {
            Map<String, String> response = restTemplate.getForObject(url, Map.class);
            if (response == null || response.containsKey("erro")) {
                throw new IllegalArgumentException("CEP inválido ou não encontrado.");
            }

            Address address = new Address();
            address.setPostalCode(postalCode);
            address.setStreet(response.get("logradouro"));
            address.setNeighborhood(response.get("bairro"));
            address.setCity(response.get("localidade"));
            address.setState(response.get("uf"));
            address.setComplement(response.get("complemento"));
            address.setRegion(response.get(("Região")));
            address.setProvince(response.get("Província"));
            return AddressMapper.toDTO(address);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereço pelo CEP.", e);
        }
    }
}
