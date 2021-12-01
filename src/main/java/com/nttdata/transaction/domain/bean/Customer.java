package com.nttdata.transaction.domain.bean;

import lombok.Data;

@Data
public class Customer {
    private String code;
    private CustomerType customerType;      // Tipo de cliente
    private DocumentType documentType;      // Tipo de documento
    private String documentNumber;          // Numero de documento
    private String name;                    // Nombre del cliente
    private String businessName;            // Razon social del cliente
    private Status state;                   // Estado: Activo, Inactivo
}
