package newage.game;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import com.google.inject.Module;
import newage.wallet.api.BalanceImpl;
import newage.wallet.api.WalletService;

public class MockOtherServiceBinder implements Module {
	WalletService walletServiceMock = mock(WalletService.class);

	@Override
	public void configure(com.google.inject.Binder binder) {
		try {
			when(walletServiceMock.withdrawBalance(anyInt(), any(BigDecimal.class))).thenReturn(new BalanceImpl(TestUtils.PLAYER_ID, BigDecimal.valueOf(1000)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		binder.bind(WalletService.class).toInstance(walletServiceMock);
	}
}