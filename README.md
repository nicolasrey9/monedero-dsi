## Monedero

### Contexto

Este repositorio contiene el código de un _monedero virtual_, al que podemos agregarle y quitarle dinero, a través 
de los métodos `Monedero.sacar` y `Monedero.poner`, respectivamente. 
Pero hay algunos problemas: por un lado el código no está muy bien testeado, y por el otro, hay numeros _code smells_. 

### Consigna

Tenés seis tareas: 

 1. :fork_and_knife: Hacé un repositorio usando este template (presionando desde Github el botón _use this template_)
 2. :arrow_down: Descargalo y construí el proyecto, utilizando `maven`
 2. :nose: Identificá y anotá todos los _code smells_ que encuentres 
 3. :test_tube: Agregá los tests faltantes y mejorá los existentes. 
     * :eyes: Ojo: ¡un test sin ningún tipo de aserción está incompleto!
 4. :rescue_worker_helmet: Corregí smells, de a un commit por vez. 
 5. :arrow_up: Subí todos los cambios a tu _fork_
 6. :bug: Activá los issues de Github desde https://github.com/TU_GITHUB_USERNAME/dds-monedero-java8/settings así podemos darte nuestras devoluciones

### Tecnologías usadas

* Java 17.
* JUnit 5. :warning: La versión 5 de JUnit es la más nueva del framework y presenta algunas diferencias respecto a la versión "clásica" (JUnit 4). Para mayores detalles, ver:
    *  [Apunte de herramientas](https://docs.google.com/document/d/1VYBey56M0UU6C0689hAClAvF9ILE6E7nKIuOqrRJnWQ/edit#heading=h.dnwhvummp994)
    *  [Entrada de Blog (en inglés)](https://www.baeldung.com/junit-5-migration)
    *  [Entrada de Blog (en español)](https://www.paradigmadigital.com/dev/nos-espera-junit-5/)
* Maven 3.3 o superior

### Code Smells Detectados
* Type Test con `esDeposito en movimiento`, aunque no me parece tan grave porque no es muy diferencial. Pero se podria solucionar con polimorfismo.
* Codigo duplicado con las validaciones de `MontoNegativoException` y `SaldoMenorException`
* `movimiento.getFecha().equals(fecha)` esto deberia ser responsabilidad del movimiento, en `getMontoExtraidoA`
* Al tener mucha logica sobre la `lista de movimiento``, esta se podria delegar en una Clase que posea esta logica.
* Los valores de `limite` de `MaximoExtraccionDiarioException` y de `MaximaCantidadDepositos` deberian ser parametrizados por si cambian
* Algunos de los tests no tienen asserts
* Los mensajes de `agregateA` y `calcularValor` del movimiento siento que son responsabilidad de la Cuenta. Pero capaz estoy errado.
* Se usa un booleano para representar si un movimiento esDeposito, lo cual puede traer problemas si surge un nuevo tipo de movimiento
 

  


