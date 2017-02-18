package newage.game.api;

import java.math.BigDecimal;

public interface Bet {
	Integer getId();
	Integer getPlayerId();
	Integer getGameId();
	BigDecimal getAmount();
}
