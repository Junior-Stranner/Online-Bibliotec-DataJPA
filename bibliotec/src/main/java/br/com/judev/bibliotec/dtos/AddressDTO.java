package br.com.judev.bibliotec.dtos;

public record AddressDTO( // Código postal (CEP)
                          String postalCode,
                          // Endereço (logradouro)
                          String street,
                          // Informação adicional do endereço (ex.: número do apartamento)
                          String complement,
                          // Bairro ou distrito
                          String neighborhood,
                          // Nome da cidade
                          String city,
                          // Abreviação do estado ou província
                          String state,
                          // Região geográfica
                          String region,
                          // Província ou divisão administrativa
                          String province) {
}
