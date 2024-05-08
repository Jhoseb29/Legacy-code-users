package jalau.cis.services;

import jalau.cis.services.mongo.MongoDBUsersService;
import jalau.cis.services.mybatis.MySqlDBUsersService;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.FileInputStream;

public class ServicesFacade {
    private static ServicesFacade instance;


    private boolean configured = false;
    private UsersService dbService;

    public static synchronized ServicesFacade getInstance () {
        if (instance == null) {
            instance = new ServicesFacade();
            Logger.getRootLogger().setLevel(Level.ERROR);
            BasicConfigurator.configure();
        }
        return instance;
    }
    private ServicesFacade() {
    }

    public UsersService getUsersService() throws Exception{
        checkInit();
        return dbService;
    }

    private void checkInit() throws Exception{
        if (!configured) throw new Exception("Facade is not configured....");
    }

    public synchronized void init(String type, String configurationFile) throws Exception {
        if (configured) throw new Exception("Facade is already configured....");
        try {
            if (type.equals("mysql")) {
                dbService = new MySqlDBUsersService(new FileInputStream(configurationFile));
                configured = true;
                return;
            }

            if (type.equals("mongo")) {
                dbService = new MongoDBUsersService(new FileInputStream(configurationFile));
                configured = true;
                return;
            }
            throw new Exception("Cannot connect to database. Unknown type");
        }
        catch (Exception ex) {
            System.out.printf("Cannot connect to DB [%s]\n", ex.getMessage());
            configured = false;
            throw ex;
        }
    }

}
