package com.gameManagement.demo.dataAccess.abstracts;

import com.gameManagement.demo.entities.concretes.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerDao extends JpaRepository<Player, Integer> {

    boolean existsPlayerByNationalityId(long nationalityId);

    boolean existsPlayerByPlayerId(int playerId);
}
