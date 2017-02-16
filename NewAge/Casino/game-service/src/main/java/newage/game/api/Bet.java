package newage.game.api;

import java.math.BigDecimal;

public class Bet {
	private Integer playerId;
	private Integer gameId;
	private BigDecimal amount;
	
	protected Bet(){}
	
	public Bet(Integer playerId, Integer gameId, BigDecimal amount){
		this.playerId = playerId;
		this.gameId = gameId;
		this.amount = amount;
	}
	
	public Integer getPlayerId() {
		return playerId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
