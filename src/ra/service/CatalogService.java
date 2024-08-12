package ra.service;


import ra.model.Catalog;
import ra.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogService implements IGenericService<Catalog, Integer> {
    private List<Catalog> catalogs = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public CatalogService(List<Catalog> catalogs, Scanner scanner) {
        this.catalogs = catalogs;
        this.scanner = scanner;
    }

    @Override
    public List<Catalog> findAll() {
        return catalogs;
    }

    @Override
    public void save(Catalog catalog) {
        catalogs.add(catalog);
    }



    @Override
    public Catalog findById(Integer id) {
        for (Catalog catalog : catalogs){
            if (catalog.getCatalogId() == id){
                return catalog;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        Catalog catalog = findById(id);
        if (catalog != null) {
            catalogs.remove(catalog);
            System.out.println("Xóa danh mục thành công.");
        } else {
            System.out.println("Không thể xóa danh mục vì có sản phẩm trong danh mục.");
        }
    }

    // New methods for catalog management

    public void addCatalogs() {
        System.out.print("Nhập số danh mục muốn thêm: ");
        int count = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < count; i++) {
            int id = -1;
            while (id < 0) {
                try {
                    System.out.print("Nhập mã danh mục: ");
                    id = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Mã danh mục phải là số nguyên dương.");
                }
            }

            String name = "";
            while (name.trim().isEmpty()) {
                System.out.print("Nhập tên danh mục: ");
                name = scanner.nextLine();
                if (name.trim().isEmpty()) {
                    System.out.println("Tên danh mục không được để trống.");
                }
            }

            String description = "";
            while (description.trim().isEmpty()) {
                System.out.print("Nhập mô tả danh mục: ");
                description = scanner.nextLine();
                if (description.trim().isEmpty()) {
                    System.out.println("Mô tả danh mục không được để trống.");
                }
            }

            Catalog catalog = new Catalog(id, name, description);
            save(catalog);
            System.out.println("Thêm mới thành công");
        }
    }

    public void displayAllCatalogs() {
        // Print header
        System.out.printf("%-10s %-20s %-30s\n",
                "Catalog ID", "Catalog Name", "Description");
        System.out.println("--------------------------------------------------------------");

        // Print each catalog in a formatted way
        for (Catalog catalog : findAll()) {
            System.out.printf("%-10d %-20s %-30s\n",
                    catalog.getCatalogId(),
                    catalog.getCatalogName(),
                    catalog.getDescription());
        }
    }

    public void updateCatalogName() {
        System.out.print("Nhập mã danh mục cần sửa: ");
        int id = Integer.parseInt(scanner.nextLine());
        Catalog catalog = findById(id);
        if (catalog != null) {
            System.out.print("Nhập tên mới: ");
            catalog.setCatalogName(scanner.nextLine());
        } else {
            System.out.println("Không tìm thấy danh mục với mã đã nhập.");
        }
    }



    private boolean hasProductInCatalog(Catalog catalog, ProductService productService) {
        for (Product product : productService.findAll()) {
            if (product.getCatalog().equals(catalog)) {
                return true;
            }
        }
        return false;
    }
}
