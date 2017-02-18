package newage.game.rest;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class BetDAO extends BaseDaoImpl<BetDBImpl, Integer> {
	@Inject
	public BetDAO(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, BetDBImpl.class);
	}

	public void initialPrepareData() throws SQLException {
		TableUtils.createTableIfNotExists(connectionSource, BetDBImpl.class);
	}

}
