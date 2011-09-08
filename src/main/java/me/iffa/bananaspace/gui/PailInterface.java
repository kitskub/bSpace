// Package Declaration
package me.iffa.bananaspace.gui;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.config.SpaceConfig;

// Bukkit Imports
import org.bukkit.World;
import org.bukkit.util.config.Configuration;

// Java Imports
import java.awt.Desktop;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import java.io.IOException;

/**
 * Interface for Pail, a Bukkit GUI.
 * 
 * @author iffa
 */
public class PailInterface extends javax.swing.JPanel {
    // Variables
    public static Configuration config = SpaceConfig.getConfig();
    private BananaSpace plugin;

    /**
     * Constructor for PailInterface.
     * 
     * @param plugin BananaSpace
     */
    public PailInterface(BananaSpace plugin) {
        this.plugin = plugin;
        initComponents();
        readConfigs();
    }

    /**
     * Reads the configuration files and changes the interface values to represent the configuration values.
     */
    private void readConfigs() {
        CheckBoxHelmet.setSelected(config.getBoolean("global.givehelmet", false));
        CheckBoxSuit.setSelected(config.getBoolean("global.givesuit", false));
        ArmorTypeBox.setText(config.getString("global.armortype", "iron"));
        HelmetBlockIdBox.setText(config.getString("global.blockid", "86"));
        SpaceList.setModel(new DefaultListModel());
        for (World world : BananaSpace.worldHandler.getSpaceWorlds()) {
            ((DefaultListModel) SpaceList.getModel()).addElement(world.getName());
            BananaSpace.debugLog("Added spaceworld '" + world.getName() + "' to list of spaceworlds (Pail).");
        }
    }

    /**
     * Loads the configuration settings for the world selected in the list. Only one safety check is made (to make sure a world that doesn't exist is not loaded).
     * 
     * @param worldname Spaceworld name
     */
    private void loadSpaceListConfig(String worldname) {
        if (config.getProperty("worlds." + worldname) == null) {
            BananaSpace.log.warning(BananaSpace.prefix + " A world with the name '" + worldname + "' does not exist in the config!");
            return;
        }
        Settings_WorldName.setText(worldname);
        Settings_Planets.setSelected(config.getBoolean("worlds." + worldname + ".generation.generateplanets", true));
        Settings_Asteroids.setSelected(config.getBoolean("worlds." + worldname + "generation.generateasteroids", true));
        Settings_GlowstoneChance.setValue(config.getInt("worlds." + worldname + ".generation.glowstonechance", 1));
        Settings_StoneChance.setValue(config.getInt("worlds." + worldname + ".generation.stonechance", 3));
        Settings_Night.setSelected(config.getBoolean("worlds." + worldname + ".alwaysnight", true));
        Settings_HelmetRequired.setSelected(config.getBoolean("worlds." + worldname + ".helmet.required", false));
        Settings_SuitRequired.setSelected(config.getBoolean("worlds." + worldname + ".suit.required", false));
        Settings_Weather.setSelected(config.getBoolean("worlds." + worldname + ".weather", false));
        Settings_Nether.setSelected(config.getBoolean("worlds." + worldname + ".nethermode", false));
        Settings_RoomHeight.setValue(config.getInt("worlds." + worldname + ".breathingarea.maxroomheight", 5));
        Settings_Neutral.setSelected(config.getBoolean("worlds." + worldname + ".neutralmobs", true));
        Settings_Hostile.setSelected(config.getBoolean("worlds." + worldname + ".hostilemobs", false));
        BananaSpace.debugLog("Loaded settings for spaceworld '" + worldname + "'.");
    }

    /**
     * Saves the configuration settings for the world selected in the list. Only one safety check is made (to make sure a world that doesn't exist is not saved).
     * 
     * @param worldname Spaceworld name
     */
    private void saveSpaceListConfig(String worldname) {
        if (config.getProperty("worlds." + worldname) == null) {
            BananaSpace.log.warning(BananaSpace.prefix + " A world with the name '" + worldname + "' does not exist in the config!");
            return;
        }
        config.setProperty("worlds." + worldname + ".generation.generateplanets", Settings_Planets.isSelected());
        config.setProperty("worlds." + worldname + ".generation.generateasteroids", Settings_Asteroids.isSelected());
        config.setProperty("worlds." + worldname + ".generation.glowstonechance", (Integer) Settings_GlowstoneChance.getValue());
        config.setProperty("worlds." + worldname + ".generation.stonechance", (Integer) Settings_StoneChance.getValue());
        config.setProperty("worlds." + worldname + ".weather", Settings_Weather.isSelected());
        config.setProperty("worlds." + worldname + ".hostilemobs", Settings_Hostile.isSelected());
        config.setProperty("worlds." + worldname + ".neutralmobs", Settings_Neutral.isSelected());
        config.setProperty("worlds." + worldname + ".alwaysnight", Settings_Night.isSelected());
        config.setProperty("worlds." + worldname + ".nethermode", Settings_Nether.isSelected());
        config.setProperty("worlds." + worldname + ".suit.required", Settings_SuitRequired.isSelected());
        config.setProperty("worlds" + worldname + ".helmet.required", Settings_HelmetRequired.isSelected());
        config.save();
        BananaSpace.debugLog("Saved settings for spaceworld '" + worldname + "'.");
    }

    /**
     * Adds a world to the list in the GUI.
     * 
     * @param worldname World name
     */
    public void addSpaceList(String worldname) {
        ((DefaultListModel) SpaceList.getModel()).addElement(worldname);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GlobalSettings = new javax.swing.JPanel();
        CheckBoxHelmet = new javax.swing.JCheckBox();
        CheckBoxSuit = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        HelmetBlockIdBox = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ArmorTypeBox = new javax.swing.JTextField();
        SpoutEnabled = new javax.swing.JCheckBox();
        ResetButton = new javax.swing.JButton();
        SaveButton = new javax.swing.JButton();
        SpaceWorlds = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SpaceList = new javax.swing.JList();
        CreateWorldButton = new javax.swing.JButton();
        DeleteWorldButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        NewWorld = new javax.swing.JTextField();
        Settings = new javax.swing.JPanel();
        Settings_Save = new javax.swing.JButton();
        Settings_Reset = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Settings_WorldName = new javax.swing.JLabel();
        Settings_Planets = new javax.swing.JCheckBox();
        Settings_Asteroids = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        Settings_StoneChance = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        Settings_GlowstoneChance = new javax.swing.JSpinner();
        Settings_HelmetRequired = new javax.swing.JCheckBox();
        Settings_SuitRequired = new javax.swing.JCheckBox();
        Settings_Weather = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        Settings_Nether = new javax.swing.JCheckBox();
        Settings_Hostile = new javax.swing.JCheckBox();
        Settings_Neutral = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        Settings_RoomHeight = new javax.swing.JSpinner();
        Settings_Night = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();

        setFont(new java.awt.Font("Arial", 0, 11));
        setInheritsPopupMenu(true);
        setMaximumSize(new java.awt.Dimension(850, 450));
        setMinimumSize(new java.awt.Dimension(850, 450));
        setPreferredSize(new java.awt.Dimension(850, 450));
        setRequestFocusEnabled(false);

        GlobalSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Global Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        GlobalSettings.setToolTipText("Global settings. These affect each and every spaceworld.");

        CheckBoxHelmet.setText("Give helmet");
        CheckBoxHelmet.setToolTipText("Selected if a spacehelmet should be given when going to a space world.");
        CheckBoxHelmet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxHelmetActionPerformed(evt);
            }
        });

        CheckBoxSuit.setText("Give suit");
        CheckBoxSuit.setToolTipText("Selected if a spacehelmet should be given when going to a space world.");
        CheckBoxSuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxSuitActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel5.setText("Helmet block id:");
        jLabel5.setToolTipText("The block id that will be the helmet.");

        HelmetBlockIdBox.setText("86");
        HelmetBlockIdBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelmetBlockIdBoxActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel4.setText("Suit armortype:");

        ArmorTypeBox.setText("iron");
        ArmorTypeBox.setToolTipText("The spacesuit armortype. Can be diamond, chainmail, gold, iron or leather.");
        ArmorTypeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArmorTypeBoxActionPerformed(evt);
            }
        });

        SpoutEnabled.setSelected(true);
        SpoutEnabled.setText("Use Spout features");
        SpoutEnabled.setToolTipText("Checked if you want to enable Spout features.");
        SpoutEnabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SpoutEnabledActionPerformed(evt);
            }
        });

        ResetButton.setText("Revert");
        ResetButton.setToolTipText("Not happy with the changes? Just press this button and all your changes will be reset!");
        ResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButtonActionPerformed(evt);
            }
        });

        SaveButton.setText("Save");
        SaveButton.setToolTipText("Saves the changes and reloads the server for changes to take effect.");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GlobalSettingsLayout = new javax.swing.GroupLayout(GlobalSettings);
        GlobalSettings.setLayout(GlobalSettingsLayout);
        GlobalSettingsLayout.setHorizontalGroup(
            GlobalSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GlobalSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GlobalSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GlobalSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(GlobalSettingsLayout.createSequentialGroup()
                            .addComponent(CheckBoxHelmet, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(HelmetBlockIdBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(24, 24, 24))
                        .addGroup(GlobalSettingsLayout.createSequentialGroup()
                            .addComponent(CheckBoxSuit)
                            .addGap(12, 12, 12)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(ArmorTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(GlobalSettingsLayout.createSequentialGroup()
                        .addComponent(SpoutEnabled)
                        .addGap(115, 115, 115)))
                .addGroup(GlobalSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ResetButton)
                    .addGroup(GlobalSettingsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SaveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)))
                .addContainerGap())
        );
        GlobalSettingsLayout.setVerticalGroup(
            GlobalSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GlobalSettingsLayout.createSequentialGroup()
                .addGroup(GlobalSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckBoxHelmet, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(HelmetBlockIdBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResetButton))
                .addGroup(GlobalSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GlobalSettingsLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(GlobalSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CheckBoxSuit, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(ArmorTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SpoutEnabled, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GlobalSettingsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SaveButton)
                        .addContainerGap())))
        );

        SpaceWorlds.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Spaceworlds", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        SpaceWorlds.setToolTipText("Panel with buttons to create and delete spaceworlds..");

        SpaceList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        SpaceList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "No spaceworlds" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        SpaceList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        SpaceList.setToolTipText("All spaceworlds. Selecting a spaceworld on the list opens its settings on the right.");
        SpaceList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                SpaceListValueChanged(evt);
            }
        });
        SpaceList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SpaceListFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SpaceListFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(SpaceList);

        CreateWorldButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        CreateWorldButton.setText("Create");
        CreateWorldButton.setToolTipText("Creates a new spaceworld with the name on the box above.");
        CreateWorldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateWorldButtonActionPerformed(evt);
            }
        });

        DeleteWorldButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        DeleteWorldButton.setText("Delete");
        DeleteWorldButton.setToolTipText("Select a spaceworld from the list and click this button to delete the spaceworld. The world data will be saved.");
        DeleteWorldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteWorldButtonActionPerformed(evt);
            }
        });

        NewWorld.setText("World name");
        NewWorld.setToolTipText("Specify a name for the spaceworld. Must not be a world already, and must not be empty or contain spaces.");

        javax.swing.GroupLayout SpaceWorldsLayout = new javax.swing.GroupLayout(SpaceWorlds);
        SpaceWorlds.setLayout(SpaceWorldsLayout);
        SpaceWorldsLayout.setHorizontalGroup(
            SpaceWorldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SpaceWorldsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SpaceWorldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DeleteWorldButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SpaceWorldsLayout.createSequentialGroup()
                        .addGroup(SpaceWorldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(NewWorld, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                            .addComponent(CreateWorldButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(60, 60, 60)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        SpaceWorldsLayout.setVerticalGroup(
            SpaceWorldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SpaceWorldsLayout.createSequentialGroup()
                .addGroup(SpaceWorldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SpaceWorldsLayout.createSequentialGroup()
                        .addComponent(NewWorld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SpaceWorldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SpaceWorldsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addGroup(SpaceWorldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SpaceWorldsLayout.createSequentialGroup()
                                        .addComponent(DeleteWorldButton)
                                        .addGap(17, 17, 17))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SpaceWorldsLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(147, 147, 147))))
                            .addGroup(SpaceWorldsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CreateWorldButton)))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        Settings.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Spaceworld Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        Settings.setToolTipText("Settings for the currently selected spaceworld in the list.");
        Settings.setMaximumSize(new java.awt.Dimension(200, 32767));
        Settings.setPreferredSize(new java.awt.Dimension(200, 250));

        Settings_Save.setText("Save");
        Settings_Save.setToolTipText("Saves the changes made to the spaceworld's settings.");
        Settings_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Settings_SaveActionPerformed(evt);
            }
        });

        Settings_Reset.setText("Revert");
        Settings_Reset.setToolTipText("Resets the changes made to the world's settings.");
        Settings_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Settings_ResetActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setText("Currently editing:");

        Settings_WorldName.setText("nothing");

        Settings_Planets.setSelected(true);
        Settings_Planets.setText("Generate planets");
        Settings_Planets.setToolTipText("Checked if planets are generated.");
        Settings_Planets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Settings_PlanetsActionPerformed(evt);
            }
        });

        Settings_Asteroids.setSelected(true);
        Settings_Asteroids.setText("Generate asteroids");
        Settings_Asteroids.setToolTipText("Asteroids are small patches of glowstone and stone. The frequency can be changed.");

        jLabel3.setText("Stone-asteroid chance:");

        Settings_StoneChance.setModel(new javax.swing.SpinnerNumberModel(3, 1, 200, 1));
        Settings_StoneChance.setToolTipText("The stone-asteroid spawning chance. From 1 to 200.");

        jLabel6.setText("Glowstone-asteroid chance:");

        Settings_GlowstoneChance.setModel(new javax.swing.SpinnerNumberModel(1, 1, 200, 1));
        Settings_GlowstoneChance.setToolTipText("The glowstone-asteroid spawning chance. From 1 to 200.");

        Settings_HelmetRequired.setText("Helmet required");
        Settings_HelmetRequired.setToolTipText("Checked if helmets are required in a spaceworld to survive.");

        Settings_SuitRequired.setText("Suit required");
        Settings_SuitRequired.setToolTipText("Checked if suits are required in a spaceworld to survive.");

        Settings_Weather.setText("Weather");
        Settings_Weather.setToolTipText("Selected if weather is allowed in the spaceworlds.");

        jSeparator1.setForeground(new java.awt.Color(210, 213, 215));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        Settings_Nether.setText("Nethermode");
        Settings_Nether.setToolTipText("Checked if the spaceworld should be like nether. (red fog and nether mobs)");

        Settings_Hostile.setText("Hostile mobs");
        Settings_Hostile.setToolTipText("Selected if hostile mobs are allowed to spawn.");
        Settings_Hostile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Settings_HostileActionPerformed(evt);
            }
        });

        Settings_Neutral.setSelected(true);
        Settings_Neutral.setText("Neutral mobs");
        Settings_Neutral.setToolTipText("Selected if neutral mobs are allowed to spawn.");

        jLabel7.setText("Maximum room height:");

        Settings_RoomHeight.setModel(new javax.swing.SpinnerNumberModel(5, 1, 64, 1));
        Settings_RoomHeight.setToolTipText("The maximum height of a room where you can breathe in. In a zone like this, no helmets or suits are required.");

        Settings_Night.setSelected(true);
        Settings_Night.setText("Always night");
        Settings_Night.setToolTipText("Checked if it should always be night in the spaceworld.");
        Settings_Night.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Settings_NightActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SettingsLayout = new javax.swing.GroupLayout(Settings);
        Settings.setLayout(SettingsLayout);
        SettingsLayout.setHorizontalGroup(
            SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SettingsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SettingsLayout.createSequentialGroup()
                        .addComponent(Settings_Save)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Settings_Reset))
                    .addGroup(SettingsLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Settings_WorldName, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SettingsLayout.createSequentialGroup()
                        .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Settings_SuitRequired)
                            .addComponent(Settings_HelmetRequired)
                            .addComponent(Settings_Planets)
                            .addGroup(SettingsLayout.createSequentialGroup()
                                .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Settings_Asteroids)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Settings_StoneChance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SettingsLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Settings_GlowstoneChance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Settings_Night)
                            .addComponent(Settings_Weather)
                            .addComponent(Settings_Nether)
                            .addComponent(Settings_Neutral)
                            .addComponent(Settings_Hostile)
                            .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(Settings_RoomHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)))))
                .addGap(107, 107, 107))
        );
        SettingsLayout.setVerticalGroup(
            SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SettingsLayout.createSequentialGroup()
                .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Settings_Save)
                    .addComponent(Settings_Reset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Settings_WorldName))
                .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SettingsLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(Settings_Planets, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Settings_Asteroids, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(Settings_StoneChance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(Settings_GlowstoneChance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Settings_HelmetRequired, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Settings_SuitRequired, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(SettingsLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)))
                    .addGroup(SettingsLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(Settings_Weather, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(Settings_Night, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Settings_Nether, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(Settings_Neutral, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(Settings_Hostile, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Settings_RoomHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)))
                .addContainerGap())
        );

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bananaspace.png"))); // NOI18N
        jLabel8.setText("  ");
        jLabel8.setOpaque(true);
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SpaceWorlds, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(Settings, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(GlobalSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(399, 399, 399))
            .addGroup(layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(jLabel8)
                .addContainerGap(447, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GlobalSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SpaceWorlds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Settings, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void CheckBoxHelmetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxHelmetActionPerformed
    }//GEN-LAST:event_CheckBoxHelmetActionPerformed

    private void CheckBoxSuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxSuitActionPerformed
    }//GEN-LAST:event_CheckBoxSuitActionPerformed

    private void ArmorTypeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArmorTypeBoxActionPerformed
    }//GEN-LAST:event_ArmorTypeBoxActionPerformed

    private void HelmetBlockIdBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelmetBlockIdBoxActionPerformed
    }//GEN-LAST:event_HelmetBlockIdBoxActionPerformed

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed

        config.setProperty("global.givesuit", CheckBoxSuit.isSelected());
        config.setProperty("global.givehelmet", CheckBoxHelmet.isSelected());
        config.setProperty("global.armortype", ArmorTypeBox.getText());
        config.setProperty("global.blockid", Integer.parseInt(HelmetBlockIdBox.getText()));
        config.setProperty("global.usespout", SpoutEnabled.isSelected());
        config.save();
        JOptionPane.showMessageDialog(this, "Your general settings have been saved!", "Settings saved!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_SaveButtonActionPerformed

    private void ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetButtonActionPerformed
        JOptionPane.showMessageDialog(this, "Your changes have been reset!", "Changes reset!", JOptionPane.INFORMATION_MESSAGE);
        readConfigs();
    }//GEN-LAST:event_ResetButtonActionPerformed

private void CreateWorldButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateWorldButtonActionPerformed
    String worldname = NewWorld.getText().trim();
    if (worldname.equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "The world name cannot be empty!", "Invalid world name", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (plugin.getServer().getWorld(worldname) != null) {
        JOptionPane.showMessageDialog(this, "A world with the given name already exists!", "Invalid world name", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (worldname.contains(" ")) {
        JOptionPane.showMessageDialog(this, "The world name cannot contain spaces!", "Invalid world name", JOptionPane.WARNING_MESSAGE);
        return;
    }
    BananaSpace.scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

        public void run() {
            BananaSpace.worldHandler.createSpaceWorld(plugin, NewWorld.getText().trim(), false);
        }
    }, 1L);
    BananaSpace.debugLog("Created spaceworld '" + worldname + "' through Pail.");
    JOptionPane.showMessageDialog(this, "A new spaceworld called '" + worldname + "' has been created!", "Spaceworld created", JOptionPane.INFORMATION_MESSAGE);
    NewWorld.setText("World name");
}//GEN-LAST:event_CreateWorldButtonActionPerformed

private void DeleteWorldButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteWorldButtonActionPerformed

    if (SpaceList.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "You need to choose a world to delete from the list!", "Select a world", JOptionPane.WARNING_MESSAGE);
        return;
    }
    String s = (String) SpaceList.getModel().getElementAt(SpaceList.getSelectedIndex());
    int n = JOptionPane.showConfirmDialog(
            this,
            "Deleting a spaceworld will only remove the world from the spaceworlds-list and unload it. The world itself will be saved. Do you want to continue?",
            "Delete a spaceworld",
            JOptionPane.YES_NO_OPTION);
    if (n == JOptionPane.YES_OPTION) {
        BananaSpace.worldHandler.removeSpaceWorld(plugin, s, false);
        ((DefaultListModel) SpaceList.getModel()).remove(SpaceList.getSelectedIndex());
        JOptionPane.showMessageDialog(this, "The spaceworld was deleted successfully!", "Spaceworld deleted", JOptionPane.INFORMATION_MESSAGE);
    }
}//GEN-LAST:event_DeleteWorldButtonActionPerformed

private void Settings_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Settings_ResetActionPerformed

    if (SpaceList.getSelectedIndex() != -1) {
        String worldname = (String) SpaceList.getModel().getElementAt(SpaceList.getSelectedIndex());
        loadSpaceListConfig(worldname);
        JOptionPane.showMessageDialog(this, "Your changes have been reset!", "Changes reset!", JOptionPane.INFORMATION_MESSAGE);
    }
}//GEN-LAST:event_Settings_ResetActionPerformed

private void Settings_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Settings_SaveActionPerformed

    String worldname = (String) SpaceList.getModel().getElementAt(SpaceList.getSelectedIndex());
    saveSpaceListConfig(worldname);
    JOptionPane.showMessageDialog(this, "The spaceworld '" + worldname + "' has been saved. Please note that most changes take effect after reloading the server.", "Spaceworld saved!", JOptionPane.INFORMATION_MESSAGE);
}//GEN-LAST:event_Settings_SaveActionPerformed

private void Settings_PlanetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Settings_PlanetsActionPerformed
}//GEN-LAST:event_Settings_PlanetsActionPerformed

private void SpoutEnabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SpoutEnabledActionPerformed
}//GEN-LAST:event_SpoutEnabledActionPerformed

private void SpaceListFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SpaceListFocusLost
}//GEN-LAST:event_SpaceListFocusLost

private void SpaceListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SpaceListFocusGained
}//GEN-LAST:event_SpaceListFocusGained

private void SpaceListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_SpaceListValueChanged

    if (SpaceList.getSelectedIndex() != -1) {
        String worldname = (String) SpaceList.getModel().getElementAt(SpaceList.getSelectedIndex());
        loadSpaceListConfig(worldname);
    }
}//GEN-LAST:event_SpaceListValueChanged

private void Settings_HostileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Settings_HostileActionPerformed
}//GEN-LAST:event_Settings_HostileActionPerformed

private void Settings_NightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Settings_NightActionPerformed
}//GEN-LAST:event_Settings_NightActionPerformed

private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
    try {
        Desktop.getDesktop().browse(java.net.URI.create("http://forums.bukkit.org/threads/32546/"));
    } catch (IOException ex) {
        BananaSpace.log.warning(BananaSpace.prefix + " Something went wrong while opening a page on your web browser!");
    }
}//GEN-LAST:event_jLabel8MouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ArmorTypeBox;
    private javax.swing.JCheckBox CheckBoxHelmet;
    private javax.swing.JCheckBox CheckBoxSuit;
    private javax.swing.JButton CreateWorldButton;
    private javax.swing.JButton DeleteWorldButton;
    private javax.swing.JPanel GlobalSettings;
    private javax.swing.JTextField HelmetBlockIdBox;
    private javax.swing.JTextField NewWorld;
    private javax.swing.JButton ResetButton;
    private javax.swing.JButton SaveButton;
    private javax.swing.JPanel Settings;
    private javax.swing.JCheckBox Settings_Asteroids;
    private javax.swing.JSpinner Settings_GlowstoneChance;
    private javax.swing.JCheckBox Settings_HelmetRequired;
    private javax.swing.JCheckBox Settings_Hostile;
    private javax.swing.JCheckBox Settings_Nether;
    private javax.swing.JCheckBox Settings_Neutral;
    private javax.swing.JCheckBox Settings_Night;
    private javax.swing.JCheckBox Settings_Planets;
    private javax.swing.JButton Settings_Reset;
    private javax.swing.JSpinner Settings_RoomHeight;
    private javax.swing.JButton Settings_Save;
    private javax.swing.JSpinner Settings_StoneChance;
    private javax.swing.JCheckBox Settings_SuitRequired;
    private javax.swing.JCheckBox Settings_Weather;
    private javax.swing.JLabel Settings_WorldName;
    private javax.swing.JList SpaceList;
    private javax.swing.JPanel SpaceWorlds;
    private javax.swing.JCheckBox SpoutEnabled;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
