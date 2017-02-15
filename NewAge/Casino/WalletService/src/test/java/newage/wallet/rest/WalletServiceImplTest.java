package newage.wallet.rest;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import newage.wallet.TestUtils;
import newage.wallet.api.Balance;
import newage.wallet.api.WalletService;
import newage.wallet.api.exception.AlreadyRegisteredPlayerException;
import newage.wallet.api.exception.InsufficientFundsException;
import newage.wallet.api.exception.PlayerNotFoundException;
import newage.wallet.api.exception.WalletException;

import static org.junit.Assert.*;

public class WalletServiceImplTest {
	private WalletService walletService;

	@Before
	public void before() throws IOException {
		walletService = TestUtils.getTestInjector().getInstance(WalletService.class);
	}

	@Test
	public void registerPlayer() throws AlreadyRegisteredPlayerException, WalletException {
		Balance balance = walletService.registerPlayer(13);
		assertNotNull(balance);
		assertEquals(BigDecimal.valueOf(0), balance.getAmount());
		assertEquals(13, balance.getPlayerId().intValue());
	}

	@Test
	public void depositBalance() throws PlayerNotFoundException, WalletException {
		walletService.registerPlayer(13);
		Balance balance = walletService.depositBalance(13, BigDecimal.valueOf(50));

		assertNotNull(balance);
		assertEquals(BigDecimal.valueOf(50), balance.getAmount());
		assertEquals(13, balance.getPlayerId().intValue());
	}

	@Test
	public void withdrawBalance()
			throws PlayerNotFoundException, InsufficientFundsException, WalletException {
		walletService.registerPlayer(13);
		walletService.depositBalance(13, BigDecimal.valueOf(50));
		Balance balance = walletService.withdrawBalance(13, BigDecimal.valueOf(20));

		assertNotNull(balance);
		assertEquals(BigDecimal.valueOf(30), balance.getAmount());
		assertEquals(13, balance.getPlayerId().intValue());
	}

	@Test
	public void getBalance() throws PlayerNotFoundException, WalletException {
		walletService.registerPlayer(13);
		walletService.depositBalance(13, BigDecimal.valueOf(50));
		Balance balance = walletService.getBalance(13);

		assertNotNull(balance);
		assertEquals(BigDecimal.valueOf(50), balance.getAmount());
		assertEquals(13, balance.getPlayerId().intValue());
	}

	@Test(expected=PlayerNotFoundException.class)
	public void removeBalance() throws PlayerNotFoundException, WalletException {
		walletService.registerPlayer(13);
		Boolean result = walletService.removeBalance(13);
		assertEquals(true, result);
		walletService.getBalance(13);
	}
}
