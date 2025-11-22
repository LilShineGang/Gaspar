# Comparación: CliOrderLinuxBDD vs CliOrderWindowsBDD

## 📊 Resumen de Cambios Realizados

Este documento explica las diferencias entre el proyecto original (Linux) y la versión adaptada para Windows.

## 🔄 Archivos Creados/Modificados

### CliOrderWindowsBDD - Archivos Nuevos

| Archivo | Descripción | Estado |
|---------|-------------|--------|
| `src/main/java/com/bdd/Order.java` | ✅ Creado | Idéntico al original |
| `src/main/java/com/bdd/App.java` | ✅ Creado | Adaptado para Windows |
| `src/test/java/com/bdd/AppTest.java` | ✅ Creado | Idéntico al original |
| `pom.xml` | ✅ Actualizado | Agregadas dependencias |
| `README.md` | ✅ Creado | Documentación rápida |
| `INSTRUCCIONES.md` | ✅ Creado | Guía completa paso a paso |

## 🔍 Diferencias Principales

### 1. **Idioma de la Interfaz**

#### Linux (Original)
```
1) Take full order
2) Get full order
3) Modify order
A) About
X) Exit
```

#### Windows (Adaptado)
```
1) Tomar pedido completo
2) Ver pedido completo
3) Modificar pedido
A) Acerca de
X) Salir
```

### 2. **Comandos del Sistema**

#### Linux
```java
// Limpiar pantalla en Linux
new ProcessBuilder("clear").inheritIO().start().waitFor();
```

#### Windows
```java
// Limpiar pantalla en Windows
new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
```

### 3. **Versión de Java**

| Proyecto | Versión Java |
|----------|--------------|
| CliOrderLinuxBDD | Java 8 |
| CliOrderWindowsBDD | Java 17 |

### 4. **Soporte de Códigos ANSI**

#### Linux
- Soporte nativo de códigos ANSI en terminal

#### Windows
- Agregada función `enableWindowsAnsiSupport()` para compatibilidad
- Funciona mejor en Windows Terminal y PowerShell
- Fallback a secuencias ANSI si el comando falla

```java
static void enableWindowsAnsiSupport() {
    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("windows")) {
        try {
            new ProcessBuilder("cmd", "/c", "echo", "").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Los códigos ANSI aún pueden funcionar en terminales modernos
        }
    }
}
```

### 5. **Mensajes y Textos**

#### Ejemplos de Cambios de Idioma

| Linux (Inglés) | Windows (Español) |
|----------------|-------------------|
| "Take in restaurant or takeaway? (R/T)" | "¿Para comer en el restaurante o para llevar? (R/L)" |
| "Table number (0 if takeaway)" | "Número de mesa (0 si es para llevar)" |
| "Client's full name" | "Nombre completo del cliente" |
| "Payment method - Cash (C) or Card (T)" | "Método de pago - Efectivo (E) o Tarjeta (T)" |
| "Do you want invoice? (Y/N)" | "¿Desea factura? (S/N)" |

### 6. **Opciones de Entrada**

| Función | Linux | Windows |
|---------|-------|---------|
| Tipo de pedido | R/T (Restaurant/Takeaway) | R/L (Restaurante/Llevar) |
| Método de pago | C/T (Cash/Card) | E/T (Efectivo/Tarjeta) |
| Factura | Y/N (Yes/No) | S/N (Sí/No) |

### 7. **Configuración Maven (pom.xml)**

#### Linux
```xml
<configuration>
    <release>8</release>
</configuration>
```

#### Windows
```xml
<properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

## 📝 Funcionalidades Idénticas

Las siguientes funcionalidades permanecen **exactamente iguales** en ambas versiones:

✅ Validación de email con regex
✅ Cálculo de propinas (0, 5, 10, 15, 20%)
✅ Redondeo de decimales a 2 posiciones
✅ Gestión de pedidos (crear, ver, modificar)
✅ Estructura de la clase Order
✅ Lógica de negocio
✅ Validaciones de entrada
✅ Formato de fecha y hora
✅ Obtención de nombre del host
✅ Colores ANSI (mismos códigos)

## 🎨 Códigos ANSI Utilizados

Ambas versiones usan los mismos códigos ANSI:

```java
static final String ESC = "\u001B[";
static final String RESET = ESC + "0m";
static final String BOLD = ESC + "1m";
static final String BLINK = ESC + "5m";
static final String FG_GREEN_LIGHT = ESC + "38;5;120m";
static final String FG_ORANGE = ESC + "38;5;215m";
static final String FG_RED = ESC + "38;5;196m";
```

## 🚀 Cómo Ejecutar Cada Versión

### Linux
```bash
cd CliOrderLinuxBDD
mvn compile
java -cp target/classes com.bdd.App
```

### Windows
```bash
cd CliOrderWindowsBDD
mvn compile
java -cp target/classes com.bdd.App
```

## 📋 Checklist de Adaptación Realizada

- [x] Crear clase Order.java
- [x] Crear clase App.java con adaptaciones para Windows
- [x] Traducir todos los mensajes al español
- [x] Cambiar opciones de entrada (R/T → R/L, C/T → E/T, Y/N → S/N)
- [x] Adaptar comando de limpiar pantalla (clear → cls)
- [x] Agregar soporte mejorado para ANSI en Windows
- [x] Actualizar pom.xml con dependencias
- [x] Crear AppTest.java
- [x] Actualizar versión de Java (8 → 17)
- [x] Crear documentación (README.md, INSTRUCCIONES.md)
- [x] Compilar y probar el proyecto

## 🎯 Resultado Final

Ambos proyectos son **funcionalmente idénticos**, con las siguientes diferencias:

1. **Idioma:** Linux en inglés, Windows en español
2. **Sistema Operativo:** Optimizado para cada plataforma
3. **Versión Java:** Linux usa Java 8, Windows usa Java 17
4. **Documentación:** Windows incluye guías detalladas en español

## 💡 Recomendaciones de Uso

### Para Linux
- Usar terminal estándar de Linux
- Funciona en cualquier distribución moderna

### Para Windows
- **Recomendado:** Windows Terminal
- **Alternativa:** PowerShell
- **Funciona:** CMD de Windows 10/11
- **No recomendado:** CMD antiguo de Windows 7/8

## 📚 Archivos de Documentación

### CliOrderWindowsBDD
- `README.md` - Inicio rápido
- `INSTRUCCIONES.md` - Guía completa paso a paso
- `COMPARACION_PROYECTOS.md` - Este archivo

### CliOrderLinuxBDD
- Sin documentación adicional (proyecto original)

---

**Fecha de adaptación:** 2025
**Desarrollador original:** Blagovest Doukov Dimitrova
**Versión:** 1.0
