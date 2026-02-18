package main;

import java.util.ArrayList;
import model.Product;
import model.Sale;
import java.util.Scanner;
import model.Amount;
import model.Client;
import model.Employee;

public class Shop {

    private Amount cash = new Amount(100.00);
    private static ArrayList<Product> inventory = new ArrayList<>();
    private int numberProducts;
    //private Sale[] sales;
    private static ArrayList<Sale> sales = new ArrayList<>();
    int counterSales = 0;
    private static Employee empleado;

    final static double TAX_RATE = 1.04;

    public Shop() {

    }

    public Amount getCash() {
        return cash;
    }

    public void setCash(Amount cash) {
        this.cash = cash;
    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        if (!shop.initSession()) {

            return;
        }
        shop.loadInventory();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        boolean exit = false;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) A\u00f1adir producto");
            System.out.println("3) A\u00f1adir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver total Ventas");
            System.out.println("9) Eliminar Producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;
                case 8:
                    shop.amountSales();
                    break;

                case 9:
                    shop.removeProduct();
                    break;

                case 10:
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory() {
        addProduct(new Product("Manzana", 10.00, true, 10));
        addProduct(new Product("Pera", 20.00, true, 20));
        addProduct(new Product("Hamburguesa", 30.00, true, 30));
        addProduct(new Product("Fresa", 5.00, true, 20));
    }

    /**
     * show current total cash
     */
    private void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct() {
        if (isInventoryFull()) {
            System.out.println("No se pueden a\u00f1adir mas productos");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        if (findProduct(name) == null) {
            System.out.print("Precio mayorista: ");
            double wholesalerPrice = scanner.nextDouble();
            System.out.print("Stock: ");
            int stock = scanner.nextInt();

            addProduct(new Product(name, wholesalerPrice, true, stock));
        } else {
            System.out.println("Ya existe el producto");
        }
    }

    /**
     * add stock for a specific product
     */
    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.nextLine();
        Product product = findProduct(name);

        if (product != null) {
            System.out.print("Seleccione la cantidad a a\u00f1adir: ");
            int stock = scanner.nextInt();
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());
        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    /**
     * set a product as expired
     */
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);
        if (product != null) {
            product.expire();
            System.out.println("El precio público del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale() {
        ArrayList<Product> shoppingcart = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.println("Realizar venta, escribir nombre cliente");
        String cliente = sc.nextLine();

        // CAMBIO: usar Amount para totalAmount
        Amount totalAmount = new Amount(0.0);
        String name = "";

        while (!name.equals("0")) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            name = sc.nextLine();

            if (name.equals("0")) {
                break;
            }
            Product product = findProduct(name);
            boolean productAvailable = false;

            if (product != null && product.isAvailable()) {
                productAvailable = true;
                totalAmount.setValue(totalAmount.getValue() + product.getPublicPrice().getValue());
                product.setStock(product.getStock() - 1);
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                //shoppingcart = product;
                shoppingcart.add(product);

                System.out.println("Producto añadido con éxito");
            }

            if (!productAvailable) {
                System.out.println("Producto no encontrado o sin stock");
            }
        }

        // aplicar impuesto
        totalAmount.setValue(totalAmount.getValue() * TAX_RATE);

        Client client = new Client(cliente);

        boolean paid = client.pay(totalAmount);

        if (paid) {
            cash.setValue(cash.getValue() + totalAmount.getValue());
            System.out.println("Venta realizada con éxito, total: " + totalAmount);
        } else {
            System.out.println("El cliente no ha podido pagar. Cantidad a deber: " + totalAmount);
        }

// guardar la venta
        sales.add(counterSales, new Sale(client, shoppingcart, totalAmount));
        counterSales++;
    }

    /**
     * show all sales
     */
    private void showSales() {
        System.out.println("Lista de ventas:");
        if (sales != null) {
            for (Sale sale : sales) {
                if (sale != null) {
                    System.out.println(sale);
                }
            }
        }
    }

    /**
     * add a product to inventory
     */
    public void addProduct(Product product) {
        if (isInventoryFull()) {
            System.out.println("No se pueden a\u00f1adir mas productos, se ha alcanzado el maximo de " + inventory.size());
            return;
        }
        //inventory[numberProducts] = product;
        inventory.add(product);
        numberProducts++;
    }

    /**
     * check if inventory is full or not
     */
    public boolean isInventoryFull() {
        //return numberProducts == 10;
        return false;
    }

    /**
     * find product by name
     */
    public Product findProduct(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
                return inventory.get(i);
            }
        }
        return null;
    }

    /**
     * show total amount of all sales
     */
    public void amountSales() {
        Amount total = new Amount(0.0);
        for (Sale sale : sales) {
            if (sale != null) {
                total.setValue(total.getValue() + sale.getAmount().getValue());
            }
        }
        System.out.println("Total de ventas: " + total);
    }

    public void removeProduct() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Que producto quieres eliminar?");
        String prod = sc.nextLine();

        Product product = findProduct(prod);

        if (product != null) {
            inventory.remove(product);
            System.out.println("Producto eliminado ");

        }
    }

    public boolean initSession() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Introduzca numero de empleado:");
        int user = sc.nextInt();
        sc.nextLine();

        System.out.print("Introduzca contraseña:");
        String password = sc.nextLine();

        Employee emp = new Employee("gorka");

        boolean success = emp.login(user, password);

        if (success) {
            System.out.println("Login correcto");
            return true;
        } else {
            System.out.println("Datos incorrectos");
            return false;
        }
    }

}
