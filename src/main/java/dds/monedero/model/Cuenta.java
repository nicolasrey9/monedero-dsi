package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private final Movimientos movimientos = new Movimientos();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void poner(double cuanto) {
    this.validarMontoPositivo(cuanto);
    this.validarCantidadDeDepositosDeHoy();

    new Movimiento(LocalDate.now(), cuanto, true).agregateA(this);
  }

  public void sacar(double cuanto) {
    this.validarMontoPositivo(cuanto);
    this.validarMontoExtraible(cuanto);

    var montoExtraidoHoy = movimientos.montoExtraidoA(LocalDate.now());
    var limite = 1000 - montoExtraidoHoy; // 1000 deberia ser parametrizado
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException(
          "No puede extraer mas de $ " + 1000 + " diarios, " + "límite: " + limite);
    }
    new Movimiento(LocalDate.now(), cuanto, false).agregateA(this);
  }

  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    var movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }

  private void validarMontoPositivo(double monto) {
    if (monto < 0) {
      throw new MontoNegativoException("No puede sacar una cantidad negativa");
    }
  }

  private void validarMontoExtraible(double monto) {
    if (monto > getSaldo()) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

  private void validarCantidadDeDepositosDeHoy() {
    if (this.movimientos.cantidadDeDepositosDe(LocalDate.now()) >= 3) {  // el 3 deberia estar parametrizado?
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }


  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  // metodo/s agregado para tests
  public int cantidadDeMovimientos() {
    return this.movimientos.cantidad();
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return this.movimientos.montoExtraidoA(fecha);
  }
}
