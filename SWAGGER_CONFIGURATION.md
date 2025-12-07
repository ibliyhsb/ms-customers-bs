# Configuración de Swagger en ms-customers-bs

## ✅ Estado: Implementación Completa y Funcionando

La documentación Swagger/OpenAPI está completamente configurada y funcional en el microservicio.

## 📍 URLs de Acceso

- **Swagger UI**: http://localhost:8081/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8081/v3/api-docs
- **OpenAPI YAML**: http://localhost:8081/v3/api-docs.yaml

## 🔧 Configuración Implementada

### 1. Dependencia en build.gradle

```gradle
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0'
```

### 2. Configuración de Swagger (SwaggerConfig.java)

**Ubicación**: `src/main/java/cl/duoc/ms_customers_bs/config/SwaggerConfig.java`

Configuración que incluye:
- Información del API (título, versión, descripción)
- Esquema de seguridad JWT con Bearer token
- Requisitos de seguridad para todos los endpoints

### 3. Configuración de Seguridad (SecurityConfig.java)

Los siguientes endpoints están permitidos sin autenticación:
- `/swagger-ui/**` - Interfaz de usuario de Swagger
- `/swagger-ui.html` - Página principal de Swagger
- `/v3/api-docs/**` - Documentación OpenAPI en formato JSON
- `/v3/api-docs` - Endpoint principal de documentación
- `/api/customers/login` - Endpoint de login
- `/api/customers/authenticate/**` - Endpoints de autenticación

### 4. Filtro JWT (JwtAuthenticationFilter.java)

El método `shouldNotFilter()` excluye las rutas de Swagger del filtro JWT para permitir acceso público a la documentación.

## 🎯 Endpoints Documentados

### Customers API

#### 🔓 Públicos (sin autenticación)
- `POST /api/customers/login` - Login de usuario
- `POST /api/customers/authenticate` - Autenticación alternativa
- `POST /api/customers/authenticate/{email}/{password}` - Autenticación por URL (deprecated)
- `GET /api/customers/authenticate/{email}/{password}` - Autenticación legacy (deprecated)

#### 🔒 Protegidos (requieren JWT)
- `GET /api/customers/GetCustomerById/{idCustomer}` - Obtener cliente por ID (USER, ADMIN)
- `GET /api/customers` - Obtener todos los clientes (ADMIN)
- `GET /api/customers/GetCustomerByEmail/{email}` - Obtener cliente por email (USER, ADMIN)
- `POST /api/customers` - Crear nuevo cliente
- `PUT /api/customers/UpdateCustomer` - Actualizar cliente (USER, ADMIN)
- `DELETE /api/customers/DeleteCustomerById/{idCustomer}` - Eliminar cliente (ADMIN)

## 🔐 Uso de Swagger con JWT

### Paso 1: Obtener Token
1. Expandir el endpoint `POST /api/customers/login`
2. Click en "Try it out"
3. Ingresar credenciales:
```json
{
  "email": "usuario@ejemplo.com",
  "password": "contraseña"
}
```
4. Copiar el token de la respuesta

### Paso 2: Autorizar en Swagger
1. Click en el botón "Authorize" (candado verde) en la parte superior derecha
2. Ingresar el token en el campo "Value" con formato: `Bearer {tu-token-aquí}`
3. Click en "Authorize"
4. Click en "Close"

### Paso 3: Probar Endpoints Protegidos
Ahora puedes probar cualquier endpoint protegido y Swagger incluirá automáticamente el header de autorización.

## 🎨 Anotaciones de Swagger en el Controlador

El controlador incluye anotaciones para documentar cada endpoint:

```java
@Tag(name = "Customers", description = "API para la gestión de clientes")
@Operation(summary = "Descripción del endpoint")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Éxito"),
    @ApiResponse(responseCode = "404", description = "No encontrado")
})
@SecurityRequirement(name = "bearer-jwt")
```

## 📝 Notas Adicionales

- La documentación se genera automáticamente a partir del código
- Los modelos DTO se documentan automáticamente con sus campos
- Las anotaciones de validación se reflejan en la documentación
- Los roles de seguridad (`@PreAuthorize`) se muestran en la descripción

## ⚠️ Advertencias del Sistema

Si ves estos mensajes en los logs, son informativos y no afectan la funcionalidad:

```
Failed to set up a Bean Validation provider
Cannot determine local hostname
```

Estos mensajes no impiden el funcionamiento correcto de Swagger.

## 🚀 Ejecutar la Aplicación

```bash
./gradlew bootRun
```

O desde tu IDE, ejecutar `MsCustomersBsApplication.java`

---

**Fecha de configuración**: 6 de diciembre de 2025
**Versión de SpringDoc**: 2.5.0
**Puerto de la aplicación**: 8081
