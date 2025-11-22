# 🚀 Guía Rápida - CliOrderWindowsBDD

## ⚡ Ejecución en 3 Pasos

### Paso 1️⃣: Abrir Terminal
Abre **Windows Terminal** o **PowerShell** en la carpeta del proyecto:
```bash
cd C:\Users\shiny\Desktop\Practica_2.2\Practica 2.2\CliOrderWindowsBDD
```

### Paso 2️⃣: Compilar
```bash
mvn compile
```
✅ Deberías ver: `BUILD SUCCESS`

### Paso 3️⃣: Ejecutar
```bash
java -cp target/classes com.bdd.App
```
🎉 ¡La aplicación se iniciará!

---

## 📖 Ejemplo de Uso Completo

### 1. Pantalla de Inicio
```
Bienvenido a DanDelilght Restaurant
Desarrollador: Blagovest Doukov Dimitrova
Sistema Operativo: Windows 11
Fecha y hora: 2025-01-12 23:06:00
Nombre del PC: TU-PC
Usuario: shiny

Presiona cualquier tecla o espera 10 segundos para continuar...
```

### 2. Menú Principal
```
== DanDelilght Restaurant v1.0 ==
1) Tomar pedido completo
2) Ver pedido completo
3) Modificar pedido
A) Acerca de
X) Salir
Elige una opción: _
```

### 3. Ejemplo: Tomar un Pedido

**Escribe:** `1` y presiona ENTER

```
¿Para comer en el restaurante o para llevar? (R/L): R
Número de mesa (0 si es para llevar): 5
Nombre completo del cliente: Juan Pérez García
Email (ENTER si no desea proporcionar email): juan@email.com
Comentarios del cliente (opcional): Sin cebolla por favor
Total (€): 45.50
Método de pago - Efectivo (E) o Tarjeta (T): T
Propina (0,5,10,15,20) [0]: 10
¿Desea factura? (S/N) [N]: S

[OK] Pedido tomado correctamente.
```

### 4. Ver el Pedido

**Escribe:** `2` y presiona ENTER

```
== DanDelilght Restaurant v1.0 ==

Fecha y hora: 2025-01-12 23:10:30
Tipo: En restaurante
Mesa: 5
Cliente: Juan Pérez García
Email: juan@email.com
Comentarios: Sin cebolla por favor
Total: € 45.50
Propina: 10% (€ 4.55)
Total con propina: € 50.05
Pago: Tarjeta
Factura solicitada: Sí

Presiona ENTER para continuar...
```

### 5. Modificar el Pedido

**Escribe:** `3` y presiona ENTER

```
== DanDelilght Restaurant v1.0 ==

1) Tipo (actual: Restaurante)
2) Mesa (actual: 5)
3) Nombre (actual: Juan Pérez García)
4) Email (actual: juan@email.com)
5) Comentarios (actual: Sin cebolla por favor)
6) Total (actual: € 45.50)
7) Pago (actual: Tarjeta)
8) Propina (actual: 10% )
9) Factura (actual: Sí)
0) Volver
Elige uno para modificar: _
```

**Ejemplo:** Cambiar la propina
- Escribe: `8`
- Propina (0,5,10,15,20): `15`
- ¡La propina se actualiza automáticamente!

### 6. Salir

**Escribe:** `X` y presiona ENTER

```
¡Gracias por usar DanDelilght Restaurant!
¡Que tengas un buen día!
```

---

## 🎯 Comandos Útiles

### Compilar el proyecto
```bash
mvn compile
```

### Compilar con pruebas
```bash
mvn test
```

### Limpiar y compilar
```bash
mvn clean compile
```

### Crear archivo JAR
```bash
mvn package
```

### Ejecutar el JAR
```bash
java -jar target/cliorderwindowsbdd-1.0-SNAPSHOT.jar
```

---

## 🔧 Solución Rápida de Problemas

### ❌ Error: "mvn no se reconoce como comando"
**Solución:** Maven no está instalado o no está en el PATH
```bash
# Verifica la instalación
mvn -version
```

### ❌ Error: "java no se reconoce como comando"
**Solución:** Java no está instalado o no está en el PATH
```bash
# Verifica la instalación
java -version
```

### ❌ No se ven los colores
**Solución:** Usa Windows Terminal en lugar de CMD antiguo
- Descarga: https://aka.ms/terminal

### ❌ Error al compilar: "Failed to delete target"
**Solución:** Cierra todos los programas que usen el proyecto
```bash
# Luego intenta de nuevo
mvn compile
```

---

## 📱 Atajos de Teclado

| Tecla | Acción |
|-------|--------|
| `1` | Tomar nuevo pedido |
| `2` | Ver pedido actual |
| `3` | Modificar pedido |
| `A` | Ver información |
| `X` | Salir |
| `ENTER` | Continuar |

---

## 💡 Consejos

1. **Usa Windows Terminal** para mejor experiencia visual
2. **Lee los mensajes de error** - son descriptivos
3. **Los campos opcionales** se pueden dejar vacíos con ENTER
4. **La propina se calcula automáticamente** según el porcentaje
5. **Puedes modificar cualquier campo** después de crear el pedido

---

## 📞 ¿Necesitas Más Ayuda?

- 📖 **Guía completa:** Lee `INSTRUCCIONES.md`
- 🔍 **Comparación con Linux:** Lee `COMPARACION_PROYECTOS.md` (en carpeta padre)
- 📝 **Inicio rápido:** Lee `README.md`

---

## ✅ Checklist de Verificación

Antes de ejecutar, verifica:
- [ ] Java 17+ instalado (`java -version`)
- [ ] Maven instalado (`mvn -version`)
- [ ] Estás en la carpeta CliOrderWindowsBDD
- [ ] Has compilado el proyecto (`mvn compile`)
- [ ] Usas Windows Terminal o PowerShell

---

**¡Listo para empezar! 🎉**

Ejecuta: `java -cp target/classes com.bdd.App`
