package newage.wallet.rest;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

@Singleton
public class WalletDAO extends BaseDaoImpl<BalanceDBImpl, Integer> {
	@Inject
	public WalletDAO(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, BalanceDBImpl.class);
	}

	public void initialPrepareData() throws SQLException {
		TableUtils.createTableIfNotExists(connectionSource, BalanceDBImpl.class);
	}

}
