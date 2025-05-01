package dds.monedero.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovimientosTest {

  private Movimientos movimientos;
  private LocalDate hoy;
  private LocalDate ayer;

  @BeforeEach
  void setUp() {
    movimientos = new Movimientos();
    hoy = LocalDate.now();
    ayer = hoy.minusDays(1);
  }

  @Test
  void testAgregarMovimiento() {
    Movimiento deposito = new Movimiento(hoy, 100.0, TipoMovimiento.DEPOSITO);
    movimientos.add(deposito);
    assertEquals(1, movimientos.cantidad());
  }

  @Test
  void testCantidadDeDepositosDeUnaFecha() {
    movimientos.add(new Movimiento(hoy, 200.0, TipoMovimiento.DEPOSITO));
    movimientos.add(new Movimiento(hoy, 300.0, TipoMovimiento.DEPOSITO));
    movimientos.add(new Movimiento(ayer, 150.0, TipoMovimiento.DEPOSITO));
    movimientos.add(new Movimiento(hoy, 100.0, TipoMovimiento.EXTRACCION));

    assertEquals(2, movimientos.cantidadDeDepositosDe(hoy));
    assertEquals(1, movimientos.cantidadDeDepositosDe(ayer));
  }

  @Test
  void testMontoExtraidoEnFecha() {
    movimientos.add(new Movimiento(hoy, 100.0, TipoMovimiento.EXTRACCION));
    movimientos.add(new Movimiento(hoy, 200.0, TipoMovimiento.EXTRACCION));
    movimientos.add(new Movimiento(ayer, 50.0, TipoMovimiento.EXTRACCION));
    movimientos.add(new Movimiento(hoy, 300.0, TipoMovimiento.DEPOSITO));

    assertEquals(300.0, movimientos.montoExtraidoA(hoy));
    assertEquals(50.0, movimientos.montoExtraidoA(ayer));
  }

  @Test
  void testMontoExtraidoEnFechaSinResultados() {
    movimientos.add(new Movimiento(hoy, 100.0, TipoMovimiento.DEPOSITO));

    assertEquals(0.0, movimientos.montoExtraidoA(ayer));
  }
}
