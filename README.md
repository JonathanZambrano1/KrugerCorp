# Kruger Corp
#### Planteamiento - API

_Kruger Corporation requiere una aplicación para llevar un registro del inventario del estado de vacunación de los empleados_

## Instrucciones del SQL

- Crear una "schema" con el nombre kruger.
- Descargar el archivo "kruger.sql".
- Importar el archivo sql al gestor, para que la API pueda funcionar de manera correcta.

_Nota: Actualmente la base no posee ningun registro._

## Indicaciones de la API

La API cuenta con una clase de Conexiones, por lo que se tiene que tener en cuenta el cambio de Usuario y de la Contraseña,

- Descargar e importar el proyecto en nuestro IDE, para así poder tener un correcto funcionamiento de este.
- Dentro del proyecto se el archivo "KrugerCorp.yaml", esta la podremos visualizar utilizando el Swagger Editor https://editor.swagger.io
- Dentro del pom.xml encontraremos todas las dependencias de la aplicación.


## Consumir la API -  Postman
_GET - /login/{cedula}/{password} : Recibe la cédula y la contraseña._
_POST - /register: Recibe un formato JSON. Ejemplo:_

```sh
{
    "cedula": "Ingrese la Cedula",
    "nombre": "Ingrese el Nombre",
    "apellido": "Ingrese el Apellido",
    "correo": "Ingrese el Correo",
    "fechaNacimiento": "Ingrese la Fecha",
    "direccion": "Ingrese la Dirección",
    "telefono": "Ingrese el Telefono",
    "rol": "Ingrese el rol Administrador o Empleado"
}
```
_GET - /alta/{cedula}: Recibe la cedula._

_PUT - /update: Recibe un formato JSON. Ejemplo:_

```sh
{
    "cedula": "Ingrese la Cedula",
    "nombre": "Ingrese el Nombre",
    "apellido": "Ingrese el Apellido",
    "correo": "Ingrese el Correo",
    "fechaNacimiento": "Ingrese la Fecha",
    "direccion": "Ingrese la Dirección",
    "telefono": "Ingrese el Telefono",
    "rol": "Ingrese el rol Administrador o Empleado"
}
```
_PUT - /delete/{cedula}: Recibe la cedula._
_PUT - /updateVacunacion: Recibe un formato JSON. Ejemplo:_
```sh
{
    "cedula": "Ingrese la Cedula",
    "estadoVac": "Ingrese el estado 1 o 0",
    "tipoVacuna": "Ingrese el Tipo de Vacuna",
    "fechaVacuna": "Ingrese la fecha ejemplo (año-mes-dia)",
    "numeroDosis": Ingrese el numero de dosis
}
```
_Nota: El estao de la vacuna es 1 o 0 donde 1 es Vacunado y 0 No Vacunado._
_GET - /filter/{option}/{info}: Existen 4 tipo de filtrado:_
-   filter/{state}/{0 - 1}: El estado si esta vacunado o no.
-   filter/{type}/{Nombre de la vacuna}: Para saber las personas vacunadas con ese tipo de vacuna.
-   filter/{range}/{Rango de la fecha 1, Rango de la Fecha 2}: Tener en cuenta el siguiente Ejemplo: 2020-01-21,2022-03-15.
-   filter/{rol}/{Empleado o Administrador}: El listado de las personas con ese rol.

