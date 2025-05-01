package dds.monedero.model;

public enum TipoMovimiento {
  DEPOSITO {
    @Override
    public double calcularNuevoSaldo(Cuenta cuenta, double monto) {
        return cuenta.getSaldo() + monto;
    }
  }, EXTRACCION {
    @Override
    public double calcularNuevoSaldo(Cuenta cuenta, double monto) {
        return cuenta.getSaldo() - monto;
    }
  };
  public abstract double calcularNuevoSaldo(Cuenta cuenta, double monto);

}
