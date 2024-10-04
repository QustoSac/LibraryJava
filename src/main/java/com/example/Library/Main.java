package com.example.Library;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/library";
        String user = "danila";
        String password = "kappa";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Успешное подключение к базе данных");

            menu(connection);

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addBook(Connection connection, Scanner scanner) {
        try {
            System.out.println("Введите название книги:");
            String title = scanner.nextLine();
            System.out.println("Введите автора:");
            String author = scanner.nextLine();
            System.out.println("Введите год издания:");
            int year = scanner.nextInt();
            scanner.nextLine();

            String query = "INSERT INTO books (title, author, year) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            pstmt.executeUpdate();

            System.out.println("Книга успешно добавлена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateBook(Connection connection, Scanner scanner) {
        try {
            System.out.println("Введите ID книги для обновления");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Введите название книги:");
            String title = scanner.nextLine();
            System.out.println("Введите автора:");
            String author = scanner.nextLine();
            System.out.println("Введите год издания:");
            int year = scanner.nextInt();
            scanner.nextLine();

            String query = "UPDATE books SET title = ?, author = ?, year = ? WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();

            System.out.println("Книга успешно обновлена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteBook(Connection connection, Scanner scanner) {
        try {
            System.out.println("Введите ID книги для удаления:");
            int id = scanner.nextInt();
            scanner.nextLine();

            String query = "DELETE FROM books WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("Книга успешно удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayBooks(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Название: " + rs.getString("title"));
                System.out.println("Автор: " + rs.getString("author"));
                System.out.println("Год издания: " + rs.getInt("year"));
                System.out.println("-----------------------");
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void menu(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Добавить новую книгу");
            System.out.println("2. Посмотреть все книги");
            System.out.println("3. Обновить данные о книге");
            System.out.println("4. Удалить книгу");
            System.out.println("5. Выход");
            System.out.print("Выберите нужный вариант: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBook(connection, scanner);
                    break;
                case 2:
                    displayBooks(connection);
                    break;
                case 3:
                    updateBook(connection, scanner);
                    break;
                case 4:
                    deleteBook(connection, scanner);
                    break;
                case 5:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова");
            }
        }
    }
}