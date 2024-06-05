import java.io.*;
import java.util.HashMap; //Importamos Hashmap
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private HashMap<String, String> contacts;

    public AddressBook() { //Usaremos Hashmap para lograr el listado editable junto con un FileReader.
        contacts = new HashMap<>();
    }
    //Se intentarán CARGAR y GUARDAR y la función try-catch para cualquier error que pudiera generarse.
    public void load(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
               if (parts.length == 2) {
                    contacts.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudieron obtener los contactos: " + e.getMessage());
        }
    }
    public void Actualizarcontcto(String viejonumero, String nuevonumero, String nuevonombre) {
        if (contacts.containsKey(viejonumero)) {
            contacts.remove(viejonumero);
            contacts.put(nuevonumero, nuevonombre);
            System.out.println("Contacto actualizado.");
        } else {
            System.out.println("Contacto no encontrado.");
        }
    }

    public void save(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("No se pudieron guardar los contactos" + e.getMessage());
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void create(String number, String name) {
        contacts.put(number, name); //ingresamos el ingresar del Hashmap, el contacto
    }

    public void delete(String number) {
        contacts.remove(number); //llamamos remover el Hashmap borrar de contactos
    }

    public static void main(String[] args) { //cargamos el listado
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();
        String filename = "contacts.txt";

        addressBook.load(filename);

        while (true) {
            System.out.println("\nMenú Interacción");
            System.out.println("1. Contactos BD");
            System.out.println("2. Crear nuevo");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Guardar y salir"); //guarda y sale, antes del buff read
            System.out.println("5. Actualizar Contacto"); // Se agrega modificación contacto
            System.out.print("Seleccione una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Ingrese el número telefónico: ");
                    String number = scanner.nextLine();
                    System.out.print("Ingrese el nombre del contacto: ");
                    String name = scanner.nextLine();
                    addressBook.create(number, name);
                    break;
                case 3:
                    System.out.print("Ingrese el número telefónico a borrar: ");
                    number = scanner.nextLine();
                    addressBook.delete(number);
                    break;
                case 4:
                    addressBook.save(filename);
                    System.out.println("Contactos guardados. Saliendo...");
                    return;
                case 5:
                    System.out.print("Ingrese el número telefónico actual: ");
                    String viejonumero = scanner.nextLine();
                    System.out.print("Ingrese el nuevo número telefónico: ");
                    String nuevonum = scanner.nextLine();
                    System.out.print("Ingrese el nuevo nombre del contacto: ");
                    String nuevonombre = scanner.nextLine();
                    addressBook.Actualizarcontcto(viejonumero, nuevonum, nuevonombre);
                    break;
                default:
                    System.out.println("Ingrese una opción válida.");
            }
        }
    }
}
