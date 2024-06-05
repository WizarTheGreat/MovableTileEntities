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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class MovableTileEntities extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getLogger().info("ACTIVE");
        getServer().getPluginManager().registerEvents(this, this);
    }
    @Override
    public void onDisable() {
    }
    @EventHandler
    public void RedstoneUpdate(BlockRedstoneEvent e) {
        checkAdjacentBlocksForPistons(e.getBlock());
    }
    @EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
        Block block = e.getBlock();
        int power = block.getBlockPower();
        if (block.getType() == Material.PISTON || block.getType() == Material.STICKY_PISTON) {
            if (power > 0) {
                BlockData blockData = block.getBlockData();
                if (blockData instanceof Directional) {
                    Directional directional = (Directional) blockData;
                    Vector direction = directional.getFacing().getDirection();
                    Block blockInFront = block.getRelative(direction.getBlockX(), direction.getBlockY(), direction.getBlockZ());
                    Block blockTwoBlocksAway = block.getRelative(
                            direction.getBlockX() * 2,
                            direction.getBlockY() * 2,
                            direction.getBlockZ() * 2);

                    BlockState blockState = blockInFront.getState();
                    if (blockTwoBlocksAway.getType() == Material.AIR) {
                        if (blockState instanceof Chest && blockInFront.getType() == Material.CHEST && !(((Chest) blockState).getInventory() instanceof DoubleChestInventory)) {
                            Inventory chestInventory = ((Chest) blockState).getInventory();

                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.CHEST);
                            blockTwoBlocksAway.getState().update();

                            Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                            directional2.setFacing(facing);
                            blockTwoBlocksAway.setBlockData(directional2);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            Inventory chest2Inventory = ((Chest) blockState2).getInventory();
                            chest2Inventory.setContents(chestInventory.getContents());
                            blockTwoBlocksAway.getState().update();
                        } else if (blockState instanceof Dispenser && blockInFront.getType() == Material.DISPENSER && blockInFront.getBlockPower() == 0) {
                            Inventory dispenserInventory = ((Dispenser) blockState).getInventory();

                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.DISPENSER);
                            blockTwoBlocksAway.getState().update();

                            Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                            directional2.setFacing(facing);
                            blockTwoBlocksAway.setBlockData(directional2);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            Inventory dispenser2Inventory = ((Dispenser) blockState2).getInventory();
                            dispenser2Inventory.setContents(dispenserInventory.getContents());
                            blockTwoBlocksAway.getState().update();
                        } else if (blockState instanceof Dropper && blockInFront.getType() == Material.DROPPER && blockInFront.getBlockPower() == 0) {
                            Inventory dropperInventory = ((Dropper) blockState).getInventory();

                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.DROPPER);
                            blockTwoBlocksAway.getState().update();

                            Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                            directional2.setFacing(facing);
                            blockTwoBlocksAway.setBlockData(directional2);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            Inventory dropper2Inventory = ((Dropper) blockState2).getInventory();
                            dropper2Inventory.setContents(dropperInventory.getContents());
                            blockTwoBlocksAway.getState().update();
                        } else if (blockState instanceof Barrel && blockInFront.getType() == Material.BARREL && blockInFront.getBlockPower() == 0) {
                            Inventory barrelInventory = ((Barrel) blockState).getInventory();

                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.BARREL);
                            blockTwoBlocksAway.getState().update();

                            Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                            directional2.setFacing(facing);
                            blockTwoBlocksAway.setBlockData(directional2);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            Inventory barrel2Inventory = ((Barrel) blockState2).getInventory();
                            barrel2Inventory.setContents(barrelInventory.getContents());
                            blockTwoBlocksAway.getState().update();
                        } else if (blockState instanceof Hopper && blockInFront.getType() == Material.HOPPER && blockInFront.getBlockPower() == 0) {
                            Inventory hopperInventory = ((Hopper) blockState).getInventory();

                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.HOPPER);
                            blockTwoBlocksAway.getState().update();

                            Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                            directional2.setFacing(facing);
                            blockTwoBlocksAway.setBlockData(directional2);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            Inventory hopper2Inventory = ((Hopper) blockState2).getInventory();
                            hopper2Inventory.setContents(hopperInventory.getContents());
                            blockTwoBlocksAway.getState().update();
                        } else if (blockState instanceof Furnace && blockInFront.getType() == Material.FURNACE) {
                            Furnace originalFurnace = (Furnace) blockState;
                            ItemStack[] furnaceInventory = originalFurnace.getInventory().getContents();

                            short cookTime = originalFurnace.getCookTime();
                            int totalCT = originalFurnace.getCookTimeTotal();
                            short burnTime = originalFurnace.getBurnTime();


                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            blockTwoBlocksAway.setType(Material.FURNACE);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            if (blockState2 instanceof Furnace) {
                                Furnace newFurnace = (Furnace) blockState2;

                                Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                                directional2.setFacing(facing);
                                blockTwoBlocksAway.setBlockData(directional2);

                                newFurnace.setCookTime(cookTime);
                                newFurnace.setCookTimeTotal(totalCT);
                                newFurnace.setBurnTime(burnTime);
                                newFurnace.getInventory().setContents(furnaceInventory);
                            }
                            if (blockState2 instanceof Furnace) {
                                ((Furnace) blockTwoBlocksAway.getState()).getInventory().setContents(furnaceInventory);
                                blockTwoBlocksAway.getState().update();
                            }
                        } else if (blockState instanceof Smoker && blockInFront.getType() == Material.SMOKER) {
                            Smoker originalSmoker = (Smoker) blockState;
                            ItemStack[] smokerInventory = originalSmoker.getInventory().getContents();

                            short cookTime = originalSmoker.getCookTime();
                            int totalCT = originalSmoker.getCookTimeTotal();
                            short burnTime = originalSmoker.getBurnTime();

                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            blockTwoBlocksAway.setType(Material.SMOKER);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            if (blockState2 instanceof Smoker) {
                                Smoker newSmoker = (Smoker) blockState2;

                                Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                                directional2.setFacing(facing);
                                blockTwoBlocksAway.setBlockData(directional2);

                                newSmoker.setCookTime(cookTime);
                                newSmoker.setCookTimeTotal(totalCT);
                                newSmoker.setBurnTime(burnTime);
                                newSmoker.getInventory().setContents(smokerInventory);
                            }
                            if (blockState2 instanceof Smoker) {
                                ((Smoker) blockTwoBlocksAway.getState()).getInventory().setContents(smokerInventory);
                                blockTwoBlocksAway.getState().update();
                            }
                        } else if (blockState instanceof BlastFurnace && blockInFront.getType() == Material.BLAST_FURNACE) {
                            BlastFurnace originalBFurnace = (BlastFurnace) blockState;
                            ItemStack[] furnaceInventory = originalBFurnace.getInventory().getContents();

                            short cookTime = originalBFurnace.getCookTime();
                            int totalCT = originalBFurnace.getCookTimeTotal();
                            short burnTime = originalBFurnace.getBurnTime();

                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            blockTwoBlocksAway.setType(Material.BLAST_FURNACE);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            if (blockState2 instanceof BlastFurnace) {
                                BlastFurnace newBFurnace = (BlastFurnace) blockState2;

                                Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                                directional2.setFacing(facing);
                                blockTwoBlocksAway.setBlockData(directional2);

                                newBFurnace.setCookTime(cookTime);
                                newBFurnace.setCookTimeTotal(totalCT);
                                newBFurnace.setBurnTime(burnTime);
                                newBFurnace.getInventory().setContents(furnaceInventory);
                            }
                            if (blockState2 instanceof BlastFurnace) {
                                ((BlastFurnace) blockTwoBlocksAway.getState()).getInventory().setContents(furnaceInventory);
                                blockTwoBlocksAway.getState().update();
                            }
                        } else if (blockState instanceof Chest && blockInFront.getType() == Material.TRAPPED_CHEST && blockInFront.getBlockPower() == 0) {
                            Inventory chestInventory = ((Chest) blockState).getInventory();

                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.TRAPPED_CHEST);
                            blockTwoBlocksAway.getState().update();

                            Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                            directional2.setFacing(facing);
                            blockTwoBlocksAway.setBlockData(directional2);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            Inventory chest2Inventory = ((Chest) blockState2).getInventory();
                            chest2Inventory.setContents(chestInventory.getContents());
                            blockTwoBlocksAway.getState().update();
                        } else if (blockState instanceof BrewingStand && blockInFront.getType() == Material.BREWING_STAND) {
                            Inventory brewInventory = ((BrewingStand) blockState).getInventory();

                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.BREWING_STAND);
                            blockTwoBlocksAway.getState().update();

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            Inventory brew2Inventory = ((BrewingStand) blockState2).getInventory();
                            brew2Inventory.setContents(brewInventory.getContents());
                            blockTwoBlocksAway.getState().update();
                        } else if (blockState instanceof Campfire && blockInFront.getType() == Material.CAMPFIRE) {
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

                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.CAMPFIRE);
                            blockTwoBlocksAway.getState().update();

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            if (blockState2 instanceof Campfire) {
                                Campfire newCampfire = (Campfire) blockState2;

                                Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                                directional2.setFacing(facing);
                                blockTwoBlocksAway.setBlockData(directional2);

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
                        } else if (blockState instanceof Campfire && blockInFront.getType() == Material.SOUL_CAMPFIRE) {
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

                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.SOUL_CAMPFIRE);
                            blockTwoBlocksAway.getState().update();

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            if (blockState2 instanceof Campfire) {
                                Campfire newCampfire = (Campfire) blockState2;

                                Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                                directional2.setFacing(facing);
                                blockTwoBlocksAway.setBlockData(directional2);

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
                        } else if (blockState instanceof Lectern && blockInFront.getType() == Material.LECTERN && blockInFront.getBlockPower() == 0) {
                            Inventory lecternInventory = ((Lectern) blockState).getInventory();
                            int page = ((Lectern) blockState).getPage();


                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.LECTERN);
                            blockTwoBlocksAway.getState().update();

                            Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                            directional2.setFacing(facing);
                            blockTwoBlocksAway.setBlockData(directional2);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            Inventory lectern2Inventory = ((Lectern) blockState2).getInventory();
                            lectern2Inventory.setContents(lecternInventory.getContents());
                            ((Lectern) blockState2).setPage(page);
                            blockTwoBlocksAway.getState().update();
                        } else if (blockState instanceof ChiseledBookshelf && blockInFront.getType() == Material.CHISELED_BOOKSHELF && blockInFront.getBlockPower() == 0) {
                            Inventory CBInventory = ((ChiseledBookshelf) blockState).getInventory();


                            BlockFace facing = ((Directional) blockInFront.getBlockData()).getFacing();
                            blockInFront.setType(Material.AIR);
                            block.getState().update();
                            blockTwoBlocksAway.setType(Material.CHISELED_BOOKSHELF);
                            blockTwoBlocksAway.getState().update();

                            Directional directional2 = (Directional) blockTwoBlocksAway.getBlockData();
                            directional2.setFacing(facing);
                            blockTwoBlocksAway.setBlockData(directional2);

                            BlockState blockState2 = blockTwoBlocksAway.getState();
                            Inventory CB2Inventory = ((ChiseledBookshelf) blockState2).getInventory();
                            CB2Inventory.setContents(CBInventory.getContents());
                            blockTwoBlocksAway.getState().update();
                        }
                    }

                }

            }
        }
    }
    private void checkAdjacentBlocksForPistons (Block block){
        Block[] adjacentBlocks = {
                block.getRelative(1, 0, 0),
                block.getRelative(-1, 0, 0),
                block.getRelative(0, 1, 0),
                block.getRelative(0, -1, 0),
                block.getRelative(0, 0, 1),
                block.getRelative(0, 0, -1)
        };

        for (Block adjacentBlock : adjacentBlocks) {
            if (adjacentBlock.getType() == Material.STICKY_PISTON) {
                Bukkit.getScheduler().runTaskLater(this, () -> {
                    if (!adjacentBlock.isBlockPowered()) {
                        handlePistonUnpowered(adjacentBlock);
                    }
                }, 3);
            }
        }
    }
    private void handlePistonUnpowered (Block block){
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional) {
            Directional directional = (Directional) blockData;
            Vector direction = directional.getFacing().getDirection();
            Block blockInFront = block.getRelative(direction.getBlockX(), direction.getBlockY(), direction.getBlockZ());
            Block blockTwoBlocksAway = block.getRelative(
                    direction.getBlockX() * 2,
                    direction.getBlockY() * 2,
                    direction.getBlockZ() * 2);

            BlockState blockState = blockTwoBlocksAway.getState();
            if (blockInFront.getType() == Material.AIR) {
                if (blockState instanceof Chest && blockTwoBlocksAway.getType() == Material.CHEST && !(((Chest) blockState).getInventory() instanceof DoubleChestInventory)) {
                    Inventory chestInventory = ((Chest) blockState).getInventory();

                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.CHEST);

                    Directional directional2 = (Directional) blockInFront.getBlockData();
                    directional2.setFacing(facing);
                    blockInFront.setBlockData(directional2);

                    BlockState blockState2 = blockInFront.getState();
                    Inventory chest2Inventory = ((Chest) blockState2).getInventory();
                    chest2Inventory.setContents(chestInventory.getContents());
                    blockInFront.getState().update();
                } else if (blockState instanceof Dispenser && blockTwoBlocksAway.getType() == Material.DISPENSER && blockTwoBlocksAway.getBlockPower() == 0) {
                    Inventory dispenserInventory = ((Dispenser) blockState).getInventory();

                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.DISPENSER);

                    Directional directional2 = (Directional) blockInFront.getBlockData();
                    directional2.setFacing(facing);
                    blockInFront.setBlockData(directional2);

                    BlockState blockState2 = blockInFront.getState();
                    Inventory dispenser2Inventory = ((Dispenser) blockState2).getInventory();
                    dispenser2Inventory.setContents(dispenserInventory.getContents());
                    blockInFront.getState().update();
                } else if (blockState instanceof Dropper && blockInFront.getType() == Material.DROPPER && blockTwoBlocksAway.getBlockPower() == 0) {
                    Inventory dropperInventory = ((Dropper) blockState).getInventory();

                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.DROPPER);

                    Directional directional2 = (Directional) blockInFront.getBlockData();
                    directional2.setFacing(facing);
                    blockInFront.setBlockData(directional2);

                    BlockState blockState2 = blockInFront.getState();
                    Inventory dropper2Inventory = ((Dispenser) blockState2).getInventory();
                    dropper2Inventory.setContents(dropperInventory.getContents());
                    blockInFront.getState().update();
                } else if (blockState instanceof Barrel && blockTwoBlocksAway.getType() == Material.BARREL && blockTwoBlocksAway.getBlockPower() == 0) {
                    Inventory barrelInventory = ((Barrel) blockState).getInventory();

                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.BARREL);

                    Directional directional2 = (Directional) blockInFront.getBlockData();
                    directional2.setFacing(facing);
                    blockInFront.setBlockData(directional2);

                    BlockState blockState2 = blockInFront.getState();
                    Inventory barrel2Inventory = ((Barrel) blockState2).getInventory();
                    barrel2Inventory.setContents(barrelInventory.getContents());
                    blockInFront.getState().update();

                } else if (blockState instanceof Hopper && blockTwoBlocksAway.getType() == Material.HOPPER && blockTwoBlocksAway.getBlockPower() == 0) {
                    Inventory hopperInventory = ((Hopper) blockState).getInventory();

                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.HOPPER);

                    Directional directional2 = (Directional) blockInFront.getBlockData();
                    directional2.setFacing(facing);
                    blockInFront.setBlockData(directional2);

                    BlockState blockState2 = blockInFront.getState();
                    Inventory hopper2Inventory = ((Hopper) blockState2).getInventory();
                    hopper2Inventory.setContents(hopperInventory.getContents());
                    blockInFront.getState().update();
                } else if (blockState instanceof Furnace && blockTwoBlocksAway.getType() == Material.FURNACE) {
                    Furnace originalFurnace = (Furnace) blockState;
                    ItemStack[] furnaceInventory = originalFurnace.getInventory().getContents();

                    short cookTime = originalFurnace.getCookTime();
                    int totalCT = originalFurnace.getCookTimeTotal();
                    short burnTime = originalFurnace.getBurnTime();


                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    block.getState().update();
                    blockInFront.setType(Material.FURNACE);
                    blockInFront.getState().update();

                    BlockState blockState2 = blockInFront.getState();
                    if (blockState2 instanceof Furnace) {
                        Furnace newFurnace = (Furnace) blockState2;

                        Directional directional2 = (Directional) blockInFront.getBlockData();
                        directional2.setFacing(facing);
                        blockInFront.setBlockData(directional2);

                        newFurnace.setCookTime(cookTime);
                        newFurnace.setCookTimeTotal(totalCT);
                        newFurnace.setBurnTime(burnTime);
                        newFurnace.getInventory().setContents(furnaceInventory);
                    }
                    if (blockState2 instanceof Furnace) {
                        ((Furnace) blockInFront.getState()).getInventory().setContents(furnaceInventory);
                        blockInFront.getState().update();
                    }
                } else if (blockState instanceof Smoker && blockTwoBlocksAway.getType() == Material.SMOKER) {
                    Smoker originalSmoker = (Smoker) blockState;
                    ItemStack[] smokerInventory = originalSmoker.getInventory().getContents();

                    short cookTime = originalSmoker.getCookTime();
                    int totalCT = originalSmoker.getCookTimeTotal();
                    short burnTime = originalSmoker.getBurnTime();


                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.SMOKER);

                    BlockState blockState2 = blockInFront.getState();
                    if (blockState2 instanceof Smoker) {
                        Smoker newSmoker = (Smoker) blockState2;

                        Directional directional2 = (Directional) blockInFront.getBlockData();
                        directional2.setFacing(facing);
                        blockInFront.setBlockData(directional2);

                        newSmoker.setCookTime(cookTime);
                        newSmoker.setCookTimeTotal(totalCT);
                        newSmoker.setBurnTime(burnTime);
                        newSmoker.getInventory().setContents(smokerInventory);
                    }
                    if (blockState2 instanceof Smoker) {
                        ((Smoker) blockInFront.getState()).getInventory().setContents(smokerInventory);
                        blockInFront.getState().update();
                    }
                } else if (blockState instanceof BlastFurnace && blockTwoBlocksAway.getType() == Material.BLAST_FURNACE) {
                    BlastFurnace originalBFurnace = (BlastFurnace) blockState;
                    ItemStack[] furnaceInventory = originalBFurnace.getInventory().getContents();

                    short cookTime = originalBFurnace.getCookTime();
                    int totalCT = originalBFurnace.getCookTimeTotal();
                    short burnTime = originalBFurnace.getBurnTime();


                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.BLAST_FURNACE);

                    BlockState blockState2 = blockInFront.getState();
                    if (blockState2 instanceof BlastFurnace) {
                        BlastFurnace newBFurnace = (BlastFurnace) blockState2;

                        Directional directional2 = (Directional) blockInFront.getBlockData();
                        directional2.setFacing(facing);
                        blockInFront.setBlockData(directional2);

                        newBFurnace.setCookTime(cookTime);
                        newBFurnace.setCookTimeTotal(totalCT);
                        newBFurnace.setBurnTime(burnTime);
                        newBFurnace.getInventory().setContents(furnaceInventory);
                    }
                    if (blockState2 instanceof BlastFurnace) {
                        ((BlastFurnace) blockInFront.getState()).getInventory().setContents(furnaceInventory);
                        blockInFront.getState().update();
                    }
                } else if (blockState instanceof Chest && blockTwoBlocksAway.getType() == Material.TRAPPED_CHEST) {
                    Inventory chestInventory = ((Chest) blockState).getInventory();

                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.TRAPPED_CHEST);

                    Directional directional2 = (Directional) blockInFront.getBlockData();
                    directional2.setFacing(facing);
                    blockInFront.setBlockData(directional2);

                    BlockState blockState2 = blockInFront.getState();
                    Inventory chest2Inventory = ((Chest) blockState2).getInventory();
                    chest2Inventory.setContents(chestInventory.getContents());
                    blockInFront.getState().update();
                } else if (blockState instanceof BrewingStand && blockTwoBlocksAway.getType() == Material.BREWING_STAND) {
                    Inventory brewInventory = ((BrewingStand) blockState).getInventory();

                    blockTwoBlocksAway.setType(Material.AIR);
                    block.getState().update();
                    blockInFront.setType(Material.BREWING_STAND);
                    blockInFront.getState().update();

                    BlockState blockState2 = blockInFront.getState();
                    Inventory brew2Inventory = ((BrewingStand) blockState2).getInventory();
                    brew2Inventory.setContents(brewInventory.getContents());
                    blockInFront.getState().update();
                } else if (blockState instanceof Campfire && blockTwoBlocksAway.getType() == Material.CAMPFIRE) {
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

                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.CAMPFIRE);

                    BlockState blockState2 = blockInFront.getState();
                    if (blockState2 instanceof Campfire) {
                        Campfire newCampfire = (Campfire) blockState2;

                        Directional directional2 = (Directional) blockInFront.getBlockData();
                        directional2.setFacing(facing);
                        blockInFront.setBlockData(directional2);

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
                } else if (blockState instanceof Campfire && blockTwoBlocksAway.getType() == Material.SOUL_CAMPFIRE) {
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

                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.SOUL_CAMPFIRE);

                    BlockState blockState2 = blockInFront.getState();
                    if (blockState2 instanceof Campfire) {
                        Campfire newCampfire = (Campfire) blockState2;

                        Directional directional2 = (Directional) blockInFront.getBlockData();
                        directional2.setFacing(facing);
                        blockInFront.setBlockData(directional2);

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
                } else if (blockState instanceof Lectern && blockTwoBlocksAway.getType() == Material.LECTERN && blockTwoBlocksAway.getBlockPower() == 0) {
                    Inventory lecternInventory = ((Lectern) blockState).getInventory();
                    int page = ((Lectern) blockState).getPage();


                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.LECTERN);

                    Directional directional2 = (Directional) blockInFront.getBlockData();
                    directional2.setFacing(facing);
                    blockInFront.setBlockData(directional2);

                    BlockState blockState2 = blockInFront.getState();
                    Inventory lectern2Inventory = ((Lectern) blockState2).getInventory();
                    lectern2Inventory.setContents(lecternInventory.getContents());
                    ((Lectern) blockState2).setPage(page);
                    blockInFront.getState().update();
                } else if (blockState instanceof ChiseledBookshelf && blockTwoBlocksAway.getType() == Material.CHISELED_BOOKSHELF && blockTwoBlocksAway.getBlockPower() == 0) {
                    Inventory CBInventory = ((ChiseledBookshelf) blockState).getInventory();


                    BlockFace facing = ((Directional) blockTwoBlocksAway.getBlockData()).getFacing();
                    blockTwoBlocksAway.setType(Material.AIR);
                    blockInFront.setType(Material.CHISELED_BOOKSHELF);

                    Directional directional2 = (Directional) blockInFront.getBlockData();
                    directional2.setFacing(facing);
                    blockInFront.setBlockData(directional2);

                    BlockState blockState2 = blockInFront.getState();
                    Inventory CB2Inventory = ((ChiseledBookshelf) blockState2).getInventory();
                    CB2Inventory.setContents(CBInventory.getContents());
                    blockInFront.getState().update();
                }

            }
        }
    }
}
//Made by WizarTheGreat
