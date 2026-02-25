# Sistema de Microservicios Bancarios

Un sistema de microservicios para la gestión de clientes y cuentas bancarias, construido con Spring Boot, RabbitMQ y Docker. Cumple con los requerimientos de la prueba técnica, incluyendo comunicación asíncrona entre microservicios.

## 🏗️ Arquitectura

El proyecto consiste en dos microservicios principales:

### MS-CLIENTES
- **Puerto**: 8081
- **Función**: Gestión de clientes y sus datos personales
- **Tecnologías**: Spring Boot 3.3.0, Java 21, H2 Database, JPA, RabbitMQ (publicador de eventos)

### MS-CUENTAS  
- **Puerto**: 8082
- **Función**: Gestión de cuentas bancarias, movimientos y reportes
- **Tecnologías**: Spring Boot 3.3.0, Java 21, H2 Database, JPA, RabbitMQ (consumidor de eventos)

### RabbitMQ
- **Puerto**: 5672 (broker), 15672 (UI de administración)
- **Función**: Comunicación asíncrona entre microservicios (eventos de cliente)

## 📋 Requisitos

- Java 21
- Maven 3.6+
- Docker y Docker Compose
- Git
- RabbitMQ (incluido en docker-compose)

## 🚀 Inicio Rápido

### Opción 1: Usando Docker Compose (Recomendado)

```bash
# Desde la raíz del proyecto (donde está docker-compose.yml)
docker-compose up --build

# Los servicios estarán disponibles en:
# MS-CLIENTES: http://localhost:8081/api
# MS-CUENTAS: http://localhost:8082/api
# RabbitMQ UI: http://localhost:15672 (user: guest, pass: guest)
```

Si tu instalación usa Docker Compose v2, el comando equivalente es:

```bash
docker compose up --build
```

### Opción 2: Ejecución Local

Primero levanta RabbitMQ:
```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

Luego, en 2 terminales separadas:
```bash
# MS-CLIENTES
cd MS-CLIENTES
mvn spring-boot:run

# MS-CUENTAS (en otra terminal)
cd ../Cuentas
mvn spring-boot:run
```

## 📊 Base de Datos

El proyecto utiliza una base de datos H2 en memoria (los datos se reinician al reiniciar el servicio). El esquema y datos iniciales se definen en `BaseDatos.sql`:

### Tablas Principales

**clientes** (MS-CLIENTES)
- id, nombre, género, edad, identificación
- dirección, teléfono, cliente_id, contraseña, estado

**clientes_cache** (MS-CUENTAS, sincronizada por eventos)
- cliente_id, nombre, estado

**cuentas** (MS-CUENTAS) 
- id, número_cuenta, tipo_cuenta, saldo_inicial
- saldo_disponible, estado, cliente_id

**movimientos** (MS-CUENTAS)
- id, fecha, tipo_movimiento, valor, saldo, cuenta_id

### Datos Iniciales

El sistema incluye 3 clientes de ejemplo:
- Jose Lema (jose123)
- Marianela Montalvo (marianela123) 
- Juan Osorio (juan123)

Y 5 cuentas bancarias asociadas.

## 🔗 Endpoints API

### MS-CLIENTES (Port 8081)

#### Clientes
- `GET /api/clientes` - Obtener todos los clientes
- `GET /api/clientes/{id}` - Obtener cliente por ID
- `GET /api/clientes/clienteId/{clienteId}` - Obtener cliente por clienteId
- `POST /api/clientes` - Crear nuevo cliente
- `PUT /api/clientes/{id}` - Actualizar cliente
- `PATCH /api/clientes/{id}` - Actualización parcial de cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente

### MS-CUENTAS (Port 8082)

#### Cuentas
- `GET /api/cuentas` - Obtener todas las cuentas
- `GET /api/cuentas/{id}` - Obtener cuenta por ID
- `POST /api/cuentas` - Crear nueva cuenta
- `PUT /api/cuentas/{id}` - Actualizar cuenta
- `PATCH /api/cuentas/{id}` - Actualización parcial de cuenta

#### Movimientos
- `GET /api/movimientos` - Obtener todos los movimientos
- `POST /api/movimientos` - Crear nuevo movimiento

#### Reportes
- `GET /api/reportes?clienteId={clienteId}&fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD` - Reporte por fechas y cliente

## 🧪 Testing

### Ejecutar Tests

```bash
# Para MS-CLIENTES
cd MS-CLIENTES
mvn test

# Para MS-CUENTAS  
cd ../Cuentas
mvn test
```

### Tests Unitarios
- MS-CLIENTES: Incluye tests unitarios para la capa de servicio (por ejemplo `ClienteService`)
- MS-CUENTAS: Incluye tests unitarios para la capa de servicio (por ejemplo `CuentaService` y `MovimientoService`, según corresponda)

### Prueba de Integración
- MS-CLIENTES: `ClienteControllerIT` (SpringBootTest + MockMvc). Ejecutar solo esa prueba:

```bash
cd MS-CLIENTES
mvn -Dtest=ClienteControllerIT test
```

## 📦 Colección Postman

Se incluye una colección de Postman en `postman-collection.json` con todos los endpoints listos para probar:

1. Importa el archivo en Postman
2. Asegúrate de que los servicios estén corriendo
3. Ejecuta las peticiones desde la colección "Banco Prueba Tecnica"

## 🔧 Configuración

### Variables de Entorno

**MS-CUENTAS** puede usar la variable:
- `CLIENTES_SERVICE_URL=http://ms-clientes:8081/api` (sobreescribe `clientes.service.url` y permite comunicación entre microservicios)

### Base de Datos H2

Ambos microservicios exponen una consola H2:
- MS-CLIENTES: http://localhost:8081/api/h2-console
- MS-CUENTAS: http://localhost:8082/api/h2-console

**Credenciales por defecto:**
- JDBC URL (MS-CLIENTES): `jdbc:h2:mem:clientesdb`
- JDBC URL (MS-CUENTAS): `jdbc:h2:mem:cuentasdb`
- Username: `sa`
- Password: (vacío)

## 🏛️ Estructura del Proyecto

```
ApiBank/
├── MS-CLIENTES/                 # Microservicio de Clientes
│   ├── src/main/java/          # Código fuente
│   │   ├── config/            # Configuración RabbitMQ y Jackson
│   │   ├── Domain/           # Entidades JPA (Persona, Cliente)
│   │   ├── Dto/              # DTOs
│   │   ├── Exception/         # Excepciones
│   │   ├── Repository/        # Repositorios JPA
│   │   ├── Service/          # Lógica de negocio y publicación de eventos
│   │   ├── Controller/       # Endpoints REST
│   │   └── event/           # Eventos y publisher
│   ├── src/test/java/          # Tests
│   ├── pom.xml                 # Configuración Maven
│   └── Dockerfile              # Configuración Docker
├── Cuentas/                    # Microservicio de Cuentas
│   ├── src/main/java/          # Código fuente
│   │   ├── config/            # Configuración RabbitMQ y Jackson
│   │   ├── domain/           # Entidades JPA (Cuenta, Movimiento, ClienteCache)
│   │   ├── dto/              # DTOs
│   │   ├── exception/         # Excepciones y handler global
│   │   ├── repository/        # Repositorios JPA
│   │   ├── service/          # Lógica de negocio y reportes
│   │   ├── controller/       # Endpoints REST
│   │   ├── client/           # Cliente REST con fallback
│   │   └── event/           # Eventos y listener
│   ├── src/test/java/          # Tests
│   ├── pom.xml                 # Configuración Maven
│   └── Dockerfile              # Configuración Docker
├── BaseDatos.sql               # Esquema y datos iniciales
├── docker-compose.yml          # Orquestación de servicios (incluye RabbitMQ)
├── postman-collection.json     # Colección Postman
└── README.md                   # Este archivo
```

## 🛠️ Tecnologías Utilizadas

- **Java 21**: Última versión LTS de Java
- **Spring Boot 3.3.0**: Framework principal
- **Spring Data JPA**: Persistencia de datos
- **Spring AMQP (RabbitMQ)**: Comunicación asíncrona entre microservicios
- **H2 Database**: Base de datos en memoria
- **Lombok**: Reducción de código boilerplate
- **Docker**: Contenerización
- **Maven**: Gestión de dependencias
- **JUnit**: Testing unitario

## 🔄 Comunicación entre Microservicios

MS-CUENTAS se comunica con MS-CLIENTES de dos formas:
1. **Asíncrona (principal)**: MS-CLIENTES publica eventos de cliente (CREATED/UPDATED/DELETED) a RabbitMQ. MS-CUENTAS los consume para mantener una tabla local `clientes_cache` y evitar llamadas REST frecuentes.
2. **Síncrona (fallback)**: Si el dato no está en cache, MS-CUENTAS consulta a MS-CLIENTES vía REST usando `ClienteClient`.

Esta estrategia garantiza que el sistema siga funcionando incluso si RabbitMQ no está disponible.

## 🐛 Manejo de Errores

El sistema incluye manejo centralizado de excepciones:
- `ResourceNotFoundException`: Recursos no encontrados
- `SaldoInsuficienteException`: Saldo insuficiente para transacciones
- `GlobalExceptionHandler`: Manejo global de errores

## 📈 Monitoreo y Logs

Los servicios generan logs estándar de Spring Boot y pueden ser monitoreados a través de:
- Consola de la aplicación
- Logs del contenedor Docker
- Endpoints de actuator (si se configuran)

### Verificación de eventos asíncronos
- **RabbitMQ UI**: http://localhost:15672 (user: guest, pass: guest)
  - Ve a **Queues** -> `cuentas.clientes.queue` para ver mensajes en tiempo real.
- **Cache local en Cuentas**: http://localhost:8082/api/h2-console
  - JDBC URL: `jdbc:h2:mem:cuentasdb`
  - Consulta: `SELECT * FROM clientes_cache;`

