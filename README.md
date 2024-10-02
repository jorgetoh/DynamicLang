Connect to the API:
```java
private void loadDynamicLang() {
    DynamicLangAccessor accessor = getServer().getServicesManager().load(DynamicLangAccessor.class);
    if (accessor != null) {
        this.dynamicLang = accessor.get(this);
    }

    if (dynamicLang == null) {
        getLogger().severe("DynamicLang API not found! Shutting down the server.");
        getServer().shutdown();
    }
    dynamicLang.register(getName());
}
```
Lang files should be loaded into the plugin folder before registering the plugin.

Dynamically load all lang files:
> This only works with vanilla spigot for some reason, does not work with paper.
```java
public void loadLangFiles() {
    final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

    if(jarFile.isFile()) {
        final JarFile jar;
        try {
            jar = new JarFile(jarFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final Enumeration<JarEntry> entries = jar.entries();
        while(entries.hasMoreElements()) {
            final String name = entries.nextElement().getName();
            if (name.startsWith("lang/") && !name.equals("lang/")) {
                loadFile(name);
            }
        }
        try {
            jar.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

private boolean loadFile(String name) {
    File configFile = new File(plugin.getDataFolder(), name);

    if(!configFile.exists()) {
        configFile.getParentFile().mkdirs();
        plugin.saveResource(name, false);
    }

    YamlConfiguration config = new YamlConfiguration();
    try {
        config.load(configFile);
    } catch (IOException | InvalidConfigurationException e) {
        return false;
    }
    return true;
}
```
