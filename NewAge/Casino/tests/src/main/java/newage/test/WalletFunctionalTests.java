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

public class WalletFunctionalTests extends AbstractTest {
	private static Integer PLAYER_ID = 12345;
	private WebClient webClient = new WebClient(ServerHosts.WALLET_HOST);
	private WalletService walletServiceClient = new WalletServiceClient(webClient);

	@Before
	public void before() throws WalletException, ParseAnswerException{
		try {
			walletServiceClient.removeBalance(PLAYER_ID);
		} catch (PlayerNotFoundException ex) {
			// It's OK
		}
	}
	@Test
	public void walletSmokeTest() throws WalletException, ParseAnswerException {
		printlnGreen("Register player...");
		Balance balance = walletServiceClient.registerPlayer(PLAYER_ID);
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(0).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
		printlnGreen("Done!");
		
		printlnGreen("Deposit balance...");
		balance = walletServiceClient.depositBalance(PLAYER_ID, BigDecimal.valueOf(50));
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(50).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
		printlnGreen("Done!");
		
		printlnGreen("Get balance...");
		balance = walletServiceClient.getBalance(PLAYER_ID);
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(50).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
		printlnGreen("Done!");
		
		printlnGreen("Withdraw balance...");
		balance = walletServiceClient.withdrawBalance(PLAYER_ID, BigDecimal.valueOf(20));
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(30).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
		printlnGreen("Done!");
		
		printlnGreen("Get balance...");
		balance = walletServiceClient.getBalance(PLAYER_ID);
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(30).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
		printlnGreen("Done!");
		
		printlnGreen("Remove balance...");
		Boolean result = walletServiceClient.removeBalance(PLAYER_ID);
		assertEquals(true, result);
		printlnGreen("Done!");		

		try {
			printlnGreen("Get balance for PlayerNotFoundException...");
			balance = walletServiceClient.getBalance(PLAYER_ID);
		} catch (PlayerNotFoundException ex) {
			printlnGreen("Done!");		
		}
	}
}
