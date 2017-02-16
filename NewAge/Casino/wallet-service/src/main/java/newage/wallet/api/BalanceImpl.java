package newage.wallet.api;

import java.math.BigDecimal;

public class BalanceImpl implements Balance {
	private BigDecimal amount;
	private Integer playerId;

	protected BalanceImpl(){}
	
	public BalanceImpl(Integer playerId, BigDecimal amount) {
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
