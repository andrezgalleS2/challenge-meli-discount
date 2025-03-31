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


## 📂 Archivos de Interés

A continuación, se describen algunos de los archivos clave en la implementación del proyecto:

### 1️⃣ `Api/Discount/DiscountController.java`
**Responsabilidad:**  
Este controlador maneja los endpoints relacionados con los descuentos de productos en Mercado Libre.  

**Principales funcionalidades:**  
- `GET /api/meli/discount` → Obtiene los productos con descuento.
- `GET /api/meli/discount/categories` → Obtiene los productos con descuento por categoría.

### 2️⃣ `Api/Token/TokenController.java`
**Responsabilidad:**  
Proporciona un endpoint para la generación de tokens JWT necesarios para la autenticación.  

**Principales funcionalidades:**  
- `GET /api/token/generate` → Genera un token de autenticación JWT.

### 3️⃣ `Application/Usescase/Discount/Categories/CategoriesService.java`
**Responsabilidad:**  
Implementa la lógica de negocio para obtener productos categorizados por descuento.

**Principales funcionalidades:**  
- Valida los IDs de los productos.
- Obtiene información de categorías a través de la API de Mercado Libre.
- Agrupa los productos por categoría y filtra los conjuntos más relevantes.

### 4️⃣ `Application/Usescase/Discount/Items/ItemsService.java`
**Responsabilidad:**  
Encargado de la lógica para obtener productos con descuento.

**Principales funcionalidades:**  
- Agrupa productos por vendedor.
- Filtra productos según la mejor combinación de descuentos.
- Ordena los productos según su fecha de creación.

### 5️⃣ `Infrastructure/Adapter/GetCategories/ExternalGetCategoriesPort.java`
**Responsabilidad:**  
Adaptador que interactúa con la API externa de Mercado Libre para obtener información de categorías.

**Principales funcionalidades:**  
- Realiza peticiones HTTP a la API de Mercado Libre.
- Incluye autenticación con **Bearer Token**.
- Maneja respuestas y errores de la API externa.

Esta estructura modular permite desacoplar la lógica de negocio de la infraestructura y facilita la escalabilidad del proyecto. 🚀

### 6️⃣ Infrastructure/Jwt

### `JwtTokenFilter.java`
Filtro de seguridad que intercepta solicitudes HTTP para validar la autenticación del usuario mediante JWT o autenticación básica en ciertos casos.

**Principales funcionalidades:**  
- Si la solicitud es a `/api/token/generate`, se valida con autenticación básica (`Basic Auth`).
- Para otras rutas, se requiere un **token JWT válido** en el encabezado `Authorization`.
-  **Manejo de errores**:
- Si el token es inválido o está ausente, responde con `401 Unauthorized` y un mensaje JSON descriptivo.

---

### `JwtTokenProvider.java`
Componente responsable de generar tokens JWT para la autenticación de usuarios.

**Principales funcionalidades:**  
- 🔑 **Firma del Token**: Utiliza `HS256` para garantizar seguridad.
- 🕒 **Expiración configurable**: El tiempo de validez del token es configurable mediante propiedades.
- 📌 **Método principal**:
- `createToken(String username)`: Genera un JWT válido para el usuario proporcionado.

---

### 7️⃣ Mocks

### `Mocks.java`
Clase de utilidades para generar datos simulados utilizados en pruebas.

**Principales funcionalidades:**  
-  **Generación de datos de prueba**:
- `getItemsResponse()`: Retorna una lista simulada de `ItemsResponse` con datos ficticios.
- `getItemsForCategory(String categoryId)`: Simula la estructura de categorías de Mercado Libre.
- **Estructuras dinámicas**:
- Usa métodos auxiliares para crear categorías y productos de prueba.

---

### 8️⃣ Utils

### `Utils.java`
Clase de utilidades con funciones auxiliares para la aplicación.

**Principales funcionalidades:**  
-  **Conversión de datos**:
- `convertToJson(T object)`: Convierte un objeto a formato JSON con formato legible.
-  **Validación de IDs**:
- `isValidIds(String ids)`: Verifica que los IDs sigan el formato correcto (`MLAxxxx`).
- **Optimización de conjuntos de datos**:
- `getLargestNonOverlappingSet(List<ItemsResponse> items)`: Implementa un algoritmo para encontrar el conjunto más grande de elementos sin superposición temporal.



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
