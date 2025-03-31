# Meli Discount API

## Descripción
Esta API RESTful permite gestionar descuentos exclusivos para vendedores en Mercado Libre, garantizando que solo un ítem activo por vendedor tenga el **Meli Discount**. Además, soporta la segmentación de descuentos por categorías.

## Diseño de arquitectura
![meli discount architecture](https://github.com/user-attachments/assets/3de36c91-6145-425d-9214-ec0ef3eb22ed)


## Tecnologías Utilizadas
- **Lenguaje:** Java 17
- **Framework:** Spring Boot
- **Autenticación:** JSON Web Tokens (JWT)
- **Integración con Mercado Libre:** APIs de Items y Categorías
- **Seguridad:** Implementación de autenticación y autorización con JWT

## Instalación y Ejecución

### Prerrequisitos
- Java 17 Coretto
- Maven

### Instalación
```sh
# Clonar el repositorio
$ git clone https://github.com/tu_usuario/meli-discount.git
$ cd meli-discount

# Construir la aplicación
$ mvn clean install
```

### Ejecución
```sh
# Correr la aplicación
$ mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

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
