import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Main extends JPanel implements MouseMotionListener
{

        private BufferedImage appleImage = null;
        private Imagesfood apple;
        private BufferedImage apple_pieImage = null;
        private Imagesfood apple_pie;
        private BufferedImage avocadoImage = null;
        private Imagesfood avocado;
        private BufferedImage boar_headImage = null;
        private Imagesfood boar_head;
        private BufferedImage breadImage = null;
        private Imagesfood bread;
        private BufferedImage cheeseImage = null;
        private Imagesfood cheese;
        private BufferedImage cheesecakeImage = null;
        private Imagesfood cheesecake;
        private BufferedImage chickenImage = null;
        private Imagesfood chicken;
        private BufferedImage cookieImage = null;
        private Imagesfood cookie;
        private BufferedImage dragon_fruitImage = null;
        private Imagesfood dragon_fruit;
        private BufferedImage fishImage = null;
        private Imagesfood fish;
        private BufferedImage fried_eggsImage = null;
        private Imagesfood fried_eggs;
        private BufferedImage honeyImage = null;
        private Imagesfood honey;
        private BufferedImage pineappleImage = null;
        private Imagesfood pineapple;
        private BufferedImage pretzelImage = null;
        private Imagesfood pretzel;
        private BufferedImage pumpkin_pieImage = null;
        private Imagesfood pumpkin_pie;
        private BufferedImage shrimpImage = null;
        private Imagesfood shrimp;
        private BufferedImage sushiImage = null;
        private Imagesfood sushi;
        private BufferedImage tboneImage = null;
        private Imagesfood tbone;
        private BufferedImage watermelonImage = null;
        private Imagesfood watermelon;
        
        if (appleImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 10;
            int imgY = 10;
            g.drawImage(appleImage, imgX, imgY, this);
        }
        if (apple_pieImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 50;
            int imgY = 10;
            g.drawImage(apple_pieImage, imgX, imgY, this);
        }
        if (avocadoImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 100;
            int imgY = 10;
            g.drawImage(avocadoImage, imgX, imgY, this);
        }
        if (boar_headImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 150;
            int imgY = 10;
            g.drawImage(boar_headImage, imgX, imgY, this);
        }
        if (breadImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 200;
            int imgY = 10;
            g.drawImage(breadImage, imgX, imgY, this);
        }
        if (cheeseImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 250;
            int imgY = 10;
            g.drawImage(cheeseImage, imgX, imgY, this);
        }
        if (cheesecakeImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 300;
            int imgY = 10;
            g.drawImage(cheesecakeImage, imgX, imgY, this);
        }
        if (chickenImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 350;
            int imgY = 10;
            g.drawImage(chickenImage, imgX, imgY, this);
        }
        if (cookieImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 400;
            int imgY = 10;
            g.drawImage(cookieImage, imgX, imgY, this);
        }
        if (dragon_fruitImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 450;
            int imgY = 10;
            g.drawImage(dragon_fruitImage, imgX, imgY, this);
        }
        if (fishImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 500;
            int imgY = 10;
            g.drawImage(fishImage, imgX, imgY, this);
        }
        if (fried_eggsImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 550;
            int imgY = 10;
            g.drawImage(fried_eggsImage, imgX, imgY, this);
        }
        if (honeyImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 600;
            int imgY = 10;
            g.drawImage(honeyImage, imgX, imgY, this);
        }
        if (pineappleImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 650;
            int imgY = 10;
            g.drawImage(pineappleImage, imgX, imgY, this);
        }
        if (pretzelImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 700;
            int imgY = 10;
            g.drawImage(pretzelImage, imgX, imgY, this);
        }
        if (pumpkin_pieImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 10;
            int imgY = 50;
            g.drawImage(pumpkin_pieImage, imgX, imgY, this);
        }
        if (shrimpImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 50;
            int imgY = 50;
            g.drawImage(shrimpImage, imgX, imgY, this);
        }
        if (sushiImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 100;
            int imgY = 50;
            g.drawImage(sushiImage, imgX, imgY, this);
        }
        if (tboneImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 150;
            int imgY = 50;
            g.drawImage(tboneImage, imgX, imgY, this);
        }
        if (watermelonImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 200;
            int imgY = 50;
            g.drawImage(watermelonImage, imgX, imgY, this);
        }


    }


    public static void main(String[] args) {
            panel.apple = new Imagesfood("apple");
            panel.appleImage = panel.apple.getImage();
            panel.apple_pie = new Imagesfood("apple_pie");
            panel.apple_pieImage = panel.apple_pie.getImage();
            panel.avocado = new Imagesfood("avocado");
            panel.avocadoImage = panel.avocado.getImage();
            panel.boar_head = new Imagesfood("boar_head");
            panel.boar_headImage = panel.boar_head.getImage();
            panel.bread = new Imagesfood("bread");
            panel.breadImage = panel.bread.getImage();
            panel.cheese = new Imagesfood("cheese");
            panel.cheeseImage = panel.cheese.getImage();
            panel.cheesecake = new Imagesfood("cheesecake");
            panel.cheesecakeImage = panel.cheesecake.getImage();
            panel.chicken = new Imagesfood("chicken");
            panel.chickenImage = panel.chicken.getImage();
            panel.cookie = new Imagesfood("cookie");
            panel.cookieImage = panel.cookie.getImage();
            panel.dragon_fruit = new Imagesfood("dragon_fruit");
            panel.dragon_fruitImage = panel.dragon_fruit.getImage();
            panel.fish = new Imagesfood("fish");
            panel.fishImage = panel.fish.getImage();
            panel.fried_eggs = new Imagesfood("fried_eggs");
            panel.fried_eggsImage = panel.fried_eggs.getImage();
            panel.honey = new Imagesfood("honey");
            panel.honeyImage = panel.honey.getImage();
            panel.pineapple = new Imagesfood("pineapple");
            panel.pineappleImage = panel.pineapple.getImage();
            panel.pretzel = new Imagesfood("pretzel");
            panel.pretzelImage = panel.pretzel.getImage();
            panel.pumpkin_pie = new Imagesfood("pumpkin_pie");
            panel.pumpkin_pieImage = panel.pumpkin_pie.getImage();
            panel.shrimp = new Imagesfood("shrimp");
            panel.shrimpImage = panel.shrimp.getImage();
            panel.sushi = new Imagesfood("sushi");
            panel.sushiImage = panel.sushi.getImage();
            panel.tbone = new Imagesfood("t-bone");
            panel.tboneImage = panel.tbone.getImage();
            panel.watermelon = new Imagesfood("watermelon");
            panel.watermelonImage = panel.watermelon.getImage();
        });

    } 
}
