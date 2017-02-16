package newage.game.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import newage.game.api.Bet;
import newage.game.api.BetService;
import newage.game.api.exception.BetServiceException;
import newage.game.api.exception.PlayerNotFoundException;
import newage.wallet.api.Balance;
import newage.common.exception.ParseAnswerException;
import newage.game.TestUtils;

import static org.junit.Assert.*;

public class BetServiceImplTest {
	private BetService betService;

	@Before
	public void before() throws IOException {
		this.betService = TestUtils.getTestInjector().getInstance(BetService.class);
	}

	@Test
	public void placeBetTest() throws BetServiceException, ParseAnswerException {
		Balance balance = betService.placeBet(12, 11, BigDecimal.valueOf(100));
		assertNotNull(balance);
		assertEquals(12, balance.getPlayerId().intValue());
	}

	public void showBetsTest() throws PlayerNotFoundException, BetServiceException, ParseAnswerException {
		betService.placeBet(12, 11, BigDecimal.valueOf(100));
		betService.placeBet(12, 114, BigDecimal.valueOf(100));
		
		List<? extends Bet> bets = betService.getBets(12);
		assertNotNull(bets);
		assertEquals(2, bets.size());
	}
}
