package dds.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Movimientos {
  private List<Movimiento> movimientos = new ArrayList<>();

  public void add(Movimiento movimiento) {
    this.movimientos.add(movimiento);
  }

  public int cantidad() {
    return this.movimientos.size();
  }

  public int cantidadDeMovimientosDe(LocalDate date) {
    return (int) movimientos.stream().filter(movimiento -> movimiento.fueDepositado(date)).count();
  }

  public double montoExtraidoA(LocalDate fecha) {
    return this.movimientos.stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.esDeLaFecha(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }
}
