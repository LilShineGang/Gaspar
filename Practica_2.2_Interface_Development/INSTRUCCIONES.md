# Instrucciones para CliOrderWindowsBDD

## 📋 Descripción del Proyecto

Este proyecto es una aplicación de gestión de pedidos para restaurante, adaptada específicamente para Windows. Es una réplica del proyecto CliOrderLinuxBDD pero optimizada para el sistema operativo Windows.

## 🎯 ¿Qué hace la aplicación?

La aplicación permite:
- ✅ Tomar pedidos completos de clientes
- ✅ Ver detalles de pedidos
- ✅ Modificar pedidos existentes
- ✅ Gestionar información como: nombre del cliente, email, mesa, método de pago, propinas, etc.
- ✅ Interfaz de consola con colores (códigos ANSI)

## 📁 Estructura del Proyecto

```
CliOrderWindowsBDD/
├── pom.xml                          # Configuración de Maven
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── bdd/
│   │               ├── App.java     # Aplicación principal
│   │               ├── Order.java   # Clase modelo para pedidos
│   │               └── Main.java    # (Archivo original, no se usa)
│   └── test/
│       └── java/
│           └── com/
│               └── bdd/
│                   └── AppTest.java # Pruebas unitarias
└── target/                          # Archivos compilados (generado por Maven)
```

## 🔧 Requisitos Previos

1. **Java JDK 17 o superior** instalado
   - Verifica con: `java -version`
   
2. **Apache Maven** instalado
   - Verifica con: `mvn -version`

3. **Terminal compatible con códigos ANSI** (recomendado):
   - Windows Terminal (recomendado)
   - PowerShell
   - CMD moderno de Windows 10/11

## 📝 Paso a Paso: ¿Qué se hizo?

### Paso 1: Crear la clase Order.java
**Ubicación:** `src/main/java/com/bdd/Order.java`

**¿Qué es?** Clase modelo que representa un pedido con todos sus atributos:
- Fecha y hora del pedido
- Tipo (para comer en restaurante o para llevar)
- Número de mesa
- Nombre del cliente
- Email
- Comentarios
- Total, propina, método de pago, etc.

### Paso 2: Crear la clase App.java
**Ubicación:** `src/main/java/com/bdd/App.java`

**¿Qué es?** La aplicación principal con:
- Menú interactivo
- Funciones para tomar, ver y modificar pedidos
- Validaciones de entrada
- Interfaz con colores usando códigos ANSI
- **Adaptaciones para Windows:**
  - Comando `cls` para limpiar pantalla
  - Soporte mejorado para códigos ANSI en Windows
  - Mensajes en español

### Paso 3: Crear la clase AppTest.java
**Ubicación:** `src/test/java/com/bdd/AppTest.java`

**¿Qué es?** Pruebas unitarias básicas usando JUnit 3.8.1

### Paso 4: Actualizar pom.xml
**Ubicación:** `pom.xml`

**¿Qué se agregó?**
- Dependencia de JUnit para pruebas
- Configuración de empaquetado JAR
- Codificación UTF-8
- Configuración para Java 17

## 🚀 Cómo Compilar el Proyecto

### Opción 1: Compilar con Maven
```bash
cd CliOrderWindowsBDD
mvn compile
```

### Opción 2: Compilar con pruebas
```bash
cd CliOrderWindowsBDD
mvn test-compile
```

### Opción 3: Empaquetar en JAR
```bash
cd CliOrderWindowsBDD
mvn package
```

## ▶️ Cómo Ejecutar la Aplicación

### Método 1: Ejecutar directamente con Java
```bash
cd CliOrderWindowsBDD
java -cp target/classes com.bdd.App
```

### Método 2: Ejecutar el JAR (después de `mvn package`)
```bash
cd CliOrderWindowsBDD
java -jar target/cliorderwindowsbdd-1.0-SNAPSHOT.jar
```

### Método 3: Usar Maven exec plugin
Primero agrega esto al `pom.xml` dentro de `<build><plugins>`:
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <mainClass>com.bdd.App</mainClass>
    </configuration>
</plugin>
```

Luego ejecuta:
```bash
mvn exec:java
```

## 🎮 Cómo Usar la Aplicación

### Menú Principal
Al ejecutar verás:
```
== DanDelilght Restaurant v1.0 ==
1) Tomar pedido completo
2) Ver pedido completo
3) Modificar pedido
A) Acerca de
X) Salir
Elige una opción:
```

### 1. Tomar Pedido Completo
Te pedirá:
1. **Tipo de pedido:** R (Restaurante) o L (Llevar)
2. **Número de mesa:** 0 si es para llevar, >0 si es en restaurante
3. **Nombre completo del cliente:** Obligatorio
4. **Email:** Opcional, con validación
5. **Comentarios:** Opcional
6. **Total en €:** Obligatorio
7. **Método de pago:** E (Efectivo) o T (Tarjeta)
8. **Propina:** 0, 5, 10, 15 o 20%
9. **¿Desea factura?:** S (Sí) o N (No)

### 2. Ver Pedido Completo
Muestra todos los detalles del pedido actual.

### 3. Modificar Pedido
Permite cambiar cualquier campo del pedido existente.

### A. Acerca de
Muestra información del desarrollador y versión.

### X. Salir
Cierra la aplicación.

## 🎨 Características Especiales para Windows

### Códigos ANSI y Colores
La aplicación usa códigos ANSI para mostrar colores:
- **Verde claro:** Texto normal
- **Naranja:** Acentos y etiquetas
- **Rojo:** Mensajes de error
- **Parpadeo:** Mensajes importantes

**Nota:** Para ver los colores correctamente, usa:
- Windows Terminal (recomendado)
- PowerShell
- CMD de Windows 10/11

### Limpiar Pantalla
La aplicación usa el comando `cls` nativo de Windows para limpiar la pantalla.

## 🔍 Diferencias con la Versión Linux

| Aspecto | Linux | Windows |
|---------|-------|---------|
| Comando limpiar pantalla | `clear` | `cls` |
| Mensajes | Inglés | Español |
| Versión Java | 8 | 17 |
| Soporte ANSI | Nativo | Requiere terminal moderno |
| Opciones menú | R/T | R/L (Restaurante/Llevar) |
| Método pago | C/T | E/T (Efectivo/Tarjeta) |

## 🧪 Ejecutar Pruebas

```bash
cd CliOrderWindowsBDD
mvn test
```

## 📦 Empaquetar para Distribución

```bash
cd CliOrderWindowsBDD
mvn clean package
```

Esto creará un archivo JAR en `target/cliorderwindowsbdd-1.0-SNAPSHOT.jar`

## 🛠️ Solución de Problemas

### Problema: No se ven los colores
**Solución:** Usa Windows Terminal o PowerShell en lugar de CMD antiguo.

### Problema: Error al compilar
**Solución:** Verifica que tienes Java 17+ y Maven instalados correctamente.

### Problema: No se puede limpiar el directorio target
**Solución:** Cierra cualquier terminal o IDE que esté usando archivos del proyecto, luego ejecuta:
```bash
mvn clean
```

### Problema: Caracteres extraños en lugar de colores
**Solución:** Asegúrate de que tu terminal soporte UTF-8 y códigos ANSI.

## 📚 Recursos Adicionales

- [Documentación de Maven](https://maven.apache.org/guides/)
- [Java 17 Documentation](https://docs.oracle.com/en/java/javase/17/)
- [Windows Terminal](https://aka.ms/terminal)

## 👨‍💻 Autor

**Blagovest Doukov Dimitrova**
- Versión: 1.0
- Proyecto: DanDelilght Restaurant

## 📄 Licencia

Este es un proyecto educativo para práctica de BDD (Behavior-Driven Development).

---

**¡Disfruta usando la aplicación! 🎉**
