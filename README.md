# ASTRAManagement
Aplicación PMDM

# ✨ ASTRA Management

<p align="center">
  <strong>Aplicación Android nativa para la gestión interna de una agencia de entretenimiento.</strong>
  <br>
  Gestión de artistas, eventos y asistencia del personal desde una única aplicación.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Plataforma-Android-3DDC84?logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/Lenguaje-Java-orange?logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Versión-1.0-8A2BE2" alt="Versión 1.0">
  <img src="https://img.shields.io/badge/Curso-2026--27-blue" alt="Curso 2026-27">
</p>

---

## 📌 Información del proyecto

| | |
|---|---|
| **Proyecto** | 1.ª evaluación de PMDM |
| **Curso académico** | 2026-27 |
| **Autora** | María Alonso |
| **GitHub** | [@MeryDreams](https://github.com/MeryDreams) |
| **Versión** | 1.0 |
| **Fecha** | 20 de noviembre de 2026 |
| **Repositorio** | [ASTRAManagement](https://github.com/MeryDreams/ASTRAManagement) |

## 🎯 Descripción

**ASTRA Management** es una aplicación Android nativa desarrollada en Java para facilitar la gestión interna de una agencia de entretenimiento.

La aplicación permite administrar un catálogo de artistas, consultar y modificar sus fichas, organizar eventos asociados a cada artista y gestionar la asistencia del personal a un showcase.

Su diseño está pensado para centralizar las principales tareas de administración en una interfaz sencilla e intuitiva, utilizando almacenamiento local mediante SQLite y Room.

## 🚀 Funcionalidades principales

### 🔐 Autenticación
- Inicio de sesión mediante usuarios almacenados en SQLite.
- Validación de credenciales.
- Consultas a la base de datos en segundo plano.

### 🏠 Panel de control
- Acceso mediante tarjetas a las diferentes secciones de la aplicación.
- Navegación hacia artistas, altas, búsquedas, eventos y asistencia.
- Gesto de arrastrar y soltar (*Drag & Drop*) para acceder a la sección «Acerca de».

### 🎤 Gestión de artistas
- Visualización del catálogo mediante `RecyclerView` y `CardView`.
- Consulta de fichas individuales.
- Eliminación de artistas mediante pulsación larga y diálogo de confirmación.
- Alta y edición de artistas.
- Selección del tipo de artista y su estado.
- Valoración mediante `RatingBar` y popularidad mediante `SeekBar`.

### 🔎 Búsqueda de artistas
- Búsqueda por tipo de artista.
- Búsqueda por nombre.
- Búsqueda por sede.

### 📅 Gestión de eventos
- Creación y edición de eventos.
- Selección de fecha mediante `DatePicker`.
- Selección de hora mediante `TimePicker`.
- Asociación de cada evento con un artista.
- Relación uno a muchos (1:N) entre artistas y eventos.
- Eliminación en cascada para mantener la integridad de los datos.

### 👥 Control de asistencia
- Registro del nombre del personal.
- Confirmación o cancelación de asistencia al showcase.
- Comunicación entre actividades mediante la Activity Result API.

### 💾 Persistencia de datos
- SQLite clásico para usuarios y datos de acceso.
- Room para la gestión de artistas y eventos.
- Operaciones de base de datos ejecutadas en segundo plano.
- Funcionamiento completamente local, sin necesidad de servidor ni conexión a internet.

## 🛠️ Tecnologías utilizadas

| Tecnología | Versión |
|---|---|
| Lenguaje de programación | Java 11 |
| Android Gradle Plugin | 9.5.1 |
| Gradle Wrapper | 9.6.0 |
| `compileSdk` | 37 |
| `targetSdk` | 37 |
| `minSdk` | 37 |
| Room | 2.6.1 |
| RecyclerView | 1.4.0 |
| CardView | 1.0.0 |
| ConstraintLayout | 2.1.4 (alias 2.2.2) |
| AppCompat | 1.8.0 |
| Material Components | 1.14.0 |
| Activity KTX | 1.13.0 |

## 📋 Requisitos del sistema

Para abrir y ejecutar el proyecto se necesita:

- **Android Studio:** compatible con Android Gradle Plugin 9.5.1 (Ladybug o superior).
- **JDK:** versión 21 para ejecutar Gradle 9.6.0.
- **Java:** el código fuente se compila con Java 11.
- **Android SDK:** API 37 instalada.
- **Dispositivo o emulador:** Android API 37 o superior.

> ⚠️ **Importante:** Actualmente, `minSdk = 37`, por lo que la aplicación solo puede instalarse en dispositivos compatibles con API 37 o superior. Para ampliar la compatibilidad con otros dispositivos Android, sería necesario reducir este valor y comprobar la compatibilidad de las dependencias utilizadas.

## ⚙️ Instalación y compilación

### 1. Clonar el repositorio

```bash
git clone https://github.com/MeryDreams/ASTRAManagement.git
```

### 2. Acceder al directorio del proyecto

```bash
cd ASTRAManagement
```

### 3. Compilar la aplicación

**Linux / macOS:**

```bash
./gradlew assembleDebug
```

**Windows:**

```bash
gradlew.bat assembleDebug
```

### 4. Instalar en un dispositivo o emulador

Con un dispositivo Android conectado o un emulador iniciado:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

El archivo APK generado se encontrará en:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## 🗂️ Estructura general del proyecto

```text
ASTRAManagement/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/astramanagement/
│           │   ├── bd/
│           │   ├── dao/
│           │   ├── modelo/
│           │   ├── room/
│           │   └── ...
│           │
│           ├── res/
│           │   ├── drawable/
│           │   ├── layout/
│           │   ├── mipmap/
│           │   └── values/
│           │
│           └── AndroidManifest.xml
│
├── gradle/
├── build.gradle
├── settings.gradle
└── README.md
```

## 👩‍💻 Autora

**María Alonso — MeryDreams**

Proyecto desarrollado como parte de la primera evaluación del módulo de Programación Multimedia y Dispositivos Móviles (PMDM), correspondiente al curso 2026-2027.

<p align="center">
  <a href="https://github.com/MeryDreams/ASTRAManagement">
    <strong>🌌 Visita el repositorio de ASTRA Management en GitHub</strong>
  </a>
</p>

---

<p align="center">
  <strong>ASTRA Management · Versión 1.0</strong>
  <br>
  <em>Organización, gestión y entretenimiento.</em>
</p>
