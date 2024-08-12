package ra.service;

import ra.model.Product;
import ra.model.Catalog;
import ra.util.InputMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductService implements IGenericService<Product, String> {
    private List<Product> products;
    private static Scanner scanner = new Scanner(System.in);

    private CatalogService catalogService;

    public ProductService(List<Product> products, Scanner scanner, CatalogService catalogService) {
        this.products = products;
        this.scanner = scanner;
        this.catalogService = catalogService;
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public Product findById(String id) {
        for (Product product : products) {
            // Add null check before calling equals
            if (product.getProductId() != null && product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }


    @Override
    public void delete(String id) {
        products.removeIf(product -> product.getProductId().equals(id));
    }


    public void addProducts() {
        System.out.print("Nhập số sản phẩm muốn thêm: ");
        int count = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < count; i++) {
            String productId;
            do {
                System.out.print("Nhập mã sản phẩm (bắt đầu bằng chữ P và thêm 4 ký tự số): ");
                productId = scanner.nextLine();
                if (!productId.matches("P\\d{4}")) {
                    System.out.println("Mã sản phẩm phải bắt đầu bằng chữ P và có thêm 4 ký tự số.");
                    productId = null;
                } else if (findById(productId) != null) {
                    System.out.println("Mã sản phẩm này đã tồn tại. Vui lòng nhập mã khác.");
                    productId = null;
                }
            } while (productId == null);

            String productName = "";
            while (productName.trim().isEmpty()) {
                System.out.print("Nhập tên sản phẩm: ");
                productName = scanner.nextLine();
                if (productName.trim().isEmpty()) {
                    System.out.println("Tên sản phẩm không được để trống.");
                }
            }

            double productPrice = -1;
            while (productPrice <= 0) {
                try {
                    System.out.print("Nhập giá sản phẩm: ");
                    productPrice = Double.parseDouble(scanner.nextLine());
                    if (productPrice <= 0) {
                        System.out.println("Giá sản phẩm phải lớn hơn 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Giá sản phẩm phải là một số hợp lệ.");
                }
            }

            System.out.print("Nhập mô tả sản phẩm: ");
            String description = scanner.nextLine();

            int stock = -1;
            while (stock < 10) {
                try {
                    System.out.print("Nhập số lượng tồn kho: ");
                    stock = Integer.parseInt(scanner.nextLine());
                    if (stock < 10) {
                        System.out.println("Số lượng tồn kho phải ít nhất là 10.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Số lượng tồn kho phải là một số nguyên.");
                }
            }

            Catalog catalog = null;
            while (catalog == null) {
                System.out.print("Chọn mã danh mục: ");
                int catalogId = Integer.parseInt(scanner.nextLine());
                catalog = catalogService.findById(catalogId);
                if (catalog == null) {
                    System.out.println("Mã danh mục không tồn tại. Vui lòng nhập mã khác.");
                }
            }

            Product product = new Product(productId, productName, productPrice, description, stock, catalog);
            save(product);
            System.out.println("Thêm mới sản phẩm thành công.");
        }
    }

    public void displayAllProducts() {
        // Print header
        System.out.printf("%-12s %-20s %-10s %-30s %-8s %-10s\n",
                "Product ID", "Product Name", "Price", "Description", "Stock", "Catalog ID");
        System.out.println("-----------------------------------------------------------------------------------------------");

        // Print each product in a formatted way
        for (Product product : findAll()) {
            System.out.printf("%-12s %-20s %-10.2f %-30s %-8d %-10d\n",
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductPrice(),
                    product.getDescription(),
                    product.getStock(),
                    product.getCatalog().getCatalogId());
        }
    }


    public void sortProductsByPriceDescending() {
        findAll().sort((p1, p2) -> Double.compare(p2.getProductPrice(), p1.getProductPrice()));
        displayAllProducts();
    }



    public void searchProductByName() {
        System.out.print("Nhập tên sản phẩm cần tìm: ");
        String name = scanner.nextLine();
        for (Product product : findAll()) {
            if (product.getProductName().contains(name)) {
                System.out.println(product);
            }
        }
    }

    public void updateProductInfo() {
        System.out.print("Nhập mã sản phẩm cần thay đổi thông tin: ");
        String productId = scanner.nextLine();
        Product product = findById(productId);
        if (product != null) {
            System.out.print("Nhập tên mới: ");
            product.setProductName(scanner.nextLine());
            System.out.print("Nhập giá mới: ");
            product.setProductPrice(Double.parseDouble(scanner.nextLine()));
            System.out.print("Nhập mô tả mới: ");
            product.setDescription(scanner.nextLine());
            System.out.print("Nhập số lượng tồn kho mới: ");
            product.setStock(Integer.parseInt(scanner.nextLine()));
        } else {
            System.out.println("Không tìm thấy sản phẩm với mã đã nhập.");
        }
    }
}
