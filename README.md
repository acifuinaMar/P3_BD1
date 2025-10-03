# Manual de Usuario – Proyecto 3

## Índice
1. [Introducción](#1-introducción)  
2. [Requisitos del sistema](#2-requisitos-del-sistema)  
3. [Instalación y configuración](#3-instalación-y-configuración)  
4. [Descripción de la interfaz](#4-descripción-de-la-interfaz)  
5. [Guía paso a paso](#5-guía-paso-a-paso)  
6. [Operaciones CRUD](#6-operaciones-crud)  
7. [Sincronización](#7-sincronización)
8. [Solución de problemas](#8-solución-de-problemas)
---

## 1. Introducción
Este proyecto es una aplicación en Java que permite gestionar registros de personas en dos bases de datos diferentes (MySQL y PostgreSQL) con capacidad de sincronizar los cambios realizados entre ellas.

Características principales:

- Gestión completa de registros de personas (inserción, actualización y eliminación).
- Conexión simultánea a MySQL y PostgreSQL.
- Sincronización bidireccional.
- Bitácora de actividades en tiempo real.
- Interfaz gráfica intuitiva.
---

## 2. Requisitos del sistema

- **Sistema operativo:** Windows 10 o superior / Linux / macOS  
- **Java Development Kit:** JDK 17  
- **RAM recomendada:** 4 GB o más  
- **Espacio en disco:** 500MB
- **MySQL Server**: 8.0 o superior
- **PostgreSQL**: 12 o superior
- **Bases de datos preconfiguradas:** tabla `personas`

---

## 3. Instalación y configuración

1. Preparar las bases de datos.
- MySQL
```
CREATE DATABASE gestion_personas;
USE gestion_personas;

CREATE TABLE personas (
    dpi VARCHAR(13) PRIMARY KEY,
    primer_nombre VARCHAR(50) NOT NULL,
    segundo_nombre VARCHAR(50),
    primer_apellido VARCHAR(50) NOT NULL,
    segundo_apellido VARCHAR(50),
    direccion_domiciliar VARCHAR(200),
    telefono_casa VARCHAR(15),
    telefono_movil VARCHAR(15),
    salario_base DECIMAL(10,2),
    bonificacion DECIMAL(10,2),
    ultima_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```
- PostgreSQL
```
CREATE DATABASE gestion_personas;

CREATE TABLE personas (
    dpi VARCHAR(13) PRIMARY KEY,
    primer_nombre VARCHAR(50) NOT NULL,
    segundo_nombre VARCHAR(50),
    primer_apellido VARCHAR(50) NOT NULL,
    segundo_apellido VARCHAR(50),
    direccion_domiciliar VARCHAR(200),
    telefono_casa VARCHAR(15),
    telefono_movil VARCHAR(15),
    salario_base DECIMAL(10,2),
    bonificacion DECIMAL(10,2),
    ultima_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
2. Configuración de la aplicación.
Asegurarse de que el archivo de configuración de conexiones contenga los parámetros correctos para ambas bases de datos (URL, usuario y contraseña).
---

## 4. Descripción de la interfaz
### 4.1 Panel de conexiones
#### Controles disponibles
- Conectar MySQL: Estable conexión con base de datos MySQL.
- Conectar PostgreSQL: Establece conexión con base de datos PostgreSQL.
- Desconectar MySQL: Cierra conexión MySQL.
- Desconectar PostgreSQL: Cierra conexión con PostgreSQL.
- Sincronizar Bases de datos: Ejecuta el proceso de sincronización.

#### Indicadores de estado
- Verde: Base de datos conectada.
- Rojo: Base de datos desconectada.

### 4.2 Panel de Formulario
#### Campos del formulario
- DPI (Obligatorio)
- Primer nombre (Obligatorio)
- Segundo nombre
- Primer apellido (Obligatorio)
- Segundo apellido
- Dirección domiciliar
- Teléfono de casa
- Télefono Móvil
- Salario base
- Bonificación

### 4.3 Panel de operaciones
#### Botones disponibles
- Agregar pesrona: registra una nueva persona.
- Actualizar persona: modifica una persona existente.
- Eliminar persona: elimina una persona ubicada por medio de su DPI.
- Buscar por DPI: buscar y carga los datos de una persona localizada por medio de su DPI.
- Limpiar campos: vacía todos los campos.

### 4.4 Panel de bitácora
- Muestra un registro temporal de todas las operaciones.
- Sigue el formato: `[YYYY-MM-DD HH:MM:SS] Mensaje de operación`.
- Utiliza el color negro de fondo y texto verde.
- Botón **limpiar bitácora** para resetear el visualizador.
---

## 5. Guía paso a paso

### 5.1 Conectar bases de datos
1. Hacer clic en **Conectar MySQL**. Esperar el mensaje "Conectado a MySQL existosamente" y a que el indicador **MySQL: Conectado** cambie a verde.
2. Hacer clic en **Conectar PostgreSQL**. Esperar el mensaje "Conectado a PostgreSQL existosamente" y a que el indicador **PostgreSQL: Conectado** cambie a verde. 

_Los botones de operaciones CRUD se habilitarán solamente cuando al menos una base de datos esté conectada._

---

### 5.2 Registrar una nueva persona.
1. Completar los campos obligatorios (DPI, primer nombre y primer apellido).
2. Completar los campos opcionales según sea necesario.
3. Hacer clic en **Agegar persona**. Si los datos son válidos, aparecerá un mensaje de confirmación y la operación se registrará en bitácora.

---
### 5.3 Buscar una persona existente
1. Ingresar el DPI en el campo correspondiente.
2. Hacer clic en **Buscar por DPI**. Si existe en la base de datos, la información se cargará en el formulario; si no, se mostrará un mensaje de error.
---
### 5.4 Actualizar datos de una persona
1. Buscar a la persona por DPI (inciso 5.3).
2. Modificar los campos necesarios.
3. Hacer clic en **Actualizar persona**. Los cambios se guardarán en la base de datos activa y habrá un mensaje de confirmación en bitácora.
---
### 5.5 Eliminar una persona
1. Ingresar el DPI de la persona a eliminar en el campo correspondiente.
2. Hacer clic en **Eliminar persona**. Aparecerá un diálogo de cofirmación para que confirmemos la eliminación. La operación se registrará en la bitácora y los campos se limpiarán.
---
### 5.6 Sincronizar bases de datos
1. Asegurarse de que ambas bases estén conectadas (indicadores en verde).
2. Hacer clic en **SIncronizar Bases de Datos**. El sistema comparará y resolverá diferencias apoyándose de la bitácora. Obtendremos un mensaje de finalización exitosa o error.
---
## 6. Operaciones CRUD
### Agregar persona
#### Consideraciones
- El DPI no debe haber sido ingresado o estar relacionado a una persona existente.
- Los campos obligatorios deben ser completados.
- El salario y bonificación deben ser números válidos (dos decimales).

### Buscar persona
#### Comportamiento
- Búsqueda de una persona específica por medio del DPI.
- Carga todos los datos en el formulario.
- Habilita los campos editables por si quisieramos editarlos.

### Actualizar persona
#### Consideraciones
- El DPI no puede modificarse.
- En las bases de datos se actualizaran únicamente los campos que fueron modificados.
- Registrará el timestamp (fecha y hora) en que se realizó la modificación.

### Eliminar persona
#### Seguridad
- Antes de eliminar a una persona, se muestra un diálogo de confirmación.
- Elimina físicamente el registro.
- Registra la transacción en la bitácora.

---
## 7. Sincronización
### ¿Cuándo sincronizar?
- Después de realizar operaciones en una sola base de datos.
- Al detectar inconsistencias entre ambas bases.
- Como manteniemiento periódico.

### Proceso de sincronización
#### 1. Detección de diferencias.
- Compara todos los registros por DPI.
- Identifica registros faltantes en cada base.
- Detecta conflictos por timestamp de modificación.

#### 2. Resolución de conflictos
- Registros nuevos: se insertan en la base faltante.
- Registros eliminados: se eliminan según la bitácora.
- Registros modificados: prvalece la modificación más reciente.

#### 3. Bitácora de sincronización
- Son aquellas operaciones marcadas con `_SINC`.
- Registro de todas las acciones realizadas.
- Timestamp de cada operación.

### Reglas de sincronización
| Escenario | Acción |
|-----------|--------|
Registro en MySQL pero no en PostgreSQL. | Insertar en PostgreSQL
Registro en PostgreSQL pero no en MySQL. | Insertar en MySQL.
Mismo registro modificado en ambas. | Conservar la modificación más reciente.
Registro eliminado en una base. |   Eliminar en la otra base (según bitácora).


## 8. Solución de problemas
### Error de conexión a MySQL o PostgreSQL.
- Verificar que el gestor de la base de datos correspondiente esté ejecutándose.
- Confirmar las credenciales de conexión.
- Verificar los permisos de usuario.
- Confirmar que la base de datos exista.

### DPI existente.
- Utilizar un DPI diferente o buscar el registre existente para su modificación o eliminación.

### Campos numéricos inválidos.
- Ingresar solamente números con un punto decimal y 2 decimales (ej. 2500.00)
