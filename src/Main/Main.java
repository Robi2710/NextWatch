package Main;

import models.*;
import service.ContentService;
import service.UserService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ContentService contentService = ContentService.getInstance();
        UserService userService = UserService.getInstance();

        System.out.println("=".repeat(60));
        System.out.println("          NEXTWATCH - Platform Demo");
        System.out.println("=".repeat(60));

        System.out.println("\n--- Inregistrare utilizatori ---");
        User alice = userService.registerUser("alice", "alice@mail.com", "pass123");
        User bob   = userService.registerUser("bob",   "bob@mail.com",   "pass456");
        User carol = userService.registerUser("carol", "carol@mail.com", "pass789");

        System.out.println("\n--- Adaugare filme ---");
        Movie inception    = contentService.addMovie("Inception",          2010, "Christopher Nolan", 148);
        Movie interstellar = contentService.addMovie("Interstellar",       2014, "Christopher Nolan", 169);
        Movie parasite     = contentService.addMovie("Parasite",           2019, "Bong Joon-ho",      132);
        Movie dune         = contentService.addMovie("Dune",               2021, "Denis Villeneuve",  155);

        System.out.println("\n--- Adaugare seriale ---");
        Series breakingBad  = contentService.addSeries("Breaking Bad",      2008, "Vince Gilligan",   5, false);
        Series houseOfDragon= contentService.addSeries("House of the Dragon",2022,"Ryan Condal",      2, true);
        Series severance    = contentService.addSeries("Severance",         2022, "Dan Erickson",     2, true);

        System.out.println("\n--- Adaugare genuri ---");
        contentService.addGenreToContent(inception.getId(),     Genre.SCIENCE_FICTION);
        contentService.addGenreToContent(inception.getId(),     Genre.THRILLER);
        contentService.addGenreToContent(interstellar.getId(),  Genre.SCIENCE_FICTION);
        contentService.addGenreToContent(interstellar.getId(),  Genre.DRAMA);
        contentService.addGenreToContent(parasite.getId(),      Genre.THRILLER);
        contentService.addGenreToContent(parasite.getId(),      Genre.DRAMA);
        contentService.addGenreToContent(dune.getId(),          Genre.SCIENCE_FICTION);
        contentService.addGenreToContent(dune.getId(),          Genre.ADVENTURE);
        contentService.addGenreToContent(breakingBad.getId(),   Genre.CRIME);
        contentService.addGenreToContent(breakingBad.getId(),   Genre.DRAMA);
        contentService.addGenreToContent(houseOfDragon.getId(), Genre.FANTASY);
        contentService.addGenreToContent(houseOfDragon.getId(), Genre.DRAMA);
        contentService.addGenreToContent(severance.getId(),     Genre.SCIENCE_FICTION);
        contentService.addGenreToContent(severance.getId(),     Genre.MYSTERY);

        System.out.println("\n--- Adaugare episoade ---");
        contentService.addEpisodeToSeries(breakingBad.getId(), "Pilot",                   1, 1, 58,  "Walter White starts cooking meth.");
        contentService.addEpisodeToSeries(breakingBad.getId(), "Cat's in the Bag",        1, 2, 48,  "Walter and Jesse deal with the aftermath.");
        contentService.addEpisodeToSeries(severance.getId(),   "Good News About Hell",    1, 1, 60,  "Mark starts a new chapter at Lumon.");
        contentService.addEpisodeToSeries(severance.getId(),   "Half Loop",               1, 2, 55,  "Helly tries to quit.");

        System.out.println("\n--- Adaugare in watchlist ---");
        userService.addToWatchlist(alice.getId(), interstellar.getId());
        userService.addToWatchlist(alice.getId(), parasite.getId());
        userService.addToWatchlist(alice.getId(), severance.getId());
        userService.addToWatchlist(bob.getId(),   inception.getId());
        userService.addToWatchlist(bob.getId(),   breakingBad.getId());
        userService.addToWatchlist(carol.getId(),  dune.getId());

        System.out.println("\n--- Marcare ca vizionat ---");
        userService.markAsWatched(alice.getId(), interstellar.getId());
        userService.markAsWatched(alice.getId(), parasite.getId());
        userService.markAsWatched(bob.getId(),   inception.getId());
        userService.markAsWatched(carol.getId(),  dune.getId());

        System.out.println("\n--- Ratings ---");
        contentService.rateContent(alice.getId(), interstellar.getId(), 9.0);
        contentService.rateContent(alice.getId(), parasite.getId(),     9.5);
        contentService.rateContent(bob.getId(),   inception.getId(),    10.0);
        contentService.rateContent(bob.getId(),   breakingBad.getId(),  9.8);
        contentService.rateContent(carol.getId(),  dune.getId(),         8.5);
        contentService.rateContent(carol.getId(),  inception.getId(),    9.0);

        System.out.println("\n--- Recenzii ---");
        contentService.addReview(alice.getId(), parasite.getId(),
                "A masterpiece of social commentary. Bong Joon-ho is a genius.", false);
        contentService.addReview(bob.getId(),   inception.getId(),
                "Mind-bending. Nolan at his peak. The ending still gets me.", false);
        contentService.addReview(bob.getId(),   inception.getId(),
                "Second watch - I notice so many details I missed the first time.", false);
        contentService.addReview(carol.getId(),  dune.getId(),
                "Visually stunning but slightly slow paced for my taste.", false);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("              REZULTATE INTEROGARI");
        System.out.println("=".repeat(60));

        System.out.println("\n--- Watchlist Alice (dupa vizionari) ---");
        models.Watchlist aliceWatchlist = userService.getWatchlist(alice.getId());
        if (aliceWatchlist.size() == 0) {
            System.out.println("  Watchlist gol - toate titlurile au fost vizionate!");
        } else {
            for (int cid : aliceWatchlist.getContentIds()) {
                Content c = contentService.getContentById(cid);
                System.out.println("  - " + (c != null ? c.getTitle() : "Unknown #" + cid));
            }
        }

        System.out.println("\n--- Continut gen SCI_FI ---");
        List<Content> sciFi = contentService.findByGenre(Genre.SCIENCE_FICTION);
        for (Content c : sciFi) {
            System.out.println("  " + c);
        }

        System.out.println("\n--- Top 5 dupa rating ---");
        List<Content> top5 = contentService.getTopRated(5);
        int rank = 1;
        for (Content c : top5) {
            System.out.printf("  #%d %s%n", rank++, c);
        }

        System.out.println("\n--- Cautare titlu: 'in' ---");
        List<Content> searchResults = contentService.searchByTitle("in");
        for (Content c : searchResults) {
            System.out.println("  " + c.getTitle() + " (" + c.getContentType() + ")");
        }

        System.out.println("\n--- Recenzii pentru Inception ---");
        List<Review> inceptionReviews = contentService.getReviewsForContent(inception.getId());
        for (Review r : inceptionReviews) {
            System.out.println("  " + r);
        }

        System.out.println("\n--- Episoade Breaking Bad Sezon 1 ---");
        for (Episode ep : breakingBad.getEpisodesBySeason(1)) {
            System.out.println("  " + ep);
        }

        System.out.println("\n--- Stare utilizatori ---");
        for (models.User u : userService.getAllUsers()) {
            System.out.println("  " + u);
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("              Demo complet!");
        System.out.println("=".repeat(60));
    }
}
