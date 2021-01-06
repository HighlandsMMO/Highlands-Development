package highlands.highlandscore;


import kr.entree.spigradle.annotations.SpigotPlugin;

import highlands.highlandscore.api.HighlandsCoreApi;
import highlands.highlandscore.commands.HighlandsCommandHandler;
import highlands.highlandscore.infrastructure.HighlandsCoreDB;
import highlands.highlandscore.settings.Settings;
import highlands.highlandscore.utilities.AdvancedLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

@SpigotPlugin
public class HighlandsCore extends JavaPlugin {
    //Settings
    private Settings settings;
    public Settings getSettings() {
        return settings;
    }

    //Loggers
    private AdvancedLogger advancedLogger;
    public AdvancedLogger getAdvancedLogger() {
        return advancedLogger;
    }

    //Database
    private HighlandsCoreDB highlandsCoreDB;
    public HighlandsCoreDB getPlayerDataDB() {
        return highlandsCoreDB;
    }

    private HighlandsCoreApi highlandsCoreApi;
    public HighlandsCoreApi getHighlandsCoreApi() { return highlandsCoreApi; }

    @Override
    public void onEnable() {
        //Settings
        saveDefaultConfig();
        settings = new Settings(this);

        //Loggers
        advancedLogger = new AdvancedLogger(this);

        //DB
        highlandsCoreDB = new HighlandsCoreDB();
        if (settings.getUseMysql())
            highlandsCoreDB.setupMysql(this);
        else
            highlandsCoreDB.setupSQLite(this);
        try {
            highlandsCoreDB.connect();
        } catch (SQLException e) {
            advancedLogger.Log(Level.SEVERE, "HighlandsCore", e.getMessage());
        }

        //Api
        highlandsCoreApi = new HighlandsCoreApi(this);

        //Command
        registerCommands();
    }

    @Override
    public void onDisable() {
        try {
            highlandsCoreDB.disconnect();
        } catch (SQLException e) {
            advancedLogger.Log(Level.SEVERE, "HighlandsCore", e.getMessage());
        }
    }

    public void reloadPluginConfig() {
        this.reloadConfig();
    }

    private void registerCommands() {
        this.getCommand("hlc").setExecutor(new HighlandsCommandHandler());
    }
}
