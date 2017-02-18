package newage.test;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import newage.common.client.WebClient;
import newage.common.exception.ParseAnswerException;
import newage.game.api.Bet;
import newage.game.api.BetService;
import newage.game.api.exception.BetServiceException;
import newage.game.client.BetServiceClient;
import newage.wallet.api.Balance;
import newage.wallet.api.WalletService;
import newage.wallet.api.exception.PlayerNotFoundException;
import newage.wallet.api.exception.WalletException;
import newage.wallet.client.WalletServiceClient;

import static org.junit.Assert.*;

public class FunctionalTests extends AbstractTest{
	private static Integer PLAYER_ID = 12345;
	private static Integer GAME_ID = 456;
	private WebClient walletWebClient = new WebClient(ServerHosts.WALLET_HOST);
	private WebClient betWebClient = new WebClient(ServerHosts.BET_HOST);

	private WalletService walletServiceClient = new WalletServiceClient(walletWebClient);
	private BetService betServiceClient = new BetServiceClient(betWebClient);

	@Before
	public void before() throws WalletException, ParseAnswerException, BetServiceException {
		try {
			walletServiceClient.removeBalance(PLAYER_ID);
		} catch (PlayerNotFoundException ex) {
			// It's OK
		}

		betServiceClient.removeAllBets(PLAYER_ID);
	}

	// It's a big Functional test scenario
	@Test
	public void complexTest() throws WalletException, BetServiceException, ParseAnswerException {
		
		printlnGreen("Register player...");
		Balance balance = walletServiceClient.registerPlayer(PLAYER_ID);
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(0).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
		printlnGreen("Done!");
		
		printlnGreen("Deposit balance...");
		balance = walletServiceClient.depositBalance(PLAYER_ID, BigDecimal.valueOf(100));
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(100).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
		printlnGreen("Done!");
		
		printlnGreen("Place bet...");
		balance = betServiceClient.placeBet(PLAYER_ID, GAME_ID, BigDecimal.valueOf(70));
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(30).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
		printlnGreen("Done!");
		
		try {
			printlnGreen("Place bet for insufficient funds...");
			betServiceClient.placeBet(PLAYER_ID, GAME_ID, BigDecimal.valueOf(40));
		} catch (BetServiceException ex) {
			ex.getMessage().equals("Insufficient funds for operation (playerId:" + PLAYER_ID + ")");
			printlnGreen("Done!");
		}

		printlnGreen("Get balance...");
		balance = walletServiceClient.getBalance(PLAYER_ID);
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(30).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
		printlnGreen("Done!");
		
		printlnGreen("Get bets...");
		List<Bet> bets = betServiceClient.getBets(PLAYER_ID);
		assertEquals(1, bets.size());
		assertEquals(BigDecimal.valueOf(70).stripTrailingZeros(), bets.get(0).getAmount().stripTrailingZeros());
		assertEquals(PLAYER_ID, bets.get(0).getPlayerId());
		assertEquals(GAME_ID, bets.get(0).getGameId());
		printlnGreen("Done!");
		
		printlnGreen("Get balance...");
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(30).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
		printlnGreen("Done!");
	}
	
	
}
