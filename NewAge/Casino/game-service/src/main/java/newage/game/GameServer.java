package newage.game;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Module;

import newage.common.FileProperties;
import newage.common.ResourceProperties;
import newage.common.server.RestServer;
import newage.common.server.bind.DatabaseModule;

public class GameServer {
	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		
		if ((args != null) && (args.length != 0) && (StringUtils.isNotEmpty(args[0]))){
			properties = new FileProperties(args[0]);
		}else{
			properties = new ResourceProperties("game.properties");
		}
		
		RestServer.builder().addModules(getModules()).addModule(new OtherServiceBinder()).runServer(properties);
	}

	protected static Collection<Module> getModules() {
		return Arrays.asList(new Binder(), new DatabaseModule());
	}
}
