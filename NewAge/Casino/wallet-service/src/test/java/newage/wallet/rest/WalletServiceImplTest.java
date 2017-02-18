package newage.wallet.rest;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import newage.common.exception.ParseAnswerException;
import newage.wallet.TestUtils;
import newage.wallet.api.Balance;
import newage.wallet.api.WalletService;
import newage.wallet.api.exception.AlreadyRegisteredPlayerException;
import newage.wallet.api.exception.InsufficientFundsException;
import newage.wallet.api.exception.PlayerNotFoundException;
import newage.wallet.api.exception.WalletException;

import static org.junit.Assert.*;

public class WalletServiceImplTest {
	private WalletService walletServiceClient;

	@Before
	public void before() throws IOException {
		walletServiceClient = TestUtils.getTestInjector().getInstance(WalletService.class);
	}

	@Test
	public void registerPlayer() throws AlreadyRegisteredPlayerException, WalletException, ParseAnswerException {
		Balance balance = walletServiceClient.registerPlayer(13);
		assertNotNull(balance);
		assertEquals(BigDecimal.valueOf(0), balance.getAmount());
		assertEquals(13, balance.getPlayerId().intValue());
	}

	@Test
	public void depositBalance() throws PlayerNotFoundException, WalletException, ParseAnswerException {
		walletServiceClient.registerPlayer(13);
		Balance balance = walletServiceClient.depositBalance(13, BigDecimal.valueOf(50));

		assertNotNull(balance);
		assertEquals(BigDecimal.valueOf(50), balance.getAmount());
		assertEquals(13, balance.getPlayerId().intValue());
	}

	@Test
	public void withdrawBalance()
			throws PlayerNotFoundException, InsufficientFundsException, WalletException, ParseAnswerException {
		walletServiceClient.registerPlayer(13);
		walletServiceClient.depositBalance(13, BigDecimal.valueOf(50));
		Balance balance = walletServiceClient.withdrawBalance(13, BigDecimal.valueOf(20));

		assertNotNull(balance);
		assertEquals(BigDecimal.valueOf(30), balance.getAmount());
		assertEquals(13, balance.getPlayerId().intValue());
	}

	@Test
	public void getBalance() throws PlayerNotFoundException, WalletException, ParseAnswerException {
		walletServiceClient.registerPlayer(13);
		walletServiceClient.depositBalance(13, BigDecimal.valueOf(50));
		Balance balance = walletServiceClient.getBalance(13);

		assertNotNull(balance);
		assertEquals(BigDecimal.valueOf(50), balance.getAmount());
		assertEquals(13, balance.getPlayerId().intValue());
	}

	@Test(expected=PlayerNotFoundException.class)
	public void removeBalance() throws PlayerNotFoundException, WalletException, ParseAnswerException {
		walletServiceClient.registerPlayer(13);
		Boolean result = walletServiceClient.removeBalance(13);
		assertEquals(true, result);
		walletServiceClient.getBalance(13);
	}
}
