package newage.wallet.rest;

import java.math.BigDecimal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import newage.wallet.api.Balance;

//TODO: This strange construction have to be removed. 
//It's due to parent Balance have to be a class with fields. 
//Gson can't work with methods. Only with fields :(
@DatabaseTable(tableName = "balances")
class BalanceImpl extends Balance {
	@DatabaseField(id = true, generatedId = false)
	private Integer playerId;

	@DatabaseField()
	private BigDecimal amount;

	protected BalanceImpl() {
	}

	public BalanceImpl(Integer playerId, BigDecimal amount) {
		super(playerId, amount);
		this.amount = amount;
		this.playerId = playerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Integer getPlayerId() {
		return playerId;
	}
}
