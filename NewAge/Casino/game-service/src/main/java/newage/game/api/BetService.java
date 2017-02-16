package newage.game.api;

import java.math.BigDecimal;
import java.util.List;

import newage.game.api.exception.BetServiceException;
import newage.game.api.exception.PlayerNotFoundException;

public interface BetService {
	Bet placeBet(Integer playerId, Integer gameId, BigDecimal amount) throws BetServiceException;
	List<? extends Bet> getBets(Integer playerId) throws PlayerNotFoundException, BetServiceException;
}
