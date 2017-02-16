package newage.game.api;

import java.math.BigDecimal;
import java.util.List;

import newage.common.exception.ParseAnswerException;
import newage.game.api.exception.BetServiceException;
import newage.game.api.exception.PlayerNotFoundException;
import newage.wallet.api.Balance;

public interface BetService {
	Balance placeBet(Integer playerId, Integer gameId, BigDecimal amount) throws BetServiceException, ParseAnswerException;
	List<Bet> getBets(Integer playerId) throws PlayerNotFoundException, BetServiceException, ParseAnswerException;
	Integer removeAllBets(Integer playerId) throws BetServiceException, ParseAnswerException;
}
