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
  private int maximaCantidadDepositos = 3;

  public Cuenta() { }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void poner(double cuanto) {
    this.validarMontoPositivo(cuanto);
    this.validarCantidadDeDepositosDeHoy();

    this.crearMovimiento(cuanto, TipoMovimiento.DEPOSITO);
  }

  public void sacar(double cuanto) {
    this.validarMontoPositivo(cuanto);
    this.validarMontoExtraible(cuanto);
    this.validarMaximoDeExtraccionDiario(cuanto);

    this.crearMovimiento(cuanto, TipoMovimiento.EXTRACCION);
  }

  private void crearMovimiento(double monto, TipoMovimiento tipoMovimiento) {
    Movimiento movimiento = new Movimiento(LocalDate.now(), monto, tipoMovimiento);
    movimiento.impactarSaldo(this);
    this.addMovimiento(movimiento);
  }

  private void validarMaximoDeExtraccionDiario(double monto) {
    var montoExtraidoHoy = movimientos.montoExtraidoA(LocalDate.now());
    var maximaExtraccionPosible = this.limite - montoExtraidoHoy;
    if (monto > maximaExtraccionPosible) {
      throw new MaximoExtraccionDiarioException(
          "No puede extraer mas de $ " + this.limite + " diarios, " + "límite: " + maximaExtraccionPosible);
    }
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
    if (this.movimientos.cantidadDeDepositosDe(LocalDate.now()) >= this.maximaCantidadDepositos) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + this.maximaCantidadDepositos + " depositos diarios");
    }
  }


  public void addMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
  }


  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }


  public double getMontoExtraidoA(LocalDate fecha) {
    return this.movimientos.montoExtraidoA(fecha);
  }
}
