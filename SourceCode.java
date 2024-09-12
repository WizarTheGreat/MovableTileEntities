package plugin.movabletileentities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public final class MovableTileEntities extends JavaPlugin implements Listener {
    private static final String PISTON_TAG = "poweredPiston";
    List<Block> nonAir = new ArrayList<>();

    public boolean is20OrHigher() {
        String version = Bukkit.getBukkitVersion();
        String[] parts = version.split("[^0-9]+");
        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        return major > 1 || (major == 1 && minor >= 20);
    }

    @Override
    public void onEnable() {
        getLogger().info("ACTIVE");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
        Block block = e.getBlock();
        int power = block.getBlockPower();
        int one = 1;
        if (block.getType() == Material.PISTON || block.getType() == Material.STICKY_PISTON) {

            if (power > 0) {
                BlockData blockData = block.getBlockData();
                if (blockData instanceof Directional) {
                    Directional directional = (Directional) blockData;
                    Vector direction = directional.getFacing().getDirection();
                    Block oldBlock = block.getRelative(
                            direction.getBlockX(),
                            direction.getBlockY(),
                            direction.getBlockZ());
                    Block newBlock = block.getRelative(
                            direction.getBlockX() * 2,
                            direction.getBlockY() * 2,
                            direction.getBlockZ() * 2);
                                checkBlock(newBlock);
                                move(block, oldBlock, newBlock);
                    }
                    block.setMetadata(PISTON_TAG, new FixedMetadataValue(this, true));

            } else if (power == 0 && block.hasMetadata(PISTON_TAG)) {

                BlockData blockData = block.getBlockData();
                if (blockData instanceof Directional && block.getType() == Material.STICKY_PISTON) {
                    Directional directional = (Directional) blockData;
                    Vector direction = directional.getFacing().getDirection();
                    Block block1 = block.getRelative(direction.getBlockX(), direction.getBlockY(), direction.getBlockZ());
                    Block block2 = block.getRelative(
                            direction.getBlockX() * 2,
                            direction.getBlockY() * 2,
                            direction.getBlockZ() * 2);
                    move(block, block2, block1);

                }
                block.removeMetadata(PISTON_TAG, this);
                }
            }
        }

    public void move(Block piston, Block oldBlock, Block newBlock) {
        BlockState blockState = oldBlock.getState();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (newBlock.getType() == Material.AIR) {
                if (blockState instanceof Chest && oldBlock.getType() == Material.CHEST && !(((Chest) blockState).getInventory() instanceof DoubleChestInventory)) {
                    Inventory chestInventory = ((Chest) blockState).getInventory();
                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    piston.getState().update();
                    newBlock.setType(Material.CHEST);
                    newBlock.getState().update();

                    Directional directional2 = (Directional) newBlock.getBlockData();
                    directional2.setFacing(facing);
                    newBlock.setBlockData(directional2);

                    BlockState blockState2 = newBlock.getState();
                    Inventory chest2Inventory = ((Chest) blockState2).getInventory();
                    chest2Inventory.setContents(chestInventory.getContents());
                    newBlock.getState().update();
                } else if (blockState instanceof Dispenser && oldBlock.getType() == Material.DISPENSER && oldBlock.getBlockPower() == 0) {
                    Inventory dispenserInventory = ((Dispenser) blockState).getInventory();

                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    piston.getState().update();
                    newBlock.setType(Material.DISPENSER);
                    newBlock.getState().update();

                    Directional directional2 = (Directional) newBlock.getBlockData();
                    directional2.setFacing(facing);
                    newBlock.setBlockData(directional2);

                    BlockState blockState2 = newBlock.getState();
                    Inventory dispenser2Inventory = ((Dispenser) blockState2).getInventory();
                    dispenser2Inventory.setContents(dispenserInventory.getContents());
                    newBlock.getState().update();
                } else if (blockState instanceof Dropper && oldBlock.getType() == Material.DROPPER && oldBlock.getBlockPower() == 0) {
                    Inventory dropperInventory = ((Dropper) blockState).getInventory();

                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    piston.getState().update();
                    newBlock.setType(Material.DROPPER);
                    newBlock.getState().update();

                    Directional directional2 = (Directional) newBlock.getBlockData();
                    directional2.setFacing(facing);
                    newBlock.setBlockData(directional2);

                    BlockState blockState2 = newBlock.getState();
                    Inventory dropper2Inventory = ((Dropper) blockState2).getInventory();
                    dropper2Inventory.setContents(dropperInventory.getContents());
                    newBlock.getState().update();
                } else if (blockState instanceof Barrel && oldBlock.getType() == Material.BARREL && oldBlock.getBlockPower() == 0) {
                    Inventory barrelInventory = ((Barrel) blockState).getInventory();

                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    piston.getState().update();
                    newBlock.setType(Material.BARREL);
                    newBlock.getState().update();

                    Directional directional2 = (Directional) newBlock.getBlockData();
                    directional2.setFacing(facing);
                    newBlock.setBlockData(directional2);

                    BlockState blockState2 = newBlock.getState();
                    Inventory barrel2Inventory = ((Barrel) blockState2).getInventory();
                    barrel2Inventory.setContents(barrelInventory.getContents());
                    newBlock.getState().update();
                } else if (blockState instanceof Hopper && oldBlock.getType() == Material.HOPPER && oldBlock.getBlockPower() == 0) {
                    Inventory hopperInventory = ((Hopper) blockState).getInventory();

                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    piston.getState().update();
                    newBlock.setType(Material.HOPPER);
                    newBlock.getState().update();

                    Directional directional2 = (Directional) newBlock.getBlockData();
                    directional2.setFacing(facing);
                    newBlock.setBlockData(directional2);

                    BlockState blockState2 = newBlock.getState();
                    Inventory hopper2Inventory = ((Hopper) blockState2).getInventory();
                    hopper2Inventory.setContents(hopperInventory.getContents());
                    newBlock.getState().update();
                } else if (blockState instanceof Furnace && oldBlock.getType() == Material.FURNACE) {
                    Furnace originalFurnace = (Furnace) blockState;
                    ItemStack[] furnaceInventory = originalFurnace.getInventory().getContents();

                    short cookTime = originalFurnace.getCookTime();
                    int totalCT = originalFurnace.getCookTimeTotal();
                    short burnTime = originalFurnace.getBurnTime();

                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    newBlock.setType(Material.FURNACE);
                    BlockState blockState2 = newBlock.getState();
                    if (blockState2 instanceof Furnace) {
                        Furnace newFurnace = (Furnace) blockState2;

                        Directional directional2 = (Directional) newBlock.getBlockData();
                        directional2.setFacing(facing);
                        newBlock.setBlockData(directional2);

                        newFurnace.setBurnTime(burnTime);
                        newFurnace.setCookTimeTotal(totalCT);
                        newFurnace.setCookTime(cookTime);

                        newFurnace.update();
                        newFurnace.getInventory().setContents(furnaceInventory);
                    }
                } else if (blockState instanceof Smoker && oldBlock.getType() == Material.SMOKER) {
                    Smoker originalSmoker = (Smoker) blockState;
                    ItemStack[] smokerInventory = originalSmoker.getInventory().getContents();

                    short cookTime = originalSmoker.getCookTime();
                    int totalCT = originalSmoker.getCookTimeTotal();
                    short burnTime = originalSmoker.getBurnTime();

                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    newBlock.setType(Material.SMOKER);

                    BlockState blockState2 = newBlock.getState();
                    if (blockState2 instanceof Smoker) {
                        Smoker newSmoker = (Smoker) blockState2;

                        Directional directional2 = (Directional) newBlock.getBlockData();
                        directional2.setFacing(facing);
                        newBlock.setBlockData(directional2);

                        newSmoker.setCookTime(cookTime);
                        newSmoker.setCookTimeTotal(totalCT);
                        newSmoker.setBurnTime(burnTime);
                        newSmoker.update();
                        newSmoker.getInventory().setContents(smokerInventory);
                    }
                } else if (blockState instanceof BlastFurnace && oldBlock.getType() == Material.BLAST_FURNACE) {
                    BlastFurnace originalBFurnace = (BlastFurnace) blockState;
                    ItemStack[] furnaceInventory = originalBFurnace.getInventory().getContents();

                    short cookTime = originalBFurnace.getCookTime();
                    int totalCT = originalBFurnace.getCookTimeTotal();
                    short burnTime = originalBFurnace.getBurnTime();

                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    newBlock.setType(Material.BLAST_FURNACE);

                    BlockState blockState2 = newBlock.getState();
                    if (blockState2 instanceof BlastFurnace) {
                        BlastFurnace newBFurnace = (BlastFurnace) blockState2;

                        Directional directional2 = (Directional) newBlock.getBlockData();
                        directional2.setFacing(facing);
                        newBlock.setBlockData(directional2);

                        newBFurnace.setCookTime(cookTime);
                        newBFurnace.setCookTimeTotal(totalCT);
                        newBFurnace.setBurnTime(burnTime);
                        newBFurnace.update();
                        newBFurnace.getInventory().setContents(furnaceInventory);
                    }
                } else if (blockState instanceof Chest && oldBlock.getType() == Material.TRAPPED_CHEST && oldBlock.getBlockPower() == 0 && !(((Chest) blockState).getInventory() instanceof DoubleChestInventory)) {
                    Inventory chestInventory = ((Chest) blockState).getInventory();

                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    piston.getState().update();
                    newBlock.setType(Material.TRAPPED_CHEST);
                    newBlock.getState().update();

                    Directional directional2 = (Directional) newBlock.getBlockData();
                    directional2.setFacing(facing);
                    newBlock.setBlockData(directional2);

                    BlockState blockState2 = newBlock.getState();
                    Inventory chest2Inventory = ((Chest) blockState2).getInventory();
                    chest2Inventory.setContents(chestInventory.getContents());
                    newBlock.getState().update();
                } else if (blockState instanceof BrewingStand && oldBlock.getType() == Material.BREWING_STAND) {
                    Inventory brewInventory = ((BrewingStand) blockState).getInventory();

                    oldBlock.setType(Material.AIR);
                    piston.getState().update();
                    newBlock.setType(Material.BREWING_STAND);
                    newBlock.getState().update();

                    BlockState blockState2 = newBlock.getState();
                    Inventory brew2Inventory = ((BrewingStand) blockState2).getInventory();
                    brew2Inventory.setContents(brewInventory.getContents());
                    newBlock.getState().update();
                } else if (blockState instanceof Campfire && oldBlock.getType() == Material.CAMPFIRE) {
                    Campfire originalCampfire = (Campfire) blockState;
                    ItemStack item1 = originalCampfire.getItem(0);
                    ItemStack item2 = originalCampfire.getItem(1);
                    ItemStack item3 = originalCampfire.getItem(2);
                    ItemStack item4 = originalCampfire.getItem(3);

                    int cookTime1 = originalCampfire.getCookTime(0);
                    int cookTime2 = originalCampfire.getCookTime(1);
                    int cookTime3 = originalCampfire.getCookTime(2);
                    int cookTime4 = originalCampfire.getCookTime(3);

                    int totalCT1 = originalCampfire.getCookTimeTotal(0);
                    int totalCT2 = originalCampfire.getCookTimeTotal(1);
                    int totalCT3 = originalCampfire.getCookTimeTotal(2);
                    int totalCT4 = originalCampfire.getCookTimeTotal(3);

                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    piston.getState().update();
                    newBlock.setType(Material.CAMPFIRE);
                    newBlock.getState().update();

                    BlockState blockState2 = newBlock.getState();
                    if (blockState2 instanceof Campfire) {
                        Campfire newCampfire = (Campfire) blockState2;

                        Directional directional2 = (Directional) newBlock.getBlockData();
                        directional2.setFacing(facing);
                        newBlock.setBlockData(directional2);

                        newCampfire.setItem(0, item1);
                        newCampfire.setCookTime(0, cookTime1);
                        newCampfire.setCookTimeTotal(0, totalCT1);

                        newCampfire.setItem(1, item2);
                        newCampfire.setCookTime(1, cookTime2);
                        newCampfire.setCookTimeTotal(1, totalCT2);

                        newCampfire.setItem(2, item3);
                        newCampfire.setCookTime(2, cookTime3);
                        newCampfire.setCookTimeTotal(2, totalCT3);

                        newCampfire.setItem(3, item4);
                        newCampfire.setCookTime(3, cookTime4);
                        newCampfire.setCookTimeTotal(3, totalCT4);

                        newCampfire.update();
                    }
                } else if (blockState instanceof Campfire && oldBlock.getType() == Material.SOUL_CAMPFIRE) {
                    Campfire originalCampfire = (Campfire) blockState;
                    ItemStack item1 = originalCampfire.getItem(0);
                    ItemStack item2 = originalCampfire.getItem(1);
                    ItemStack item3 = originalCampfire.getItem(2);
                    ItemStack item4 = originalCampfire.getItem(3);

                    int cookTime1 = originalCampfire.getCookTime(0);
                    int cookTime2 = originalCampfire.getCookTime(1);
                    int cookTime3 = originalCampfire.getCookTime(2);
                    int cookTime4 = originalCampfire.getCookTime(3);

                    int totalCT1 = originalCampfire.getCookTimeTotal(0);
                    int totalCT2 = originalCampfire.getCookTimeTotal(1);
                    int totalCT3 = originalCampfire.getCookTimeTotal(2);
                    int totalCT4 = originalCampfire.getCookTimeTotal(3);

                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    piston.getState().update();
                    newBlock.setType(Material.SOUL_CAMPFIRE);
                    newBlock.getState().update();

                    BlockState blockState2 = newBlock.getState();
                    if (blockState2 instanceof Campfire) {
                        Campfire newCampfire = (Campfire) blockState2;

                        Directional directional2 = (Directional) newBlock.getBlockData();
                        directional2.setFacing(facing);
                        newBlock.setBlockData(directional2);

                        newCampfire.setItem(0, item1);
                        newCampfire.setCookTime(0, cookTime1);
                        newCampfire.setCookTimeTotal(0, totalCT1);

                        newCampfire.setItem(1, item2);
                        newCampfire.setCookTime(1, cookTime2);
                        newCampfire.setCookTimeTotal(1, totalCT2);

                        newCampfire.setItem(2, item3);
                        newCampfire.setCookTime(2, cookTime3);
                        newCampfire.setCookTimeTotal(2, totalCT3);

                        newCampfire.setItem(3, item4);
                        newCampfire.setCookTime(3, cookTime4);
                        newCampfire.setCookTimeTotal(3, totalCT4);

                        newCampfire.update();
                    }
                } else if (blockState instanceof Lectern && oldBlock.getType() == Material.LECTERN && oldBlock.getBlockPower() == 0) {
                    Inventory lecternInventory = ((Lectern) blockState).getInventory();
                    int page = ((Lectern) blockState).getPage();


                    BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                    oldBlock.setType(Material.AIR);
                    piston.getState().update();
                    newBlock.setType(Material.LECTERN);
                    newBlock.getState().update();

                    Directional directional2 = (Directional) newBlock.getBlockData();
                    directional2.setFacing(facing);
                    newBlock.setBlockData(directional2);

                    BlockState blockState2 = newBlock.getState();
                    Inventory lectern2Inventory = ((Lectern) blockState2).getInventory();
                    lectern2Inventory.setContents(lecternInventory.getContents());
                    ((Lectern) blockState2).setPage(page);
                    newBlock.getState().update();
                } else if (is20OrHigher()) {
                    if (blockState instanceof ChiseledBookshelf && oldBlock.getType() == Material.CHISELED_BOOKSHELF && oldBlock.getBlockPower() == 0) {
                        Inventory CBInventory = ((ChiseledBookshelf) blockState).getInventory();


                        BlockFace facing = ((Directional) oldBlock.getBlockData()).getFacing();
                        oldBlock.setType(Material.AIR);
                        piston.getState().update();
                        newBlock.setType(Material.CHISELED_BOOKSHELF);
                        newBlock.getState().update();

                        Directional directional2 = (Directional) newBlock.getBlockData();
                        directional2.setFacing(facing);
                        newBlock.setBlockData(directional2);

                        BlockState blockState2 = newBlock.getState();
                        Inventory CB2Inventory = ((ChiseledBookshelf) blockState2).getInventory();
                        CB2Inventory.setContents(CBInventory.getContents());
                        newBlock.getState().update();
                    }
                } else if (blockState instanceof Jukebox && oldBlock.getType() == Material.JUKEBOX && oldBlock.getBlockPower() == 0) {
                    Jukebox jukebox = (Jukebox) blockState;
                    if (!jukebox.isPlaying()) {
                        Inventory JukeInventory = ((Jukebox) blockState).getInventory();

                        oldBlock.setType(Material.AIR);
                        newBlock.setType(Material.JUKEBOX);

                        BlockState blockState2 = newBlock.getState();
                        Inventory Juke2Inventory = ((Jukebox) blockState2).getInventory();
                        Juke2Inventory.setContents(JukeInventory.getContents());
                        newBlock.getState().update();
                    }
                }
            }
        }, 2);

    }
    public void checkBlock(Block block){
        if (block.getType().toString().contains("BANNER")
                || block.getType().toString().contains("CARPET")
                || block.getType().toString().contains("TORCH")
                || block.getType().toString().contains("SAPLING")
                || block.getType().toString().contains("POT")
                || block.getType().toString().contains("WHEAT")
                || block.getType().toString().contains("GLOW_LICHEN")
                || block.getType().toString().contains("VINES")
                || block.getType().toString().contains("AMETHYST_CLUSTER")
                || block.getType().toString().contains("BUTTON")
                || block.getType().toString().contains("COMPARATOR")
                || block.getType().toString().contains("REPEATER")
                || block.getType().toString().contains("TRIPWIRE")
                || block.getType().toString().contains("PRESSURE")
                || block.getType().toString().contains("POTATOES")
                || block.getType().toString().contains("CARROTS")
                || block.getType().toString().contains("BEETROOT")
                || block.getType().toString().contains("LANTERN")
                || block.getType().toString().contains("REDSTONE_WIRE")
                || block.getType().toString().contains("LEVER")
                || block.getType().toString().contains("DRIPSTONE")
                || block.getType().toString().contains("COCOA")) {
            ItemStack itemStack = new ItemStack(block.getType());
            block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
            block.setType(Material.AIR);
        }
    }
}
//Made by WizarTheGreat