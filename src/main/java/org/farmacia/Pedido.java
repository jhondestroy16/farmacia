package org.farmacia;

public class Pedido {
    private final String nombreMedicamento;
    private final String tipoMedicamento;
    private final int cantidad;
    private final String distribuidor;
    private final boolean principal;
    private final boolean secundaria;

    public Pedido(String nombreMedicamento, String tipoMedicamento, int cantidad, String distribuidor, boolean principal, boolean secundaria) {
        this.nombreMedicamento = nombreMedicamento;
        this.tipoMedicamento = tipoMedicamento;
        this.cantidad = cantidad;
        this.distribuidor = distribuidor;
        this.principal = principal;
        this.secundaria = secundaria;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public String getTipoMedicamento() {
        return tipoMedicamento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public boolean isSecundaria() {
        return secundaria;
    }

    public String getDistribuidor() {
        return distribuidor;
    }
}
