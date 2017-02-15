package newage.game;

import org.mockito.Mockito;

import com.google.inject.Module;

import newage.wallet.api.WalletService;

public class MockOtherServiceBinder implements Module {
	WalletService walletServiceMock = Mockito.mock(WalletService.class);

	@Override
	public void configure(com.google.inject.Binder binder) {
		binder.bind(WalletService.class).toInstance(walletServiceMock);
	}
}