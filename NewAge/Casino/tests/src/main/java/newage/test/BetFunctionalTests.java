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
import newage.wallet.api.exception.InsufficientFundsException;
import newage.wallet.api.exception.PlayerNotFoundException;
import newage.wallet.api.exception.WalletException;
import newage.wallet.client.WalletServiceClient;

import static org.junit.Assert.*;

public class BetFunctionalTests {
	private static Integer PLAYER_ID = 12345;
	private static Integer GAME_ID = 456;
	private WebClient walletWebClient = new WebClient("http://localhost:9999/wallet");
	private WebClient betWebClient = new WebClient("http://localhost:9998/bet");

	private WalletService walletService = new WalletServiceClient(walletWebClient);
	private BetService betService = new BetServiceClient(betWebClient);

	@Before
	public void before() throws WalletException, ParseAnswerException, BetServiceException {
		try {
			walletService.removeBalance(PLAYER_ID);
		} catch (PlayerNotFoundException ex) {
			// It's OK
		}

		betService.removeAllBets(PLAYER_ID);
	}

	@Test
	public void betSmokeTest() throws WalletException, BetServiceException, ParseAnswerException {
		walletService.registerPlayer(PLAYER_ID);
		walletService.depositBalance(PLAYER_ID, BigDecimal.valueOf(100));

		Balance balance1 = betService.placeBet(PLAYER_ID, GAME_ID, BigDecimal.valueOf(50));
		assertEquals(PLAYER_ID, balance1.getPlayerId());
		assertEquals(BigDecimal.valueOf(50).stripTrailingZeros(), balance1.getAmount().stripTrailingZeros());

		Balance balance2 = betService.placeBet(PLAYER_ID, GAME_ID, BigDecimal.valueOf(30));
		assertEquals(PLAYER_ID, balance2.getPlayerId());
		assertEquals(BigDecimal.valueOf(20).stripTrailingZeros(), balance2.getAmount().stripTrailingZeros());

		List<Bet> bets = betService.getBets(PLAYER_ID);
		assertEquals(2, bets.size());

		assertEquals(bets.get(0).getPlayerId(), PLAYER_ID);
		assertEquals(bets.get(0).getGameId(), GAME_ID);
		assertEquals(bets.get(0).getAmount().stripTrailingZeros(), BigDecimal.valueOf(50).stripTrailingZeros());

		assertEquals(bets.get(1).getPlayerId(), PLAYER_ID);
		assertEquals(bets.get(1).getGameId(), GAME_ID);
		assertEquals(bets.get(1).getAmount().stripTrailingZeros(), BigDecimal.valueOf(30).stripTrailingZeros());

	}

	// It's a big Functional test scenario
	@Test
	public void complexTest() throws WalletException, BetServiceException, ParseAnswerException {
		Balance balance = walletService.registerPlayer(PLAYER_ID);
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(0).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());

		balance = walletService.depositBalance(PLAYER_ID, BigDecimal.valueOf(100));
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(100).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());

		balance = betService.placeBet(PLAYER_ID, GAME_ID, BigDecimal.valueOf(70));
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(30).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());

		try {
			betService.placeBet(PLAYER_ID, GAME_ID, BigDecimal.valueOf(40));
		} catch (BetServiceException ex) {
			ex.getMessage().equals("Insufficient funds for operation (playerId:" + PLAYER_ID + ")");
		}

		balance = walletService.getBalance(PLAYER_ID);
		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(30).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());

		List<Bet> bets = betService.getBets(PLAYER_ID);
		assertEquals(1, bets.size());
		assertEquals(BigDecimal.valueOf(70).stripTrailingZeros(), bets.get(0).getAmount().stripTrailingZeros());
		assertEquals(PLAYER_ID, bets.get(0).getPlayerId());
		assertEquals(GAME_ID, bets.get(0).getGameId());

		assertEquals(PLAYER_ID, balance.getPlayerId());
		assertEquals(BigDecimal.valueOf(30).stripTrailingZeros(), balance.getAmount().stripTrailingZeros());
	}
}
