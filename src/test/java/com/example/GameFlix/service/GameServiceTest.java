package com.example.GameFlix.service;

import com.example.GameFlix.model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    void getAllGames_ShouldReturnList() {
        // Add a test game first to ensure we have data
        gameService.addGame("Test Game", "Test Description", "Action",
                "Test Developer", "Test Publisher", 2024, 29.99);

        var games = gameService.getAllGames();
        Assertions.assertFalse(games.isEmpty());
    }

    @Test
    void addGame_ShouldReturnSuccessMessage() {
        // Test adding a new game
        String result = gameService.addGame("New Game Title", "Game Description",
                "Adventure", "Game Developer",
                "Game Publisher", 2024, 59.99);

        Assertions.assertEquals("Game added successfully", result);

        // Verify the game was actually added
        List<Game> games = gameService.getAllGames();
        boolean gameExists = games.stream()
                .anyMatch(game -> "New Game Title".equals(game.getTitle()));
        Assertions.assertTrue(gameExists);
    }

    @Test
    void addGame_WithDuplicateTitle_ShouldReturnErrorMessage() {
        // Add a game first
        gameService.addGame("Duplicate Title", "Description", "Genre",
                "Developer", "Publisher", 2024, 49.99);

        // Try to add another game with the same title
        String result = gameService.addGame("Duplicate Title", "Different Description",
                "Different Genre", "Different Developer",
                "Different Publisher", 2023, 39.99);

        Assertions.assertEquals("Game with this title already exists", result);
    }


}