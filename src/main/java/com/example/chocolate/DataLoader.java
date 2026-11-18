package com.example.chocolate;

import com.example.chocolate.model.Chocolate;
import com.example.chocolate.model.Place;
import com.example.chocolate.model.User;
import com.example.chocolate.repository.ChocolateRepository;
import com.example.chocolate.repository.PlaceRepository;
import com.example.chocolate.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Adds sample data when the app starts
@Component
public class DataLoader implements CommandLineRunner {

    private final PlaceRepository places;
    private final ChocolateRepository chocolates;
    private final UserRepository users;

    public DataLoader(PlaceRepository places, ChocolateRepository chocolates, UserRepository users) {
        this.places = places;
        this.chocolates = chocolates;
        this.users = users;
    }

    @Override
    public void run(String... args) {
        // Seed default users if not present
        if (users.count() == 0) {
            users.save(new User("admin", "password123", "Chocolatier Admin", "admin@chocolateworld.com", "ROLE_ADMIN"));
            users.save(new User("user", "password123", "Cocoa Lover", "lover@chocolateworld.com", "ROLE_USER"));
        }

        // If chocolates already exist, update any missing image URLs
        if (places.count() > 0) {
            chocolates.findAll().forEach(c -> {
                if (c.getImageUrl() == null || c.getImageUrl().isBlank()) {
                    String name = c.getName();
                    if (name.contains("Praline")) c.setImageUrl("/images/praline.jpg");
                    else if (name.contains("Truffle")) c.setImageUrl("/images/truffle.jpg");
                    else if (name.contains("Alpine")) c.setImageUrl("/images/milk_bar.jpg");
                    else if (name.contains("Honey")) c.setImageUrl("/images/white_nougat.jpg");
                    else if (name.contains("Gianduja")) c.setImageUrl("/images/gianduja.jpg");
                    else if (name.contains("Ghana")) c.setImageUrl("/images/dark_ghana.jpg");
                    else if (name.contains("Oaxaca")) c.setImageUrl("/images/cinnamon_oaxaca.jpg");
                    else if (name.contains("Cardamom")) c.setImageUrl("/images/cardamom_dark.jpg");
                    else if (name.contains("Pepper")) c.setImageUrl("/images/pepper_milk.jpg");
                    else if (name.contains("Matcha")) c.setImageUrl("/images/matcha_white.jpg");
                    else if (name.contains("Ruby") || name.contains("Yuzu")) c.setImageUrl("/images/ruby_yuzu.jpg");
                    else {
                        String t = c.getType() != null ? c.getType().toLowerCase() : "dark";
                        c.setImageUrl("/images/default_" + t + ".jpg");
                    }
                    chocolates.save(c);
                }
            });
            return;
        }

        Place brussels = places.save(new Place("Brussels", "Belgium",
                "Home of the praline: a thin chocolate shell around a soft filling."));
        Place zurich = places.save(new Place("Zurich", "Switzerland",
                "Known for smooth milk chocolate made with Alpine milk."));
        Place turin = places.save(new Place("Turin", "Italy",
                "Birthplace of gianduja, chocolate blended with roasted hazelnuts."));
        Place accra = places.save(new Place("Accra", "Ghana",
                "One of the world's biggest cocoa growers, famous for rich dark chocolate."));
        Place oaxaca = places.save(new Place("Oaxaca", "Mexico",
                "Chocolate here is ground with sugar and spices and often drunk hot."));
        Place kochi = places.save(new Place("Kochi", "India",
                "Cocoa grows beside spice plantations, so cardamom and pepper show up in the bars."));
        Place tokyo = places.save(new Place("Tokyo", "Japan",
                "Chocolatiers mix in matcha, sesame and yuzu."));

        chocolates.save(new Chocolate("Hazelnut Praline Shells", "Milk",
                "Milk chocolate shells filled with a soft hazelnut cream.", 650, brussels, "/images/praline.jpg"));
        chocolates.save(new Chocolate("Brussels Dark Truffle", "Dark",
                "A dark ganache centre rolled in cocoa powder.", 720, brussels, "/images/truffle.jpg"));
        chocolates.save(new Chocolate("Alpine Milk Bar", "Milk",
                "Creamy, mild and very smooth.", 400, zurich, "/images/milk_bar.jpg"));
        chocolates.save(new Chocolate("Honey Almond Nougat Bar", "White",
                "White chocolate with honey and crunchy almonds.", 480, zurich, "/images/white_nougat.jpg"));
        chocolates.save(new Chocolate("Gianduja Squares", "Milk",
                "Melt-in-the-mouth chocolate with a high share of hazelnut paste.", 560, turin, "/images/gianduja.jpg"));
        chocolates.save(new Chocolate("Ghana Single-Origin 70%", "Dark",
                "Deep, slightly fruity dark chocolate with a long finish.", 520, accra, "/images/dark_ghana.jpg"));
        chocolates.save(new Chocolate("Oaxacan Cinnamon Tablet", "Dark",
                "Coarse, grainy chocolate with cinnamon, made for hot chocolate.", 450, oaxaca, "/images/cinnamon_oaxaca.jpg"));
        chocolates.save(new Chocolate("Cardamom Dark Bar", "Dark",
                "Kerala cocoa with a hint of green cardamom.", 380, kochi, "/images/cardamom_dark.jpg"));
        chocolates.save(new Chocolate("Black Pepper Milk Bar", "Milk",
                "Sweet milk chocolate with a warm pepper kick.", 350, kochi, "/images/pepper_milk.jpg"));
        chocolates.save(new Chocolate("Matcha White Chocolate", "White",
                "Sweet white chocolate with bitter green tea.", 700, tokyo, "/images/matcha_white.jpg"));
        chocolates.save(new Chocolate("Yuzu Ruby Bar", "Ruby",
                "Pink ruby chocolate with bright citrus.", 780, tokyo, "/images/ruby_yuzu.jpg"));
    }
}
