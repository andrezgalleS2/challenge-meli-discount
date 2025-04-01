# Meli Discount API

 ## Bienvenidos 😁

## Descripción
Esta API REST permite gestionar descuentos exclusivos para vendedores en Mercado Libre, garantizando que solo un ítem activo por vendedor tenga el **Meli Discount**. Además, soporta la segmentación de descuentos por categorías tal cual como se requiere en el challenge presentado.

## Diseño de arquitectura
![meli discount architecture](https://github.com/user-attachments/assets/3de36c91-6145-425d-9214-ec0ef3eb22ed)

En este diseño he querido plasmar como aborde la solución desde un concepto macro donde puedo tener una visión clara de como mi desarrollo debe comportarse y conectarse según la lógica de negocio, teniendo conexiones al api externa de mercado libre , autenticando y autorizando la comunicación interna de mis endpoints y los scopes de cada sistema.

## Tecnologías Utilizadas
- **Lenguaje:** Java 21
- **Framework:** Spring Boot
- **Autenticación:** JSON Web Tokens (JWT)
- **Integración con Mercado Libre:** APIs de Items y Categorías
- **Seguridad:** Implementación de autenticación y autorización con JWT

## Instalación y Ejecución

### Prerrequisitos
- Java 21 Coretto (21.0.6)
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

<img width="1433" alt="image" src="https://github.com/user-attachments/assets/25805024-fde6-4adb-8958-19af388ccd13" />

y como Language level SDK Default

### Ejecución

Después de haber configurado el entorno podremos runear el proyecto directamente desde el archivo *ChallengeMeliDiscountApplication*

<img width="1440" alt="image" src="https://github.com/user-attachments/assets/1b4ce21c-af2c-4743-91e9-75f2a61db4f4" />


El proyecto tendrá como puerto de hospedaje `http://localhost:8080`.

## Estructura y arquitectura de meli-discount

El proyecto sigue los principios de **Clean Architecture**, asegurando modularidad y separación de responsabilidades. La estructura del file system es la siguiente:

<img width="373" alt="image" src="https://github.com/user-attachments/assets/9edea16a-0237-49f2-aed1-16f95bc8ce8a" />

Esto se hace con el fin que a futuro se pueda tener una facilidad de escalar el codigo y trabajar en responsabilidades unicas por personas o equipos.


## 📂 Archivos de Interés

A continuación, se describen algunos de los archivos clave en la implementación del proyecto:

## 📘 Challenge Meli Discount API

Este repositorio contiene la implementación de la API de Challenge Meli Discount, la cual interactúa con las APIs de Mercado Libre para obtener información de productos y categorías, aplicando descuentos según las reglas definidas.

### 📂 Archivos de Interés

### 1️⃣ ExternalGetCategoriesPort.java

**Ubicación:** `adapter/impl/ExternalGetCategoriesPort`

**Descripción:**
Clase encargada de consumir la API de Mercado Libre para obtener las categorías de productos.

**Principales funcionalidades:**  
- **Consumo de API externa:** Utiliza `RestTemplate` para realizar solicitudes GET a la API de Mercado Libre.
- **Autenticación:** Agrega el token de autorización en la cabecera de la petición.
- **Manejo de errores:** Registra en logs cualquier error en la consulta de categorías.

---

### 2️⃣ ExternalGetItemsPort.java

**Ubicación:** `adapter/impl/ExternalGetItemsPort`

**Descripción:**
Clase encargada de obtener información de productos desde la API de Mercado Libre.

**Principales funcionalidades:**  
- **Consumo de API externa:** Realiza solicitudes GET para obtener los detalles de los productos según los identificadores proporcionados.
- **Autenticación:** Usa `RestTemplate` con el token de autorización.
- **Gestión de errores:** Captura excepciones y reporta errores en logs.

---

### 3️⃣ DiscountController.java

**Ubicación:** `controller/DiscountController`

**Descripción:**
Controlador que expone endpoints para obtener información de productos con descuento y por categoría.

**Principales funcionalidades:**  
- **`/api/meli/discount`**: Recibe una lista de identificadores de productos y devuelve información con descuentos aplicados.
- **`/api/meli/discount/categories`**: Permite obtener información de productos con descuento agrupados por categoría.

---

### 4️⃣ TokenController.java

**Ubicación:** `controller/TokenController`

**Descripción:**
Controlador encargado de la generación de tokens de autenticación.

**Principales funcionalidades:**  
- **`/api/token/generate`**: Genera un nuevo token de autenticación necesario para consumir las APIs externas.

---
### 5️⃣ Seguridad

#### JwtTokenFilter.java

**Ubicación:** `security/JwtTokenFilter`

**Descripción:**  
Filtro de seguridad que valida los tokens JWT en cada solicitud, asegurando la autenticación del usuario antes de permitir el acceso a los recursos protegidos.

---

#### JwtTokenProvider.java

**Ubicación:** `security/JwtTokenProvider`

**Descripción:**  
Proveedor de tokens JWT, encargado de generar tokens de acceso con firma HMAC y gestionar su expiración.

---

### 6️⃣ Servicios

#### CategoriesService.java

**Ubicación:** `service/CategoriesService`

**Descripción:**  
Implementación del servicio de categorías. Recupera información de categorías basándose en los IDs de los productos, asegurando que los datos sean correctos y sin superposición.

---

#### ItemsService.java

**Ubicación:** `service/ItemsService`

**Descripción:**  
Servicio para obtener productos con descuentos. Filtra y agrupa los productos por vendedor, asegurando que las fechas de creación de los productos sean consideradas en la selección.

---

#### TokenService.java

**Ubicación:** `service/TokenService`

**Descripción:**  
Servicio encargado de la generación de tokens JWT, asegurando autenticación basada en credenciales preconfiguradas.

---

### 7️⃣ Utilidades

#### FunctionsUtils.java

**Ubicación:** `utils/FunctionsUtils`

**Descripción:**  
Clase de utilidades con funciones para validación de IDs, conversión de objetos a JSON y lógica para encontrar conjuntos óptimos de productos sin superposición temporal.

---

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

## Consideraciones para Escalar el Proyecto a 100k RPM 🔝

Para escalar este proyecto a 100k RPM, tendremos que tener las siguientes consideraciones que mejorarán el rendimiento para el proyecto y lo prepararán para altos niveles de tráfico y estrés:

### Escenario 1: Escalabilidad Vertical (única instancia)

Nuestro proyecto podrá tener una única instancia y un plan de infraestructura que sea capaz de escalar según las reglas de consumo de la máquina. Esto permitirá que, en los picos de consumo, haya un mayor aprovisionamiento de recursos para gestionar la alta demanda. Asimismo, cuando no haya picos de tráfico, los recursos podrán reducirse para evitar su desperdicio.

#### Consideraciones clave:
1. **Autoescalado (Auto-Scaling)**: 
   - Configurar un sistema de autoescalado para ajustar dinámicamente los recursos (CPU, memoria, etc.) según la demanda de tráfico. Servicios como **AWS Auto Scaling**, **Azure Scale Sets** o **Google Cloud Auto Scaling** son ideales para este tipo de configuraciones.

2. **Manejo de Picos de Demanda**: 
   - Asegurar que el sistema pueda reaccionar rápidamente durante los picos de tráfico para garantizar que la infraestructura pueda manejar un mayor número de solicitudes sin afectar el rendimiento.

3. **Optimización de Costos**:
   - Implementar estrategias para reducir los recursos durante los periodos de baja demanda, como el uso de instancias de bajo costo (**EC2 Spot Instances**) o ajustar dinámicamente la infraestructura para no desperdiciar recursos cuando la demanda disminuye.

4. **Monitoreo y Alertas**:
   - Implementar un sistema de monitoreo efectivo utilizando herramientas como **Prometheus** y **Grafana**, **AWS CloudWatch** o **Datadog**, para analizar métricas de uso y realizar ajustes automáticos de recursos según el comportamiento del sistema.

---

### Escenario 2: Escalabilidad Horizontal (instancias distribuidas)

En este escenario, el sistema se puede distribuir en instancias, como en pods de Kubernetes, lo que permite gestionar mejor los recursos y el tráfico. Al multiplicar las instancias del proyecto, se podrá gestionar el tráfico de manera más eficiente, lo que es una opción viable si el sistema crece y se vuelve más robusto con bases de datos y otros componentes adicionales.

#### Consideraciones clave:
1. **Contenedores y Orquestación (Kubernetes)**:
   - Usar **Kubernetes** para distribuir las instancias en contenedores dentro de un clúster de pods, lo que facilita la gestión de múltiples réplicas del sistema de forma eficiente. Esto garantizaría una mayor disponibilidad y escalabilidad.

2. **Balanceo de Carga**:
   - Implementar un balanceador de carga para distribuir el tráfico de manera equitativa entre todas las instancias del sistema. Soluciones como **NGINX**, **HAProxy** o servicios en la nube como **AWS Elastic Load Balancer (ELB)** o **Google Cloud Load Balancing** son esenciales para este propósito.

3. **Escalabilidad Automática**:
   - Configurar el autoescalado para ajustar automáticamente el número de réplicas según el tráfico entrante. **Kubernetes Horizontal Pod Autoscaler (HPA)** puede ser utilizado para gestionar la escalabilidad según las métricas de carga (como CPU o memoria).

4. **Resiliencia y Recuperación Ante Fallos**:
   - Utilizar estrategias de resiliencia como **Pod Disruption Budgets** y **Pod Affinity** en Kubernetes para asegurar la distribución adecuada de las instancias y garantizar la alta disponibilidad incluso durante fallos de componentes o recursos.



## Contacto
### Andrés Gallego Tovar 👷.
### Email: andrezgalle01@gmail.com - andres.gallegot@ecci.edu.co
### Tel: +57 312 661 3327
