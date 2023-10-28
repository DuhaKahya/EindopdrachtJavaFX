package com.example.duhakahya697637endassignment.Data;

import com.example.duhakahya697637endassignment.Model.Order;
import com.example.duhakahya697637endassignment.Model.Product;
import com.example.duhakahya697637endassignment.Model.User;
import com.example.duhakahya697637endassignment.exceptions.SaveDataToFileException;
import com.example.duhakahya697637endassignment.exceptions.UnknownObjectException;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final List<Product> products;
    private final List<User> users;
    private final List<Order> orders;
    private String currentRole = "";

    private static final File DATA_FILE = new File("DuhaKahya-697637-end-assignment/src/main/resources/com/example/duhakahya697637endassignment/data.dat");


    public Database() {
        products = new ArrayList<>();
        users = new ArrayList<>();
        orders = new ArrayList<>();

        //check for data file
        if (DATA_FILE.exists()) {
            loadStandardData();
            System.out.println("Loaded standard data");
        } else {
            load();
        }

    }
    public boolean isValidUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Valid user
            }
        }
        return false; // User not found or invalid password
    }

    public String getUserRole(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentRole = user.getRole();
                return user.getRole(); // Return the user's role
            }
        }

        return null; //
    }
    public String roleOfUser(){
        return currentRole;
    }

    private void addProducts(){
        products.add(new Product(1, "Fender Stratocaster Electric Guitar", "Guitars", 1199.99, "Classic electric guitar."));
        products.add(new Product(10, "Yamaha Acoustic Guitar", "Guitars", 599.99, "High-quality acoustic guitar for beginners."));
        products.add(new Product(12, "Roland Digital Piano", "Keyboards", 1499.99, "88-key digital piano with weighted keys."));
        products.add(new Product(6, "Shure SM58 Microphone", "Microphones", 99.99, "Legendary dynamic microphone for vocals."));
        products.add(new Product(5, "AKG K240 Studio Headphones", "Headphones", 69.99, "Over-ear studio headphones with clear sound."));
        products.add(new Product(3, "Focusrite Scarlett 2i2 Audio Interface", "Audio Interfaces", 199.99, "USB audio interface for recording."));
        products.add(new Product(9, "Ibanez RG Series Electric Guitar", "Guitars", 699.99, "Versatile electric guitar for rock and metal."));
        products.add(new Product(18, "Fender Deluxe Reverb Amp", "Amplifiers", 899.99, "Classic tube amplifier with rich tone."));
        products.add(new Product(12, "Amazon Echo Dot (4th Gen)", "Smart Speakers", 49.99, "Smart speaker with Alexa voice assistant."));

    }
    private void addUser() {
        users.add(new User("admin", "1234", "Sales"));
        users.add(new User("duha", "4321", "Manager"));

    }

    private void loadStandardData() {
        //create standard data
        try {
            Files.createFile(DATA_FILE.toPath());
        } catch (FileAlreadyExistsException ignored) {
            System.out.println("File could not be deleted, overwriting data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Created new data file");

        //add Data
        addProducts();
        addUser();

        save();
    }
    public void save() {
        System.out.println("Saving data to file");
        //save list to file
        try (FileOutputStream fos = new FileOutputStream(DATA_FILE, false);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Object object : products) {
                oos.writeObject(object);
            }

        } catch (Exception e) {
            throw new SaveDataToFileException(e);
        }
    }

    private void load() {
        //load list from file
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE))) {
            loadObjects(ois);
            System.out.println("Loaded data from file");
        } catch (IOException e) {
            try {
                Files.delete(DATA_FILE.toPath());
            } catch (IOException Ignored) {// ignore unable to delete
            } finally {
                loadStandardData();
            }
        } catch (ClassNotFoundException e) {
            throw new UnknownObjectException();
        }
    }

    private void loadObjects(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        while (true) {
            try {
                Object object = ois.readObject();
                if (object instanceof Order order) {//check for objects found in file
                    orders.add(order);
                } else if (object instanceof User user) {
                    users.add(user);
                }
                else if (object instanceof Product product) {
                    products.add(product);
                } else {
                    throw new UnknownObjectException();
                }
            } catch (EOFException e) {
                return;
            } catch (ClassNotFoundException e) {
                throw new UnknownObjectException(e);
            }
        }
    }


    public List<Product> getProducts() {
        return products;
    }
    public List<Order> getOrders(){ return orders; }
    public List<User> getUsers(){ return users; }

}

