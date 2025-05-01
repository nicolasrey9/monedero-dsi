package dds.monedero.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoTest {

  private Movimiento depositoHoy;
  private Movimiento extraccionAyer;
  private Cuenta cuenta;
  private LocalDate hoy;
  private LocalDate ayer;

  @BeforeEach
  void setUp() {
    hoy = LocalDate.now();
    ayer = hoy.minusDays(1);

    depositoHoy = new Movimiento(hoy, 500.0, TipoMovimiento.DEPOSITO);
    extraccionAyer = new Movimiento(ayer, 200.0, TipoMovimiento.EXTRACCION);

    cuenta = new Cuenta();  // Asegurate que tenga getSaldo y setSaldo
    cuenta.setSaldo(1000.0);
  }

  @Test
  void testFueDepositadoVerdadero() {
    assertTrue(depositoHoy.fueDepositado(hoy));
  }

  @Test
  void testFueDepositadoFalsoPorFecha() {
    assertFalse(depositoHoy.fueDepositado(ayer));
  }

  @Test
  void testFueDepositadoFalsoPorTipo() {
    assertFalse(extraccionAyer.fueDepositado(ayer));
  }

  @Test
  void testFueExtraidoVerdadero() {
    assertTrue(extraccionAyer.fueExtraido(ayer));
  }

  @Test
  void testFueExtraidoFalsoPorFecha() {
    assertFalse(extraccionAyer.fueExtraido(hoy));
  }

  @Test
  void testEsDeLaFechaVerdadero() {
    assertTrue(depositoHoy.esDeLaFecha(hoy));
  }

  @Test
  void testEsDeLaFechaFalso() {
    assertFalse(depositoHoy.esDeLaFecha(ayer));
  }

  @Test
  void testImpactarSaldoConDeposito() {
    depositoHoy.impactarSaldo(cuenta);
    assertEquals(1500.0, cuenta.getSaldo());
  }

  @Test
  void testImpactarSaldoConExtraccion() {
    extraccionAyer.impactarSaldo(cuenta);
    assertEquals(800.0, cuenta.getSaldo());
  }
}
