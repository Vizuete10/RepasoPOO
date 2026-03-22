package retoNormal;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ListaVehiculos lista = new ListaVehiculos();
        int opcion;

        do {
            System.out.println("\n====== GESTIÓN DE ALQUILER DE VEHÍCULOS ======");
            System.out.println("1. Añadir vehículo");
            System.out.println("2. Listar vehículos");
            System.out.println("3. Buscar vehículo por matrícula");
            System.out.println("4. Modificar días alquilados");
            System.out.println("5. Modificar recargo premium");
            System.out.println("6. Modificar precio seguro diario");
            System.out.println("7. Eliminar vehículo");
            System.out.println("8. Ver estadísticas");
            System.out.println("9. Salir");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine(); //  limpiar buffer tras nextInt()

            switch (opcion) {

                case 1: // ── Añadir vehículo ──────────────────────────────────
                    System.out.print("Matrícula: ");
                    String mat = sc.nextLine().trim().toUpperCase();

                    if (lista.buscarPorMatricula(mat) != null) {
                        System.out.println("ERROR: esa matrícula ya existe.");
                        break;
                    }

                    System.out.print("Modelo: ");
                    String modelo = sc.nextLine();

                    System.out.print("Precio base por día: ");
                    double precio = sc.nextDouble();
                    sc.nextLine();

                    double recargo = -1;
                    while (recargo < 0 || recargo > 25) {
                        System.out.print("Porcentaje recargo premium (0-25): ");
                        recargo = sc.nextDouble();
                        sc.nextLine();
                        if (recargo < 0 || recargo > 25)
                            System.out.println("ERROR: debe estar entre 0 y 25.");
                    }

                    Vehiculo nuevo = new Vehiculo(mat, modelo, precio, recargo);
                    if (lista.anadirVehiculo(nuevo)) {
                        System.out.println("✔ Vehículo añadido correctamente.");
                    }
                    break;

                case 2: // ── Listar todos ──────────────────────────────────────
                    lista.listarTodos();
                    break;

                case 3: // ── Buscar pro matrícula ──────────────────────────────
                    System.out.print("Matrícula a buscar: ");
                    String buscar = sc.nextLine().trim().toUpperCase();
                    Vehiculo encontrado = lista.buscarPorMatricula(buscar);
                    if (encontrado != null) {
                        System.out.println(encontrado);
                    } else {
                        System.out.println("Vehículo no encontrado.");
                    }
                    break;

                case 4: // ── Modificar días alquilados ─────────────────────────
                    System.out.print("Matrícula: ");
                    String matDias = sc.nextLine().trim().toUpperCase();
                    System.out.print("Nuevos días alquilados: ");
                    int dias = sc.nextInt();
                    sc.nextLine();
                    if (lista.modificarDiasAlquilados(matDias, dias)) {
                        System.out.println("✔ Días actualizados correctamente.");
                    } else {
                        System.out.println("ERROR: vehículo no encontrado.");
                    }
                    break;

                case 5: // ── Modificar recargo premium ─────────────────────────
                    System.out.print("Matrícula: ");
                    String matRecargo = sc.nextLine().trim().toUpperCase();
                    if (lista.buscarPorMatricula(matRecargo) == null) {
                        System.out.println("ERROR: vehículo no encontrado.");
                        break;
                    }
                    double nuevoRecargo = -1;
                    while (nuevoRecargo < 0 || nuevoRecargo > 25) {
                        System.out.print("Nuevo porcentaje (0-25): ");
                        nuevoRecargo = sc.nextDouble();
                        sc.nextLine();
                        if (nuevoRecargo < 0 || nuevoRecargo > 25)
                            System.out.println("ERROR: debe estar entre 0 y 25.");
                    }
                    lista.modificarRecargoPremium(matRecargo, nuevoRecargo);
                    System.out.println("✔ Recargo premium actualizado.");
                    break;

                case 6: // ── Modificar precio seguro diario (estático) ─────────
                    System.out.print("Nuevo precio del seguro diario: ");
                    double nuevoSeguro = sc.nextDouble();
                    sc.nextLine();
                    Vehiculo.setPrecioSeguroDiario(nuevoSeguro);
                    System.out.println("✔ Precio del seguro actualizado para todos los vehículos.");
                    break;

                case 7: // ── Eliminar vehículo ─────────────────────────────────
                    System.out.print("Matrícula a eliminar: ");
                    String matElim = sc.nextLine().trim().toUpperCase();
                    if (lista.eliminarPorMatricula(matElim)) {
                        System.out.println("✔ Vehículo eliminado correctamente.");
                    } else {
                        System.out.println("ERROR: vehículo no encontrado.");
                    }
                    break;

                case 8: // ── Ver estadísticas ──────────────────────────────────
                    System.out.println("\n--- ESTADÍSTICAS ---");
                    System.out.printf("Ingreso total por recargos premium: %.2f €%n",
                                      lista.calcularIngresoTotalRecargos());
                    System.out.println("\nVehículos premium (recargo > 12%):");
                    lista.listarVehiculosPremium();
                    break;

                case 9: // ── Salir ─────────────────────────────────────────────
                    System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
                    break;

                default:
                    System.out.println("Opción no válida. Elige entre 1 y 9.");
            }

        } while (opcion != 9);

        sc.close();
    }
}