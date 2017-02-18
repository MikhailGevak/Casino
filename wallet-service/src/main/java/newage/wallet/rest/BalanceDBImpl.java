package newage.wallet.rest;

import java.math.BigDecimal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import newage.wallet.api.Balance;

@DatabaseTable(tableName = "balances")
class BalanceDBImpl implements Balance {
	@DatabaseField(id = true, generatedId = false)
	private Integer playerId;

	@DatabaseField()
	private BigDecimal amount;

	protected BalanceDBImpl() {
	}

	public BalanceDBImpl(Integer playerId, BigDecimal amount) {
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
