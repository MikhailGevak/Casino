package newage.wallet.api;

import java.math.BigDecimal;

public interface Balance {
	BigDecimal getAmount();
	Integer getPlayerId();
}
