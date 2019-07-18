package controller;

public class Runner {
    public static void main(String[] args) {
        Scrapper scrapper = new Scrapper("https://ain.ua");
        scrapper.scrapAllData();

    }
}
