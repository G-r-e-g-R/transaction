package com.nttdata.affiliation.infraestructure.client;

/**
 * Clase que contiene los End Points de los servicios de Cliente y Producto.
 */
public class UriService {
    /**
     * Constructor.
     */
    protected  UriService() {
        super();
    }

    /**
     * URI base para obtener los servicios.
     */
    public static final String BASE_URI = "http://localhost:8092";

    /**
     * Servicios de Customer: Obtener un cliente.
     */
    public static final String CUSTOMER_GET_BY_ID = "/customers/{id}";

    /**
     * Servicios de Producto: Obtener un producto de cuenta bancaria.
     */
    public static final
    String PRODUCT_ACCOUNT_GET_BY_ID = "/products/account/{id}";

    /**
     * Servicios de Producto: Obtener un producto de credito.
     */
    public static final
    String PRODUCT_CREDIT_GET_BY_ID = "/products/credit/{id}";
    /**
     * Servicio de Afiliación: Obtiene la afiliación de cuenta bancaria.
     */
    public static  final
    String AFFILIATION_ACCOUNT_GET_BY_ID = "/affiliations/accounts/{id}";
    /**
     * Servicio de Afiliación: Obtiene la afiliación de credito.
     */
    public static final
    String AFFILIATION_CREDIT_GET_BY_ID = "/affiliations/credits/{id}";
    /**
     * Servicio de Afiliacion: Actualiza la afiliación de cuenta bancaria.
     */
    public static final
    String AFFILIATION_ACCOUNT_PUT = "/affiliations/accounts/{id}";
    /**
     * Servicio de Afiliacion: Actualiza la afiliación de credito.
     */
    public static final
    String AFFILIATION_CREDIT_PUT = "/affiliations/credits/{id}";
}
