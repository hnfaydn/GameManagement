package com.gameManagement.demo.dataAccess.abstracts;

import com.gameManagement.demo.entities.concretes.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDao extends JpaRepository<Game,Integer> {
}
