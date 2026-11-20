# ASTRAManagement
Aplicación PMDM

<div align="center">

# 🎬 ASTRAManagement

**Aplicación PMDM · Gestión de artistas y eventos de una agencia de entretenimiento**

![PMDM](https://img.shields.io/badge/PMDM-2026%2F27-7B1FA2)
![Java](https://img.shields.io/badge/Java-11-007396)
![Android](https://img.shields.io/badge/Android-API%2037-3DDC84)
![Gradle](https://img.shields.io/badge/Gradle-9.6-02303A)
![Room](https://img.shields.io/badge/Room-2.6.1-4285F4)
![Estado](https://img.shields.io/badge/Estado-Entregado-2E7D32)

| 👤 Autor | 🔗 Repositorio | 📌 Versión |
| :---: | :---: | :---: |
| María Alonso (`MeryDreams`) | [MeryDreams/ASTRAManagement](https://github.com/MeryDreams/ASTRAManagement) | 1.0 · 20/11/2026 |

</div>

---

## 📑 Índice

- [🚀 Funcionalidades](#funcionalidades)
- [🛠️ Tecnologías](#tecnologias)
- [📋 Requisitos](#requisitos)
- [⚙️ Compilación e instalación](#compilacion)
- [🔑 Credenciales de prueba](#credenciales)
- [📁 Estructura del proyecto](#estructura)
- [🗄️ Base de datos](#base-de-datos)
- [📚 Documentación](#documentacion)
- [⚠️ Limitaciones y trabajo futuro](#limitaciones)
- [🤖 Herramientas de IA](#ia)
- [🧹 Antes de subir a GitHub](#github)

---

<a id="funcionalidades"></a>

## 🚀 Funcionalidades

Proyecto de la **1.ª evaluación de Programación de Dispositivos Móviles** (curso 2026-27). La app permite:

- 🔐 **Acceso** con usuarios reales guardados en SQLite, consultados en segundo plano.
- 🏠 **Panel de control** con tarjetas de artistas, altas, búsqueda, eventos y asistencia, más un gesto de arrastrar y soltar para «Acerca de».
- 🎤 **Catálogo de artistas** con `RecyclerView` + `CardView`: clic corto para la ficha, clic largo para borrar con confirmación.
- 📝 **Alta y edición** con `Spinner`, `RadioButton`, `Switch`, `RatingBar`, `SeekBar` y `ProgressBar`.
- 🔎 **Búsqueda** por tipo, artista y sede.
- 📅 **Eventos** con `DatePicker`, `TimePicker` y borrado en cascada al eliminar al artista.
- ✅ **Asistencia** del personal mediante la Activity Result API.
- 💾 **Persistencia local** con SQLite clásico y Room, sin servidor ni conexión a internet.

> [!NOTE]
> Toda la información se guarda únicamente en el dispositivo: no hay servidor ni sincronización en la nube.

---

<a id="tecnologias"></a>

## 🛠️ Tecnologías

| Elemento | Versión |
| :--- | :--- |
| ☕ Lenguaje | Java 11 |
| 🐘 Gradle / AGP | 9.6.0 / 9.5.1 |
| 🤖 SDK (compile / target / min) | 37 / 37 / 37 |
| 🗄️ Room | 2.6.1 |
| 📃 RecyclerView | 1.4.0 |
| 💳 CardView | 1.0.0 |
| 📐 ConstraintLayout | 2.1.4 |
| 🧩 AppCompat / Material / Activity KTX | 1.8.0 / 1.14.0 / 1.13.0 |

---

<a id="requisitos"></a>

## 📋 Requisitos

- 🧰 Android Studio compatible con AGP 9.5.1.
- ☕ JDK 21 para ejecutar Gradle 9.6.0.
- 📱 SDK de Android 37 instalado.
- 📲 Emulador o dispositivo con **API 37 o superior**.

> [!TIP]
> Si quieres probar en más dispositivos, baja `minSdk` de 37 a 24 o 26 en `app/build.gradle.kts`.

---

<a id="compilacion"></a>

## ⚙️ Compilación e instalación

```bash
git clone https://github.com/MeryDreams/ASTRAManagement.git
cd ASTRAManagement

./gradlew assembleDebug          # Windows: gradlew.bat assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

💡 También puedes abrir la carpeta en Android Studio y ejecutar la configuración **app**. El APK se genera en `app/build/outputs/apk/debug/app-debug.apk`.

---

<a id="credenciales"></a>

## 🔑 Credenciales de prueba

Los usuarios se crean automáticamente la primera vez que se instala la aplicación.

| Correo | Contraseña | Nombre |
| :--- | :--- | :--- |
| `maria@astra.com` | `1234` | María |
| `carlos@astra.com` | `1234` | Carlos |
| `laura@astra.com` | `1234` | Laura |

---

<a id="estructura"></a>

## 📁 Estructura del proyecto

```text
app/src/main/java/com/example/astramanagement/
├── login/        LoginActivity
├── bd/           AppDatabase (SQLiteOpenHelper)
├── dao/          UsuarioDAO, ArtistaDAO, EventoDAO
├── modelo/       Usuario
├── room/         Artista, Evento, ArtistaConEventos,
│                 ArtistaDatabase, DatabaseClient
├── adapters/     ArtistaAdapter, EventoAdapter
└── activities/   MainActivity, ArtistasActivity, AltaArtistaActivity,
                  FichaArtistaActivity, BuscarArtista, EventosActivity,
                  AltaEventoActivity, AsistenciaActivity,
                  ConfirmarAsistenciaActivity, AcercaDeActivity

docs/             Manual de usuario y manual técnico (PDF)
```

---

<a id="base-de-datos"></a>

## 🗄️ Base de datos

- 🧾 **SQLite** (`artistas.db`): tablas `usuarios` y `lista_personal`, relacionadas por `usuario_id` con borrado en cascada.
- 🧩 **Room** (`artistas_room.db`): entidades `Artista` y `Evento`, con clave foránea `Evento.artistaId → Artista.id` y consulta `@Relation` de artistas con sus eventos.
- 🧵 Las consultas se ejecutan en un `ExecutorService` y la interfaz se actualiza con `runOnUiThread`.

---

<a id="documentacion"></a>

## 📚 Documentación

| Manual | Enlace |
| :--- | :--- |
| 📖 Manual de usuario | [Ver PDF](docs/Manual_de_usuario_ASTRA_Management.pdf) |
| 🔧 Manual técnico | [Ver PDF](docs/Manual_tecnico_ASTRA_Management.pdf) |

Ambos en **A4, Calibri 11**, con portada, índice, introducción, bibliografía/webgrafía, nota sobre herramientas de IA y la URL de este repositorio.

---

<a id="limitaciones"></a>

## ⚠️ Limitaciones y trabajo futuro

> [!WARNING]
> Puntos pendientes que conviene conocer antes de la defensa.

- El segundo `Spinner` de `BuscarArtista` no se rellena: una variable local `nombres` oculta al campo de clase.
- La tabla `lista_personal` existe, pero todavía no se usa desde la interfaz.
- No hay menú lateral, menú de opciones ni menú contextual.
- Falta `res/values-en/strings.xml`: la aplicación está solo en español.
- Algunos layouts usan tamaños fijos en dp y pueden recortarse en horizontal.
- `minSdk = 37` limita los dispositivos compatibles.

---

<a id="ia"></a>

## 🤖 Herramientas de IA

> [!NOTE]
> Se ha usado el agente **OpenCode** (modelo **Big Pickle**) como apoyo para revisar el código, comprobar la compilación y redactar la documentación. El código y las decisiones de diseño son del estudiante, que puede explicarlos y modificarlos en la defensa. No se han empleado otros servicios de generación de código.

---

<a id="github"></a>

## 🧹 Antes de subir a GitHub

> [!IMPORTANT]
> No versiones `local.properties`, `.gradle/`, `build/`, `app/build/` ni `.idea/`. El `.gitignore` actual no ignora `app/build/` completo, así que añade esto:

```gitignore
/app/build
/.idea
```

---

<div align="center">

**Hecho con 💜 para PMDM · 2026/27**  
[🔗 Ver repositorio en GitHub](https://github.com/MeryDreams/ASTRAManagement)

</div>
