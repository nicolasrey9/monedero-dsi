package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  private final double monto;
  private TipoMovimiento tipoMovimiento;

  public Movimiento(LocalDate fecha, double monto, TipoMovimiento tipoMovimiento) {
    this.fecha = fecha;
    this.monto = monto;
    this.tipoMovimiento = tipoMovimiento;
  }

  public double getMonto() {
    return monto;
  }

  public boolean fueDepositado(LocalDate fecha) {
    return this.isDeposito() && esDeLaFecha(fecha);
  }

  public boolean fueExtraido(LocalDate fecha) {
    return this.isExtraccion() && esDeLaFecha(fecha);
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public boolean isDeposito() {
    return this.tipoMovimiento.equals(TipoMovimiento.DEPOSITO);
  }

  public boolean isExtraccion() {
    return this.tipoMovimiento.equals(TipoMovimiento.EXTRACCION);
  }


  public void impactarSaldo(Cuenta cuenta) {
    cuenta.setSaldo(tipoMovimiento.calcularNuevoSaldo(cuenta, this.getMonto()));
  }

}
