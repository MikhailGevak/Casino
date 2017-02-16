package newage.game.api;

import java.math.BigDecimal;

public class BetImpl implements Bet {
	private Integer  id;
	private Integer  playerId;
	private Integer  gameId;
	private BigDecimal amount;
	
	protected BetImpl(){}
	
	public BetImpl(Integer id, Integer playerId, Integer gameId, BigDecimal amount){
		this.id = id;
		this.playerId = playerId;
		this.gameId = gameId;
		this.amount = amount;
	}
	
	public Integer getId() {
		return id;
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
