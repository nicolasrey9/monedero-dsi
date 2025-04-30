package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;


public class Cuenta {

  private double saldo = 0;
  private final Movimientos movimientos = new Movimientos();
  private double limite = 1000;

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void poner(double cuanto) {
    this.validarMontoPositivo(cuanto);
    this.validarCantidadDeDepositosDeHoy();

    this.crearMovimiento(cuanto, true);
  }

  public void sacar(double cuanto) {
    this.validarMontoPositivo(cuanto);
    this.validarMontoExtraible(cuanto);
    this.validarMaximoDeExtraccionDiario(cuanto);

    this.crearMovimiento(cuanto, false);
  }

  private void crearMovimiento(double monto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(LocalDate.now(), monto, esDeposito);
    movimiento.impactarSaldo(this);
    this.addMovimiento(movimiento);
  }

  private void validarMaximoDeExtraccionDiario(double monto) {
    var montoExtraidoHoy = movimientos.montoExtraidoA(LocalDate.now());
    var maximaExtraccionPosible = this.limite - montoExtraidoHoy;
    if (monto > maximaExtraccionPosible) {
      throw new MaximoExtraccionDiarioException(
          "No puede extraer mas de $ " + 1000 + " diarios, " + "límite: " + maximaExtraccionPosible);
    }
  }

  public void addMovimiento(Movimiento movimiento) {
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
