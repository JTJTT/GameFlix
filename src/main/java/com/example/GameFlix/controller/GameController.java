package com.example.GameFlix.controller;

import com.example.GameFlix.model.Game;
import com.example.GameFlix.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    // Web endpoints
    @GetMapping("/games")
    public String showGames(Model model) {
        List<Game> games = gameService.getAllGames();
        model.addAttribute("games", games);
        return "GameList";
    }

    @GetMapping("/admin/games")
    public String showAdminGames(Model model) {
        List<Game> games = gameService.getAllGames();
        model.addAttribute("games", games);
        return "AdminManagement";
    }

    @GetMapping("/games/add")
    public String showAddGamePage() {
        return "AddGame";
    }

    @GetMapping("/games/edit/{id}")
    public String showEditGamePage(@PathVariable Long id, Model model) {
        Optional<Game> game = gameService.getGameById(id);
        if (game.isPresent()) {
            model.addAttribute("game", game.get());
            return "EditGame";  // Changed from "editGame" to "EditGame"
        }
        model.addAttribute("error", "Game not found");
        return "redirect:/admin/games";
    }

    @PostMapping("/games/add")
    public String addGame(@RequestParam String title,
                         @RequestParam String description,
                         @RequestParam String genre,
                         @RequestParam String developer,
                         @RequestParam String publisher,
                         @RequestParam Integer releaseYear,
                         @RequestParam Double price,
                         Model model) {
        String result = gameService.addGame(title, description, genre, developer, publisher, releaseYear, price);
        model.addAttribute("message", result);

        if (result.equals("Game added successfully")) {
            return "redirect:/admin/games";
        }
        return "AddGame";  // Stay on form page to show error
    }

    @PostMapping("/games/edit/{id}")
    public String updateGame(@PathVariable Long id,
                           @RequestParam String title,
                           @RequestParam String description,
                           @RequestParam String genre,
                           @RequestParam String developer,
                           @RequestParam String publisher,
                           @RequestParam Integer releaseYear,
                           @RequestParam Double price,
                           Model model) {
        String result = gameService.updateGame(id, title, description, genre, developer, publisher, releaseYear, price);
        model.addAttribute("message", result);
        return "redirect:/admin/games";
    }

    @PostMapping("/games/delete/{id}")
    public String deleteGame(@PathVariable Long id, Model model) {
        String result = gameService.deleteGame(id);
        model.addAttribute("message", result);
        return "redirect:/admin/games";
    }

    @PostMapping("/games/toggle/{id}")
    public String toggleGameAvailability(@PathVariable Long id, Model model) {
        String result = gameService.toggleGameAvailability(id);
        model.addAttribute("message", result);
        return "redirect:/admin/games";
    }

    // API endpoints
    @GetMapping("/api/games")
    @ResponseBody
    public ResponseEntity<List<Game>> getAllGamesApi() {
        List<Game> games = gameService.getAvailableGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/api/games/{id}")
    @ResponseBody
    public ResponseEntity<Game> getGameByIdApi(@PathVariable Long id) {
        Optional<Game> game = gameService.getGameById(id);
        return game.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/games")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addGameApi(@RequestBody Map<String, Object> request) {
        Map<String, String> response = new HashMap<>();
        
        try {
            String title = (String) request.get("title");
            String description = (String) request.get("description");
            String genre = (String) request.get("genre");
            String developer = (String) request.get("developer");
            String publisher = (String) request.get("publisher");
            Integer releaseYear = Integer.valueOf(request.get("releaseYear").toString());
            Double price = Double.valueOf(request.get("price").toString());

            String result = gameService.addGame(title, description, genre, developer, publisher, releaseYear, price);
            response.put("message", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Invalid game data: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/api/games/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateGameApi(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Map<String, String> response = new HashMap<>();
        
        try {
            String title = (String) request.get("title");
            String description = (String) request.get("description");
            String genre = (String) request.get("genre");
            String developer = (String) request.get("developer");
            String publisher = (String) request.get("publisher");
            Integer releaseYear = Integer.valueOf(request.get("releaseYear").toString());
            Double price = Double.valueOf(request.get("price").toString());

            String result = gameService.updateGame(id, title, description, genre, developer, publisher, releaseYear, price);
            response.put("message", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Invalid game data: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/api/games/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteGameApi(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        String result = gameService.deleteGame(id);
        response.put("message", result);
        return ResponseEntity.ok(response);
    }
}