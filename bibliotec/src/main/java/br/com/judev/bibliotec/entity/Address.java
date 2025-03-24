package br.com.judev.bibliotec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "addresses")
@Entity
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código postal (CEP)
    private String postalCode;

    // Endereço (logradouro)
    private String street;

    // Informação adicional do endereço (ex.: número do apartamento)
    private String complement;

    // Bairro ou distrito
    private String neighborhood;

    // Nome da cidade
    private String city;

    // Abreviação do estado ou província
    private String state;

    // Região geográfica
    private String region;

    // Província ou divisão administrativa
    private String province;

}
