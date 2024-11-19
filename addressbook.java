import java.util.Scanner;

class AddressBook {
    private static Scanner scanner = new Scanner(System.in);
    private static Contact[] contacts = new Contact[100];
    private static int count = 0;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nAddress Book Menu:");
            System.out.println("a) Create Address Book");
            System.out.println("b) View Address Book");
            System.out.println("c) Insert a Record");
            System.out.println("d) Delete a Record");
            System.out.println("e) Modify a Record");
            System.out.println("f) Exit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice.toLowerCase()) {
                case "a":
                    createAddressBook();
                    break;
                case "b":
                    viewAddressBook();
                    break;
                case "c":
                    insertRecord();
                    break;
                case "d":
                    deleteRecord();
                    break;
                case "e":
                    modifyRecord();
                    break;
                case "f":
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Creates a new empty address book (array of contacts)
    private static void createAddressBook() {
        System.out.println("Address Book created.");
        count = 0; // Reset the count when a new address book is created
    }

    // Views all contacts in the address book
    private static void viewAddressBook() {
        if (count == 0) {
            System.out.println("No records found in the address book.");
        } else {
            System.out.println("Address Book:");
            for (int i = 0; i < count; i++) {
                contacts[i].display();
            }
        }
    }

    // Inserts a new record in the address book
    private static void insertRecord() {
        if (count < contacts.length) {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            Contact newContact = new Contact(name, phone, email);
            contacts[count] = newContact;
            count++;

            System.out.println("Record inserted successfully.");
        } else {
            System.out.println("Address book is full, cannot insert more records.");
        }
    }

    // Deletes a record by the name
    private static void deleteRecord() {
        if (count == 0) {
            System.out.println("No records to delete.");
            return;
        }

        System.out.print("Enter Name of the contact to delete: ");
        String nameToDelete = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (contacts[i].getName().equalsIgnoreCase(nameToDelete)) {
                // Shift elements to remove the contact
                for (int j = i; j < count - 1; j++) {
                    contacts[j] = contacts[j + 1];
                }
                contacts[count - 1] = null; // Nullify the last element
                count--;
                found = true;
                System.out.println("Record deleted successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("No contact found with that name.");
        }
    }

    // Modifies an existing record
    private static void modifyRecord() {
        if (count == 0) {
            System.out.println("No records to modify.");
            return;
        }

        System.out.print("Enter Name of the contact to modify: ");
        String nameToModify = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (contacts[i].getName().equalsIgnoreCase(nameToModify)) {
                System.out.println("Record found. Modify details:");

                System.out.print("Enter new Name (or press Enter to keep existing): ");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) contacts[i].setName(newName);

                System.out.print("Enter new Phone Number (or press Enter to keep existing): ");
                String newPhone = scanner.nextLine();
                if (!newPhone.isEmpty()) contacts[i].setPhone(newPhone);

                System.out.print("Enter new Email (or press Enter to keep existing): ");
                String newEmail = scanner.nextLine();
                if (!newEmail.isEmpty()) contacts[i].setEmail(newEmail);

                System.out.println("Record modified successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No contact found with that name.");
        }
    }

    // Contact class to store contact details
    static class Contact {
        private String name;
        private String phone;
        private String email;

        public Contact(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void display() {
            System.out.println("Name: " + name + ", Phone: " + phone + ", Email: " + email);
        }
    }
}