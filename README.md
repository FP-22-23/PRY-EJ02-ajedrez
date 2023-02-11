# Proyecto del Segundo Cuatrimestre Fundamentos de Programación (Curso 2021/22)
Autor/a: Toñi Reina   uvus:reinaqu

Este proyecto es una adaptación del proyecto presentado por el estudiante Pablo Diego Acosta, en el curso 2021/22.

## Estructura de las carpetas del proyecto

* **/src**: Directorio con el código fuente.
  * **fp.ajedrez**: Paquete que contiene los tipos del proyecto.
  * **fp.ajedrez.test**: Paquete que contiene las clases de test del proyecto.
  * **fp.utiles**:  Paquete que contiene las clases de utilidad. 
* **/data**: Contiene el dataset del proyecto.
    * **chess.csv**: Archivo csv que contiene datos de diferentes partidas de ajedrez.
    
## Estructura del *dataset*

El dataset original Chess Game Dataset se puede obtener de la URL [https://www.kaggle.com/datasnaek/chess](https://www.kaggle.com/datasnaek/chess). Originalmente tiene 16 columnas y cada fila contiene datos sobre una partida de ajedrez jugada en la plataforma lichess.com. El dataset usado en este proyecto tiene 11 columna, 9 se han tomado del dataset original, y dos, la fecha y la duración de las partidas, se han generado de forma aleatoria. A continuación se describen las 11 columnas del dataset:

* **rated**: de tipo booleano,  indica si las partidas han sido calificadas o no, es decir si
son partidas amistosas o de tipo clasificatorio.
* **victory_status**: de tipo cadena, indica el tipo de victoria. Puede tomar los valores: outoftime, resign, mate or draw.
* **winner**: de tipo cadena, indica el resultado de la partida. Puede tomar los valores: white, black o draw, para indicar si ganan las blancas, las negras, o hay tablas.
* **white_id**: de tipo cadena, contiene el identificador del jugador de blancas.
* **white_rating**: de tipo entero, tiene el valor ELO del jugador de blancas. El valor ELO es un número que se usa en lichess.com para hacer un ranking de los jugadores.
* **black_id**: de tipo cadena, contiene el identificador del jugador de negras.
* **black_rating**: de tipo entero, tiene el valor ELO del jugador de negras. 
* **moves**: de tipo cadena, contiene una secuencia con los movimientos de la partida. Los movimientos están separados por espacios en blanco. Por ejemplo, ```d4 d5 c4 c6 cxd5 e6 dxe6 fxe6 Nf3 Bb4+ Nc3 Ba5 Bf4```.
* **opening_name**: de tipo cadena, indica la salida y la defensa de la partida. Esto no se
corresponde con el primer o segundo movimiento, sino que son las salidas y sus distintas variantes, por ejemplo, ```Queen's Pawn Game: Mason Attack```.
* **fecha**: de tipo fecha. Indica la fecha en la que se jugó la partida.
* **duracion**: de tipo entero. Indica la duración en minutos de la partida.

## Tipos implementados

Los tipos que se han implementado en el proyecto son los siguientes:

### Tipo Base - Partida
Representa una partida de ajedrez concreta.
**Propiedades**:

- _clasificatoria_, de tipo _Boolean_, consultable. Indica si las partidas han sido calificadas o no, es decir si son partidas amistosas o de tipo clasificatorio. 
- _tipoVictoria_, de tipo _TipoVictoria_, consultable. Indica el tipo de victoria. Puede tomar los valores OUTOFTIME, RESIGN, MATE, DRAW.
- _resultado_, de tipo _Resultado_, consultable. Indica el resultado de la partida. Puede tomar los valores WHITE, BLACK, DRAW.
- _jugadorBlancas_, de tipo _String_, consultable. Contiene el identificador del jugador de blancas.
- _jugadorNegras_, de tipo _String_, consultable. Contiene el identificador del jugador de negras.
- _ratingBlancas_, de tipo _Integer_, consultable. Contiene el rating del jugador de blancas.
- _ratingNegras_, de tipo _Integer_, consultable. Contiene el rating del jugador de negras.
- _movimientos_, de tipo _List\<String\>_, consultable. Lista de movimientos de la partida.
- _apertura_, de tipo _String_, consultable. Tipo de apertura usado en la partida.
- _apertura_, de tipo _String_, consultable. Tipo de apertura usado en la partida.
- _movimientoApertura_, de tipo _String_, consultable. Es el primer movimiento de la partida y, por lo tanto, se obtiene a partir del primer elemento de la lista de movimientos.
- _numMovimientos_, de tipo _Integer_, consultable. Número de movimientos que se han realizado en la partida. Se calcula a partir del número de elementos de la lista de movimientos.
- _jugadorGanador_, de tipo _String_, consultable. Contiene el id del jugador que gana la partida, o ```null```, si la partida ha quedado en tablas.
- _ratingGanador_, de tipo _Integer_, consultable. Contiene el rating del jugador que gana la partida, o ```null```, si la partida ha quedado en tablas.
- _diferenciaRatings_, de tipo _Integer_, consultable. Contiene la diferencia de ratings entre los dos jugadores de la partida (en valor absoluto).


**Constructores**: 

- C1: Tiene un parámetro por cada propiedad básica del tipo.
- C2: Crea un objeto de tipo ```Partida``` a partir de los siguientes parámetros: ```Boolean clasificatoria, TipoVictoria tipoVictoria, Resultado resultado, String jugadorBlancas, String jugadorNegras, Integer ratingBlancas, Integer ratingNegras, String movimientos, String apertura, LocalDate fecha, Integer duracion```.

**Restricciones**:
 
- R1: La duración debe estar entre 1 y 60.
- R2: El movimiento inicial debe ser uno de los movimientos siguientes: h3, h4, g3, g4, f3, f4, e3, e4, d3, d4, c3, c4, b3, b4, a3, a4, Nh3, Nf3, Nc3, Na3.
- R3: El rating de las blancas debe ser mayor que cero.
- R4: El rating de las negras debe ser mayor que cero.
***Criterio de igualdad**: Dos partidas son iguales si todas sus propiedades básicas son iguales.

**Criterio de ordenación**: Por fecha, duración y número de movimientos.

**Otras operaciones**:

- _String getMovimiento(Integer numMovimiento)_: Devuelve el movimiento dado por el número numMovimiento. Eleva ```IllegalArgumentException``` si ```numMovimiento``` no está en el intervalo [1, getNumMovimientos()]

#### Tipos auxiliares

- TipoVictoria, enumerado. Puede tomar los valores OUTOFTIME, RESIGN, MATE, DRAW.
- Resultado, enumerado. Puede tomar los valores WHITE, BLACK, DRAW.

### Factoría - FactoriaPartidas
Clase de factoría para construir objetos de tipo Partidas.

- _Partidas leerPartidas(String nomfich)_:Crea un objeto de tipo Partidas a partir de la información recogida en el archivo csv, cuya ruta se da como parámetro.


### Tipo Contenedor - Partidas

Clase contenedora de los objetos de tipo Partida.

**Propiedades**:

-  _partidas_, de tipo _List\<Partida\>_, consultable. Lista de partidas de ajedrez 
-  _numero partidas_, de tipo _Integer_, consultable. Número de partidas del contenedor. 
 
**Constructores**: 

- C1: Constructor por defecto. Creal un objeto de tipo Partidas sin ninguna partida almacenada.
- C2: Constructor con un parámetro de tipo Collection\<Partida\>. Crea un objeto de tipo Partidas con las partidas incluidas en la colección dada como parámetro.
- C3: Constructor con un parámetro de tipo Stream\<Partida\>. Crea un objeto de tipo Partidas con las partidas incluidas en el Stream dado 

**Criterio de igualdad**: Dos partidas son iguales si lo son sus propiedades partidas.


**Otras operaciones**:
- _void agregarPartida(Partida p)_: Añade una partida de ajedrez al objeto.
- _Double getPromedioDuracionesMedias(TipoVictoria vic)_: Devuelve la media de la duración media(en segundos) por turno de las partidas. Si la media no se puede calcular, devuelve cero.
- _Map<String, Double> getPorcentajesSiguienteMovimiento(String movimiento, Integer numeroMovimiento)_: Devuelve un Map en el que las claves son movimientos siguientes al dado como parámetro (según el movimiento y la posición en la que se hace), y los valores el porcentaje de veces que se ha hecho ese movimiento. Por ejemplo,     si el movimiento es "Nc6" y el número de movimiento es el 6, el Map contiene como claves los movimientos hechos en séptimo lugar tras un movimiento "Nc6". Los valores serán el porcentaje de veces que se han hecho esos movimientos. Eleva ```IllegalArgumentException```si numeroMovimiento no es mayor o igual que uno.
- _Double getPorcentajeVictoriasDeApertura(String apertura, Resultado resultado)_: Devuelve el porcentaje de partidas que incluyen la cadena de apertura en su apertura y cuyo resultado es el dado como parámetro.
- _SortedSet<Partida> getNPartidasMasCortas(Integer anyo, Integer n)_: Devuelve un conjunto ordenado con las n partidas más cortas jugadas en el año dado como parámetro. El conjunto estará ordenado por el número de movimientos de la partida.
- _List<String> getNMejoresJugadores(Integer anyo, Integer n)_: Devuelve una lista con los ids de los n jugadores con más victorias en el año dado como parámetro.
- _Long getTiempoTotalJuego(String idJugador)_: Devuelve el total de minutos jugados por el jugador dado como parámetro.
- _String getJugadorMasVictorias(Integer anyo, Resultado resultado)_:
Devuelve true si hay algún jugador que tenga más de n victorias.
- _Map<TipoVictoria, String> getGanadorNPorTipoVictoria(Integer n)_:
Devuelve un map en el que las claves son los tipos de victoria y el valor es el enésimo jugador con más rating entre los jugadores que han tenido victorias de ese tipo. Es decir, si hacemos un ranking de los jugadores según su rating, nos quedaríamos con el que está en la posición n.


