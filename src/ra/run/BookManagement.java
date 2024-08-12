package ra.run;

import ra.service.CatalogService;
import ra.service.ProductService;
import ra.util.InputMethods;

import java.util.ArrayList;
import java.util.Scanner;

public class BookManagement {
    private static CatalogService catalogService;
    private static ProductService productService;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        catalogService = new CatalogService(new ArrayList<>(), scanner);
        productService = new ProductService(new ArrayList<>(), scanner, catalogService);
        while (true) {
            System.out.println("**************************BASIC-MENU**************************");
            System.out.println("1. Quản lý danh mục");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = getValidatedChoice(3);

            switch (choice) {
                case 1:
                    manageCatalogs();
                    break;
                case 2:
                    manageProducts();
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        }
    }

    private static int getValidatedChoice(int maxOption) {
        int choice = -1;
        while (choice < 1 || choice > maxOption) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > maxOption) {
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ, vui lòng nhập một số nguyên!");
            }
        }
        return choice;
    }

    private static void manageCatalogs() {
        while (true) {
            System.out.println("********************CATALOG-MANAGEMENT********************");
            System.out.println("1. Nhập số danh mục thêm mới và nhập thông tin cho từng danh mục");
            System.out.println("2. Hiển thị thông tin tất cả các danh mục");
            System.out.println("3. Sửa tên danh mục theo mã danh mục");
            System.out.println("4. Xóa danh mục theo mã danh mục (lưu ý ko xóa khi có sản phẩm)");
            System.out.println("5. Quay lại");
            System.out.println("Chọn một tùy chọn:");
            int choice = -1;
            while (choice < 1 || choice > 5) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice < 1 || choice > 5) {
                        System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập một số nguyên!");
                }
            }

            switch (choice) {
                case 1:
                    catalogService.addCatalogs();
                    break;
                case 2:
                    catalogService.displayAllCatalogs();
                    break;
                case 3:
                    catalogService.updateCatalogName();
                    break;
                case 4:
                    System.out.println("nhập id danh mục muốn xóa");
                    int id = Integer.parseInt(scanner.nextLine());
                    catalogService.delete(id);
                    break;
                case 5:
                    return;
            }
        }
    }

    private static void manageProducts() {
        while (true) {
            System.out.println("********************PRODUCT-MANAGEMENT********************");
            System.out.println("1. Nhập số lượng sản phẩm và nhập thông tin sản phẩm");
            System.out.println("2. Hiển thị thông tin tất cả các sản phẩm");
            System.out.println("3. Sắp xếp sản phẩm theo giá giảm dần");
            System.out.println("4. Xóa sản phẩm theo mã");
            System.out.println("5. Tìm kiếm sản phẩm theo tên");
            System.out.println("6. Thay đổi thông tin sản phẩm theo mã");
            System.out.println("7. Quay lại");
            System.out.println("Chọn một tùy chọn:");

            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    productService.addProducts();
                    break;
                case 2:
                    productService.displayAllProducts();
                    break;
                case 3:
                    productService.sortProductsByPriceDescending();
                    System.out.println("Sản phẩm đã được sắp xếp theo giá giảm dần.");
                    break;
                case 4:
                    System.out.println("Nhập ID sản phẩm cần xóa:");
                    String id = InputMethods.getString();
                    productService.delete(id);
                    break;
                case 5:
                    productService.searchProductByName();
                    break;
                case 6:
                    productService.updateProductInfo();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Tùy chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }
}
