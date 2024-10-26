import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

// Creacion de Clase Padre (Canchas)
abstract class Cancha {
    protected String tipoCancha;
    protected String nombreCancha;

    public Cancha(String tipoCancha, String nombreCancha) {
        this.tipoCancha = tipoCancha;
        this.nombreCancha = nombreCancha;
    }

    public String getTipoCancha() {
        return tipoCancha;
    }

    public String getNombreCancha() {
        return nombreCancha;
    }

    public abstract String consultarDisponibilidad(LocalDate fecha, LocalTime hora, Calendario calendario);
}

// Creacion de Clase Hija (Canchas de Fútbol 5)
class CanchaFutbol5 extends Cancha {
    public CanchaFutbol5(String nombreCancha) {
        super("Fútbol 5", nombreCancha);
    }

    @Override
    public String consultarDisponibilidad(LocalDate fecha, LocalTime hora, Calendario calendario) {
        for (Turno turno : calendario.getTurnos()) {
            if (turno.getCancha().equals(this) && turno.getFecha().equals(fecha) && turno.getHora().equals(hora)) {
                return "La cancha " + nombreCancha + " NO está disponible en la fecha " + fecha + " a las " + hora + ".";
            }
        }
        return "La cancha " + nombreCancha + " está disponible en la fecha " + fecha + " a las " + hora + ".";
    }
}

// Creacion de Clase Hija (Canchas de Fútbol 8)
class CanchaFutbol8 extends Cancha {
    public CanchaFutbol8(String nombreCancha) {
        super("Fútbol 8", nombreCancha);
    }

    @Override
    public String consultarDisponibilidad(LocalDate fecha, LocalTime hora, Calendario calendario) {
        for (Turno turno : calendario.getTurnos()) {
            if (turno.getCancha().equals(this) && turno.getFecha().equals(fecha) && turno.getHora().equals(hora)) {
                return "La cancha " + nombreCancha + " NO está disponible en la fecha " + fecha + " a las " + hora + ".";
            }
        }
        return "La cancha " + nombreCancha + " está disponible en la fecha " + fecha + " a las " + hora + ".";
    }
}

// Creacion de Clase Turno
class Turno {
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private Cancha cancha;
    private int dniCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String telefonoCliente;

    public Turno(LocalDate fecha, LocalTime hora, String estado, Cancha cancha, int dniCliente, String nombreCliente, String apellidoCliente, String telefonoCliente) {
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.cancha = cancha;
        this.dniCliente = dniCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.telefonoCliente = telefonoCliente;
    }

    public int getDniCliente() {
        return dniCliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void mostrarDetalles() {
        System.out.println("-------------------");
        System.out.println("Detalles del Turno:");
        System.out.println("-------------------");
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora: " + hora);
        System.out.println("Estado: " + estado);
        System.out.println("Cancha: " + cancha.getNombreCancha());
        System.out.println("DNI Cliente: " + dniCliente);
        System.out.println("Nombre Cliente: " + nombreCliente);
        System.out.println("Apellido Cliente: " + apellidoCliente);
        System.out.println("Teléfono Cliente: " + telefonoCliente);
    }

    public void modificarTurno(LocalDate nuevaFecha, LocalTime nuevaHora) {
        this.fecha = nuevaFecha;
        this.hora = nuevaHora;
    }

    public void cancelarTurno() {
        this.estado = "Cancelado";
    }
}

// Creacion de Clase Calendario
class Calendario {
    private ArrayList<Turno> turnos;

    public Calendario() {
        this.turnos = new ArrayList<>();
    }

    public void agregarTurno(Turno turno) {
        turnos.add(turno);
    }

    public Turno buscarTurnoPorDNI(int dni) {
        for (Turno turno : turnos) {
            if (turno.getDniCliente() == dni) {
                return turno;
            }
        }
        return null; // Este return es utilizado si el turno no se encuentra con el DNI ingresado
    }

    public void eliminarTurno(Turno turno) {
        turnos.remove(turno);
    } // No lo pude implementar para el uso en Calendario de eliminar un turno.

    public ArrayList<Turno> getTurnos() {
        return turnos;
    }

    public void ordenarTurnosPorFecha() {
        Collections.sort(turnos, Comparator.comparing(Turno::getFecha).thenComparing(Turno::getHora));
    }
}

// Creacion de Clase Principal con el Menu
public class SistemaGestionTurnos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calendario calendario = new Calendario();

        // Crear un administrador con usuario y contraseñas predefenidos.
        Administrador admin = new Administrador("admin", "1234"); //Hago utilizacion de "admin" como usuario" y "1234" como contraseña

        // Solicitar usuario y contraseña para iniciar sesion
        boolean sesionIniciada = false;
        do {
            System.out.println("-------------------------------------------- ");
            System.out.println(" Bienvenidos al Sistema de Gestion de Turnos ");
            System.out.println("-------------------------------------------- ");
            System.out.println("  |       Iniciar Sesion        |  ");
            System.out.println("-------------------------------------------");
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();
            System.out.print("Contraseña: ");
            String contrasena = scanner.nextLine();

            if (admin.iniciarSesion(usuario, contrasena)) {
                sesionIniciada = true;
                System.out.println("Bienvenido, " + admin.getUsuario() + "!");
            } else {
                System.out.println("Credenciales incorrectas. Intente nuevamente.");
            }
        } while (!sesionIniciada);

        // Cantidad de Canchas que presenta el proyecto
        ArrayList<Cancha> canchas = new ArrayList<>();
        canchas.add(new CanchaFutbol5("Cancha N°1"));
        canchas.add(new CanchaFutbol5("Cancha N°2"));
        canchas.add(new CanchaFutbol5("Cancha N°3"));
        canchas.add(new CanchaFutbol5("Cancha N°4"));
        canchas.add(new CanchaFutbol5("Cancha N°5"));
        canchas.add(new CanchaFutbol8("Cancha N°1"));
        canchas.add(new CanchaFutbol8("Cancha N°2"));

        int opcion;
        do {
            System.out.println("---------------------------------");
            System.out.println("\nSistema de Gestión de Turnos");
            System.out.println("---------------------------------");
            System.out.println("1. Reservar Turno");
            System.out.println("2. Modificar Turno");
            System.out.println("3. Cancelar Turno");
            System.out.println("4. Consultar Disponibilidad de una Cancha");
            System.out.println("5. Mostrar Detalles del Turno");
            System.out.println("6. Salir");

            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            switch (opcion) {
                case 1: // Reservar Turno
                    System.out.println("\n------------------");
                    System.out.println("     CANCHAS     ");
                    System.out.println("------------------");
                    System.out.println("1 - Fútbol 5");
                    System.out.println("2 - Fútbol 8");
                    int tipoCancha = scanner.nextInt();
                    scanner.nextLine();

                    // Mostrar canchas según el tipo elegido
                    ArrayList<Cancha> canchasDisponibles = new ArrayList<>();
                    for (Cancha cancha : canchas) {
                        if ((tipoCancha == 1 && cancha.getTipoCancha().equals("Fútbol 5")) ||
                                (tipoCancha == 2 && cancha.getTipoCancha().equals("Fútbol 8"))) {
                            canchasDisponibles.add(cancha);
                        }
                    }

                    System.out.println("\nSeleccione la cancha:");
                    for (int i = 0; i < canchasDisponibles.size(); i++) {
                        System.out.println((i + 1) + ". " + canchasDisponibles.get(i).getNombreCancha());
                    }
                    int seleccionCancha = scanner.nextInt();
                    scanner.nextLine();

                    // Obtener fecha y hora
                    LocalDate fecha = null;
                    LocalTime hora = null;

                    // Uso de Excepciones para fecha
                    while (fecha == null) {
                        System.out.print("Ingrese la fecha (dd-MM-yyyy): ");
                        String fechaStr = scanner.nextLine();
                        try {
                            fecha = LocalDate.parse(fechaStr, dateFormatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Fecha inválida. Intente nuevamente.");
                        }
                    }

                    // Uso de Excepciones para hora
                    while (hora == null) {
                        System.out.print("Ingrese la hora (HH:mm): ");
                        String horaStr = scanner.nextLine();
                        try {
                            hora = LocalTime.parse(horaStr, timeFormatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Hora inválida. Intente nuevamente.");
                        }
                    }

                    // Verificar si el turno ya existe
                    Cancha canchaSeleccionada = canchasDisponibles.get(seleccionCancha - 1);
                    boolean turnoDuplicado = false;
                    for (Turno turnoExistente : calendario.getTurnos()) {
                        if (turnoExistente.getCancha().equals(canchaSeleccionada) &&
                                turnoExistente.getFecha().equals(fecha) &&
                                turnoExistente.getHora().equals(hora)) {
                            turnoDuplicado = true;
                            break;
                        }
                    }

                    if (turnoDuplicado) {
                        System.out.println("Error: Ya existe un turno reservado para la cancha " + canchaSeleccionada.getNombreCancha() +
                                " el " + fecha + " a las " + hora + ".");
                    } else {
                        // Crear el turno si no hay duplicados
                        System.out.print("Ingrese el DNI del cliente: ");
                        int dniCliente = scanner.nextInt();
                        scanner.nextLine(); // Consumir el salto de línea
                        System.out.print("Ingrese el nombre del cliente: ");
                        String nombreCliente = scanner.nextLine();
                        System.out.print("Ingrese el apellido del cliente: ");
                        String apellidoCliente = scanner.nextLine();
                        System.out.print("Ingrese el teléfono del cliente: ");
                        String telefonoCliente = scanner.nextLine();

                        Turno nuevoTurno = new Turno(fecha, hora, "Reservado", canchaSeleccionada, dniCliente, nombreCliente, apellidoCliente, telefonoCliente);
                        calendario.agregarTurno(nuevoTurno);
                        System.out.println("Turno reservado exitosamente.");
                    }
                    break;


                case 2: // Modificar Turno
                    System.out.print("Ingrese el DNI del cliente para modificar el turno: ");
                    int dniModificar = scanner.nextInt();
                    Turno turnoModificar = calendario.buscarTurnoPorDNI(dniModificar);
                    if (turnoModificar != null) {
                        System.out.println("Turno encontrado.");
                        turnoModificar.mostrarDetalles();

                        LocalDate nuevaFecha = null;
                        LocalTime nuevaHora = null;

                        // Uso de Excepciones para la nueva fecha
                        while (nuevaFecha == null) {
                            System.out.print("Ingrese la nueva fecha (dd-MM-yyyy): ");
                            String nuevaFechaStr = scanner.nextLine();
                            try {
                                nuevaFecha = LocalDate.parse(nuevaFechaStr, dateFormatter);
                            } catch (DateTimeParseException e) {
                                System.out.println("Fecha inválida. Intente nuevamente.");
                            }
                        }

                        // Uso de Excepciones para la nueva hora
                        while (nuevaHora == null) {
                            System.out.print("Ingrese la nueva hora (HH:mm): ");
                            String nuevaHoraStr = scanner.nextLine();
                            try {
                                nuevaHora = LocalTime.parse(nuevaHoraStr, timeFormatter);
                            } catch (DateTimeParseException e) {
                                System.out.println("Hora inválida. Intente nuevamente.");
                            }
                        }

                        turnoModificar.modificarTurno(nuevaFecha, nuevaHora);
                        System.out.println("Turno modificado exitosamente.");
                    } else {
                        System.out.println("No se encontró un turno con ese DNI.");
                    }
                    break;

                case 3: // Cancelar Turno
                    System.out.print("Ingrese el DNI del cliente para cancelar el turno: ");
                    int dniCancelar = scanner.nextInt();
                    Turno turnoCancelar = calendario.buscarTurnoPorDNI(dniCancelar);
                    if (turnoCancelar != null) {
                        turnoCancelar.cancelarTurno();
                        System.out.println("Turno cancelado exitosamente.");
                    } else {
                        System.out.println("No se encontró un turno con ese DNI.");
                    }
                    break;

                case 4: // Consultar Disponibilidad de una Cancha
                    System.out.println("\nSeleccione la cancha para consultar disponibilidad:");
                    for (int i = 0; i < canchas.size(); i++) {
                        System.out.println((i + 1) + ". " + canchas.get(i).getNombreCancha());
                    }
                    int seleccionCanchaConsulta = scanner.nextInt();
                    scanner.nextLine();

                    Cancha canchaConsulta = canchas.get(seleccionCanchaConsulta - 1);

                    // Obtener fecha y hora
                    LocalDate fechaConsulta = null;
                    LocalTime horaConsulta = null;

                    // Uso de Excepciones para la fecha
                    while (fechaConsulta == null) {
                        System.out.print("Ingrese la fecha (dd-MM-yyyy): ");
                        String fechaConsultaStr = scanner.nextLine();
                        try {
                            fechaConsulta = LocalDate.parse(fechaConsultaStr, dateFormatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Fecha inválida. Intente nuevamente.");
                        }
                    }

                    // Uso de Excepciones para la hora
                    while (horaConsulta == null) {
                        System.out.print("Ingrese la hora (HH:mm): ");
                        String horaConsultaStr = scanner.nextLine();
                        try {
                            horaConsulta = LocalTime.parse(horaConsultaStr, timeFormatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Hora inválida. Intente nuevamente.");
                        }
                    }

                    String disponibilidad = canchaConsulta.consultarDisponibilidad(fechaConsulta, horaConsulta, calendario);
                    System.out.println(disponibilidad);
                    break;

                case 5: // Mostrar Detalles del Turno
                    System.out.print("Ingrese el DNI del cliente para mostrar detalles del turno: ");
                    int dniDetalles = scanner.nextInt();
                    Turno turnoDetalles = calendario.buscarTurnoPorDNI(dniDetalles);
                    if (turnoDetalles != null) {
                        turnoDetalles.mostrarDetalles();
                    } else {
                        System.out.println("No se encontró un turno con ese DNI.");
                    }
                    break;

                case 6: // Salir
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

            // Ordenar turnos por fecha
            calendario.ordenarTurnosPorFecha();

        } while (opcion != 6);

        scanner.close();
    }
}

// Clase Administrador para gestionar la sesión
class Administrador {
    private String usuario;
    private String contrasena;

    public Administrador(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public boolean iniciarSesion(String usuario, String contrasena) {
        return this.usuario.equals(usuario) && this.contrasena.equals(contrasena);
    }
}
