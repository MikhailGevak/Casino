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
import newage.game.TestUtils;

import static org.junit.Assert.*;

public class BetServiceImplTest {
	private BetService betService;

	@Before
	public void before() throws IOException {
		this.betService = TestUtils.getTestInjector().getInstance(BetService.class);
	}

	@Test
	public void placeBetTest() throws BetServiceException {
		Bet bet = betService.placeBet(12, 11, BigDecimal.valueOf(100));
		assertNotNull(bet);
		assertEquals(12, bet.getPlayerId().intValue());
		assertEquals(11, bet.getGameId().intValue());
		assertEquals(BigDecimal.valueOf(100), bet.getAmount());
	}

	public void showBetsTest() throws PlayerNotFoundException, BetServiceException {
		betService.placeBet(12, 11, BigDecimal.valueOf(100));
		betService.placeBet(12, 114, BigDecimal.valueOf(100));
		
		List<? extends Bet> bets = betService.getBets(12);
		assertNotNull(bets);
		assertEquals(2, bets.size());
	}
}
