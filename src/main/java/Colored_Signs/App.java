package Colored_Signs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        Listener listener = new Listener(){

            @EventHandler
            public void signChange(SignChangeEvent event) {
                if (!event.getPlayer().hasPermission("ColoredSigns.format")) {
                    return;
                }

                String[] lines = event.getLines();

                final String formatChar = "§";

                final String colors = "0123456789abcdef";
                final String formats = "klmnor";
                final String concat = colors + formats;

                final String alternating = "(&[" + concat + "]+:)|&r";
                final String pattern = "&[" + concat + "]";

                int i = 0;

                String[] formatted = customSyntax.alternate(lines, alternating);

                for (String line : formatted) {
                    event.setLine(i, line);
                    i++;
                }

                i = 0;
                Pattern lookfor = Pattern.compile(pattern);

                for (String line : lines) {
                    if (!line.isEmpty() && line.length() > 2) {
                        Matcher matcher = lookfor.matcher(line);

                        String newLine = line;

                        while (matcher.find()) {
                            String found = matcher.group();
                            int start = matcher.start();
                            int finish = matcher.end();

                            newLine = newLine.substring(0, start) + formatChar + found.charAt(1) + newLine.substring(finish);
                        }

                        event.setLine(i, newLine);
                    }

                    i++;
                }
                
            }
        };

        this.getServer().getPluginManager().registerEvents(listener, this);

        getLogger().info("ColoredSigns initialized");
    }

    @Override
    public void onDisable() {
        
    }
}
