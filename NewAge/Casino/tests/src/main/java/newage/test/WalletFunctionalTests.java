package newage.test;

import org.junit.Before;
import org.junit.Test;

import newage.common.client.WebClient;
import newage.common.exception.ParseAnswerException;
import newage.wallet.api.Balance;
import newage.wallet.api.WalletService;
import newage.wallet.api.exception.PlayerNotFoundException;
import newage.wallet.api.exception.WalletException;
import newage.wallet.client.WalletServiceClient;

import static org.junit.Assert.*;

import java.math.BigDecimal;

public class WalletFunctionalTests {
	private static Integer PLAYER_ID = 12345;
	private WebClient webClient = new WebClient("http://localhost:9999/wallet");
	private WalletService walletService = new WalletServiceClient(webClient);

	@Before
	public void before() throws WalletException, ParseAnswerException{
		try {
			walletService.removeBalance(PLAYER_ID);
		} catch (PlayerNotFoundException ex) {
			// It's OK
		}
	}
	@Test
	public void walletSmokeTest() throws WalletException, ParseAnswerException {
		

		Balance balance = walletService.registerPlayer(PLAYER_ID);
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(0).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());

		balance = walletService.depositBalance(PLAYER_ID, BigDecimal.valueOf(50));
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(50).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());

		balance = walletService.getBalance(PLAYER_ID);
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(50).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());

		balance = walletService.withdrawBalance(PLAYER_ID, BigDecimal.valueOf(20));
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(30).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());

		balance = walletService.getBalance(PLAYER_ID);
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(30).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());

		Boolean result = walletService.removeBalance(PLAYER_ID);
		assertEquals(true, result);

		try {
			balance = walletService.getBalance(PLAYER_ID);
		} catch (PlayerNotFoundException ex) {
			// Do nothing
		}
	}
}
