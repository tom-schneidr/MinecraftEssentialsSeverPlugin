package io.essentials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.plugin.java.JavaPlugin;

import io.essentials.commands.ClearInventoryCommand;
import io.essentials.commands.ConfirmCommand;
import io.essentials.commands.CraftCommand;
import io.essentials.commands.EnderchestCommand;
import io.essentials.commands.FeedCommand;
import io.essentials.commands.FlyCommand;
import io.essentials.commands.GamemodeCommand;
import io.essentials.commands.GodCommand;
import io.essentials.commands.HealCommand;
import io.essentials.commands.InvseeCommand;
import io.essentials.commands.KillCommand;
import io.essentials.commands.PingCommand;
import io.essentials.commands.PlayerTimeCommand;
import io.essentials.commands.PlaytimeCommand;
import io.essentials.commands.SmiteCommand;
import io.essentials.commands.TimeCommand;
import io.essentials.commands.TpToggleCommand;
import io.essentials.commands.TpaCommand;
import io.essentials.commands.TpacceptCommand;
import io.essentials.commands.TpahereCommand;
import io.essentials.commands.TpallCommand;
import io.essentials.commands.TpdenyCommand;
import io.essentials.commands.VanishCommand;
import io.essentials.data.ConfigManager;
import io.essentials.data.MySQL;
import io.essentials.listeners.FalldamageListener;
import io.essentials.listeners.InventoryListener;
import io.essentials.listeners.LoginListener;
import io.essentials.listeners.LogoutListener;

/*
 * essentials java plugin
 */
public class Essentials extends JavaPlugin
{
  private static Essentials instance;
  // Recipient of tp request is the Key, sender is the Value
  private static Map<UUID,UUID> tpa_requests = new HashMap<>();
  private static Map<UUID,UUID> tpahere_requests = new HashMap<>();
  // Players that are currently vanished
  private static List<UUID> vanished = new ArrayList<>();
  // Player that just joined
  private static List<UUID> loginList = new ArrayList<>();
  // Players with pending confirmation
  private static Map<Map<UUID,UUID>,String> confirmList = new HashMap<>();
  // Players that are currently invseeing another players inventory
  private static Map<UUID,UUID> invseeList = new HashMap<>();
  // Players time they logged on
  private static Map<UUID,Long> loginTimeList = new HashMap<>();
  // Players that have toggled Tps off
  private static List<UUID> tpToggledOffList = new ArrayList<>();


  public static MySQL mysql;


  public static ConfigManager configManager = new ConfigManager();

  public void onEnable() {
    instance = this;
    //Setup
    configManager.setupFiles();
    mysql = new MySQL();
    mysql.setup();

    // Commands
    getCommand("feed").setExecutor(new FeedCommand());
    getCommand("god").setExecutor(new GodCommand());
    getCommand("heal").setExecutor(new HealCommand());
    getCommand("fly").setExecutor(new FlyCommand());
    getCommand("craft").setExecutor(new CraftCommand());
    getCommand("enderchest").setExecutor(new EnderchestCommand());
    getCommand("tpa").setExecutor(new TpaCommand());
    getCommand("tpahere").setExecutor(new TpahereCommand());
    getCommand("tpaccept").setExecutor(new TpacceptCommand());
    getCommand("tpdeny").setExecutor(new TpdenyCommand());
    getCommand("invsee").setExecutor(new InvseeCommand());
    getCommand("gamemode").setExecutor(new GamemodeCommand());
    getCommand("vanish").setExecutor(new VanishCommand());
    getCommand("kill").setExecutor(new KillCommand());
    getCommand("tpall").setExecutor(new TpallCommand());
    getCommand("confirm").setExecutor(new ConfirmCommand());
    getCommand("time").setExecutor(new TimeCommand());
    getCommand("ptime").setExecutor(new PlayerTimeCommand());
    getCommand("smite").setExecutor(new SmiteCommand());
    getCommand("ping").setExecutor(new PingCommand());
    getCommand("clearinventory").setExecutor(new ClearInventoryCommand());
    getCommand("playtime").setExecutor(new PlaytimeCommand());
    getCommand("tptoggle").setExecutor(new TpToggleCommand());

    // Listeners
    getServer().getPluginManager().registerEvents(new LoginListener(), this);
    getServer().getPluginManager().registerEvents(new LogoutListener(), this);
    getServer().getPluginManager().registerEvents(new FalldamageListener(), this);
    getServer().getPluginManager().registerEvents(new InventoryListener(), this);
  }

  public static Essentials getInstance() {
    return instance;
  }

  public static Map<UUID,UUID> getTpaRequests() {
    return tpa_requests;
  }

  public static Map<UUID,UUID> getTpahereRequests() {
    return tpahere_requests;
  }

  public static List<UUID> getVanished() {
    return vanished;
  }

  public static List<UUID> getLogin() {
    return loginList;
  }

  public static Map<Map<UUID,UUID>,String> getConfirmList() {
    return confirmList;
  }

  public static Map<UUID,UUID> getInvseeList() {
    return invseeList;
  }

  public static Map<UUID,Long> getLoginTimeList() {
    return loginTimeList;
  }

  public static List<UUID> getTpToggledOffList() {
    return tpToggledOffList;
  }
}
