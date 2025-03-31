# Meli Discount API

 ## Bienvenidos 😁

## Descripción
Esta API REST permite gestionar descuentos exclusivos para vendedores en Mercado Libre, garantizando que solo un ítem activo por vendedor tenga el **Meli Discount**. Además, soporta la segmentación de descuentos por categorías tal cual como se requiere en el challenge presentado.

## Diseño de arquitectura
![meli discount architecture](https://github.com/user-attachments/assets/3de36c91-6145-425d-9214-ec0ef3eb22ed)

En este diseño he querido plasmar como aborde la solución desde un concepto macro donde puedo tener una visión clara de como mi desarrollo debe comportarse y conectarse según la lógica de negocio, teniendo conexiones al api externa de mercado libre , autenticando y autorizando la comunicación interna de mis endpoints y los scopess de cada sistema.

## Tecnologías Utilizadas
- **Lenguaje:** Java 17
- **Framework:** Spring Boot
- **Autenticación:** JSON Web Tokens (JWT)
- **Integración con Mercado Libre:** APIs de Items y Categorías
- **Seguridad:** Implementación de autenticación y autorización con JWT

## Instalación y Ejecución

### Prerrequisitos
- Java 17 Coretto (17.0.10)
- Maven
- Intellij Idea

### Instalación
```sh
# Clonar el repositorio
$ git clone https://github.com/tu_usuario/meli-discount.git
$ cd meli-discount
```

Luego de clonar nuestro repositorio procederemos abrir el proyecto en Intellij y nos iremos al apartado de project structure.

<img width="1440" alt="image" src="https://github.com/user-attachments/assets/5c745746-550a-473e-b084-dcc4322bc99c" />

En el apartado de project structure veremos directamente la configuración del SDK para nuestro proyecto, por favor realizar la siguiente configuración para ejecutar el proyecto.

<img width="1440" alt="image" src="https://github.com/user-attachments/assets/4ad9d447-048f-44ed-8f9b-1780b68849b4" />

y como Language level SDK Default

### Ejecución

Después de haber configurado el entorno podremos runear el proyecto directamente desde el archivo *ChallengeMeliDiscountApplication*

<img width="1440" alt="image" src="https://github.com/user-attachments/assets/1b4ce21c-af2c-4743-91e9-75f2a61db4f4" />


La API estará disponible en `http://localhost:8080`.

## Estructura y arquitectura de meli-discount

El proyecto sigue los principios de **Clean Architecture**, asegurando modularidad y separación de responsabilidades. La estructura del file system es la siguiente:

<img width="373" alt="image" src="https://github.com/user-attachments/assets/9edea16a-0237-49f2-aed1-16f95bc8ce8a" />

Esto se hace con el fin que a futuro se pueda tener una facilidad de escalar el codigo y trabajar en responsabilidades unicas por personas o equipos.

##Archivos de interés



## Endpoints

### Generar Token
```sh
curl --location 'http://localhost:8080/api/token/generate' \
--header 'Cookie: JSESSIONID=E4BBF30322D98082D2A19C2E937D5F09'
```

### Obtener Ítems con Meli Discount
```sh
curl --location 'http://localhost:8080/api/meli/discount?item_ids=MLA1747839094,MLA1641136702' \
--header 'Authorization: Bearer TU_TOKEN_AQUI'
```

### Obtener Categorías de los Ítems
```sh
curl --location 'http://localhost:8080/api/meli/discount/categories?item_ids=MLA1747839094%2CMLA1641136702' \
--header 'Authorization: Bearer TU_TOKEN_AQUI' \
--header 'Cookie: JSESSIONID=E4BBF30322D98082D2A19C2E937D5F09'
```

## Consideraciones
- Un vendedor solo puede tener un ítem con **Meli Discount** activo.
- Los ítems deben cumplir las reglas de solapamiento de fechas.
- Para segmentación por categorías, los ítems no deben compartir la misma categoría padre.

## Contacto
Si tienes dudas o mejoras, puedes contactarme en `tu.email@dominio.com`.
