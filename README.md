# 🧾 Pricing Engine

Este proyecto es una Prueba de Concepto (PoC) para un sistema de consulta de precios en un contexto de comercio electrónico.  
El servicio expone un endpoint REST que permite obtener el precio aplicable a un producto en una fecha concreta, teniendo en cuenta reglas de negocio como la prioridad de tarifas.

---

## 🎯 Objetivo

Dado:

- Fecha de aplicación  
- ID de producto  
- ID de cadena (brand)  

El sistema devuelve:

- Product ID  
- Brand ID  
- Tarifa aplicable (priceList)  
- Rango de fechas  
- Precio final (price)  

---

## 🏗️ Arquitectura

Se ha implementado usando Arquitectura Hexagonal:

- Domain → lógica de negocio  
- Application → casos de uso (funcionalidades de negocio)  
- Infrastructure → adaptadores (Controller REST, DB, config)  

---

## ⚙️ Tecnologías

- Java 21  
- Spring Boot 3  
- Spring Data JPA  
- H2 Database (in-memory)  
- Spring Cache (+ Caffeine)  
- JUnit (+ MockMvc)  
- Lombok  
- MapStruct  
- OpenAPI (YAML)  
- Dockerfile  
- Terraform  

---

## 🧪 Testing (TDD)

Se ha aplicado TDD:

1. Tests creados antes del código  
2. Tests fallando inicialmente  
3. Implementación progresiva hasta verde  

---

## 🧠 Principios SOLID aplicados

**S - Single Responsibility**  
- GetPriceUseCase: solo contiene lógica de negocio  
- PriceController: solo maneja HTTP  
- En domain > exceptions se almacena toda la lógica de control de excepciones  

**O - Open/Closed**  
- Nuevas reglas de pricing se pueden añadir sin modificar clases existentes (estrategias)  

**L - Liskov Substitution**  
- PriceRepositoryPort permite sustituir implementaciones (H2, Mongo, API externa…)  

**I - Interface Segregation**  
- PriceRepositoryPort es específico (no genérico)  

**D - Dependency Inversion**  
- GetPriceUseCase depende de interfaz (Port), no de implementación  

---

## 🧩 Flujo completo

Controller  
↓  
UseCase  
↓  
RepositoryPort  
↓  
RepositoryAdapter  
↓  
PriceMapper (Entity → Domain)  
↓  
UseCase  
↓  
Controller  
↓  
PriceDtoMapper (Domain → DTO)  
↓  
Response  

---

## ☁️ Infraestructura Cloud (AWS)

### 🐳 Docker  
docker build -t pricing-service .

---

### ☁️ AWS App Runner  
Se ha elegido App Runner por:

- Serverless  
- Autoescalado  
- Sin gestión de infraestructura  
- Integración directa con contenedores  

---

### 📦 Terraform  
Incluye:

- iam.tf  
- main.tf  
- monitoring.tf  
- providers.tf  
- variables.tf  

---

### 🔐 Seguridad  
- IAM Role con mínimo privilegio  

---

### 📊 Observabilidad  
- Logs enviados a CloudWatch + configuración de alertas por caída de servicio  

---

## 🟢 Cómo ejecutar

### 1. Clonar proyecto  
git clone <repo-url>  
cd pricing-service  

---

### 3. Compilar y ejecutar  
mvn clean install  
mvn spring-boot:run  

---

### 4. Endpoint  
GET /prices  

---

### Swagger  
http://localhost:8080/swagger-ui.html  

---

### Ejemplo llamada  
GET /prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1  

---

### GET vs POST (en este caso de uso)

**GET (query params)**  
- Cacheable por defecto (HTTP caching)  
- Marida bien con CDN (CloudFront)  
- Fácil de monitorizar (URLs visibles)  
❌ Query params “menos elegantes”  
❌ Limitación de longitud si los queryParams crecen mucho  

**POST (body)**  
- Más limpio  
- Mejor para queries complejas  
❌ No cache HTTP automática, lo que se traduce en más carga en backend  

---

### 4. Consola H2 (BBDD interna)  
http://localhost:8080/h2-console  

---

## 🚀 Deploy

### Build del JAR  
mvn clean package  

---

### Build de la imagen Docker  
docker build -t pricing-service .  

---

### Login en ECR  
aws ecr get-login-password --region <region> \  
| docker login \  
--username AWS \  
--password-stdin <account-id>.dkr.ecr.<region>.amazonaws.com  

---

### Tag de la imagen  
docker tag pricing-service:latest <repo-url>:latest  

---

### Push a ECR  
docker push <repo-url>:latest  

---

### Deploy con Terraform  
terraform init  
terraform apply  

---

## 🎯 Conclusión

Este proyecto demuestra:

- Uso de TDD  
- Uso de Arquitectura Hexagonal  
- Principios SOLID  
- Versionado con GitFlow  
- Escalabilidad + uso de caché (tanto interna a nivel microservicio como preparado para externa, a nivel de CDN - AWS CloudFront o API GW caché)  
- Preparado para despliegue en contenedores Docker  
- Preparado para cloud AWS (Terraform) + alertas de monitorización  
