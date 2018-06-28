# Taller de coches JPA #

Este repositorio contiene el código fuente para la gestión de un taller de coches. Esta aplicación se centra en el uso de un mapeo objeto-relacional (OR) para el acceso y tratamiento datos. Para su implementación se ha utilizado el desarrollo orientado a pruebas por lo que, en la carpeta test, puede encontrar los requisitos que cumple este proyecto.

# Decisiones de diseño #

Se ha utilizado un mapeador OR porque une caracterisiticas de las bases de datos relacionales y las orientadas a objetos (OO), creando de esta forma un sistema OO con persistencia en bases de datos relacionales lo que permite:
- Usar el paradigma OO desde el análisis hasta implementación
- Persistencia a volúmenes de datos grandes de forma eficiente
- Capacidad de hacer consultas eficientes
- Atender a la realidad de las empresas -> BDD relacionales
- Persistencia automática y transparente a objetos en BBDD relacionales
- Soporte de concurrencia

# Mapeo del sistema #

![mapeo](https://github.com/MrKarrter/CarWorkshop_JDBC/blob/master/Diagrama%20Tablas.png)
