package com.nttdata.transaction.domain.bean;

import lombok.Data;

@Data
public class Account {
    private String id;
    private AccountType accountType;            // Tipo de Cuenta: Ahorro, cuenta corriente, Plazo Fijo
    private String name;                        // Nombre de la cuenta (Ej. Cuenta Sueldo, etc. Opcional)
    private Double commission;                  // Comisión de la cuenta
    private int limitMovement;                  // Limite Máximo de movimiento
    private Double minimumDailyAverageAmount;   // Monto mínimo de promedio diario
}
