package com.example.GameFlix.service;

import com.example.GameFlix.model.Game;
import com.example.GameFlix.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> getAvailableGames() {
        return gameRepository.findByIsAvailable(true);
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    public List<Game> getGamesByGenre(String genre) {
        return gameRepository.findByGenreAndIsAvailable(genre, true);
    }

    public String addGame(String title, String description, String genre, String developer, 
                         String publisher, Integer releaseYear, Double price) {
        if (gameRepository.findByTitle(title).isPresent()) {
            return "Game with this title already exists";
        }

        Game game = new Game(title, description, genre, developer, publisher, releaseYear, price);
        gameRepository.save(game);
        return "Game added successfully";
    }

    public String updateGame(Long gameId, String title, String description, String genre, 
                           String developer, String publisher, Integer releaseYear, Double price) {
        Optional<Game> gameOpt = gameRepository.findById(gameId);
        if (gameOpt.isEmpty()) {
            return "Game not found";
        }

        Game game = gameOpt.get();
        game.setTitle(title);
        game.setDescription(description);
        game.setGenre(genre);
        game.setDeveloper(developer);
        game.setPublisher(publisher);
        game.setReleaseYear(releaseYear);
        game.setPrice(price);

        gameRepository.save(game);
        return "Game updated successfully";
    }

    public String deleteGame(Long gameId) {
        Optional<Game> gameOpt = gameRepository.findById(gameId);
        if (gameOpt.isEmpty()) {
            return "Game not found";
        }

        gameRepository.deleteById(gameId);
        return "Game deleted successfully";
    }

    public String toggleGameAvailability(Long gameId) {
        Optional<Game> gameOpt = gameRepository.findById(gameId);
        if (gameOpt.isEmpty()) {
            return "Game not found";
        }

        Game game = gameOpt.get();
        game.setIsAvailable(!game.getIsAvailable());
        gameRepository.save(game);
        return "Game availability updated successfully";
    }
}