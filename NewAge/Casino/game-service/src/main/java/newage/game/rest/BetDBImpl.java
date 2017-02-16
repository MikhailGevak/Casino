package newage.game.rest;

import java.math.BigDecimal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import newage.game.api.Bet;

@DatabaseTable(tableName = "balances")
public class BetDBImpl implements Bet{
	public final static String PLAYER_ID_COLUMN_NAME = "playerId";
	
	@DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
	private Integer id;
	@DatabaseField(columnName = PLAYER_ID_COLUMN_NAME)
	private Integer playerId;
	@DatabaseField
	private Integer gameId;
	@DatabaseField
	private BigDecimal amount;
	
	protected BetDBImpl(){}
	
	public BetDBImpl(Integer playerId, Integer gameId, BigDecimal amount){
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
