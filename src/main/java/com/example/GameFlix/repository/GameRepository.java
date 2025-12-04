package com.example.GameFlix.repository;

import com.example.GameFlix.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByTitle(String title);
    List<Game> findByGenre(String genre);//add search
    List<Game> findByIsAvailable(Boolean isAvailable);
    List<Game> findByGenreAndIsAvailable(String genre, Boolean isAvailable);
}