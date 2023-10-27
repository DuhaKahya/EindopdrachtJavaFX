package com.example.duhakahya697637endassignment;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private List<Product> product = new ArrayList<>();

    public Database() {
        product.add(new Product(1, "Fender Stratocaster Electric Guitar", "Guitars", 1199.99, "Classic electric guitar."));
        product.add(new Product(10, "Yamaha Acoustic Guitar", "Guitars", 599.99, "High-quality acoustic guitar for beginners."));
        product.add(new Product(12, "Roland Digital Piano", "Keyboards", 1499.99, "88-key digital piano with weighted keys."));
        product.add(new Product(6, "Shure SM58 Microphone", "Microphones", 99.99, "Legendary dynamic microphone for vocals."));
        product.add(new Product(5, "AKG K240 Studio Headphones", "Headphones", 69.99, "Over-ear studio headphones with clear sound."));
        product.add(new Product(3, "Focusrite Scarlett 2i2 Audio Interface", "Audio Interfaces", 199.99, "USB audio interface for recording."));
        product.add(new Product(9, "Ibanez RG Series Electric Guitar", "Guitars", 699.99, "Versatile electric guitar for rock and metal."));
        product.add(new Product(18, "Fender Deluxe Reverb Amp", "Amplifiers", 899.99, "Classic tube amplifier with rich tone."));
        product.add(new Product(12, "Amazon Echo Dot (4th Gen)", "Smart Speakers", 49.99, "Smart speaker with Alexa voice assistant."));

    }

    public List<Product> getProducts() {
        return product;
    }

}
