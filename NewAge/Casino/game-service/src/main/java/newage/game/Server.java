package newage.game;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import com.google.inject.Module;

import newage.common.server.RestServer;
import newage.common.server.bind.DatabaseModule;

public class Server {
	public static void main(String[] args) throws Exception {
		RestServer.builder().addModules(getModules()).addModule(new OtherServiceBinder()).runServer(new Properties());
	}

	protected static Collection<Module> getModules() {
		return Arrays.asList(new Binder(), new DatabaseModule());
	}
}
