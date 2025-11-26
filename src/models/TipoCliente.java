package models;

public enum TipoCliente {
    VIP(1),
    ADULTO_MAYOR(2),
    REGULAR(3);

    private final int prioridad;

    TipoCliente(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getPrioridad() {
        return prioridad;
    }
}