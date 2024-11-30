package plugin.movabletileentities;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;

public final class MovableTileEntities extends JavaPlugin implements Listener {

    private static final List<Pair<Class<?>>> funcList;

    private static final String PISTON_TAG = "poweredPiston";

    @Override
    public void onEnable() {
        getLogger().info("ACTIVE");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
        if (e.isCancelled() || e.getSourceBlock() instanceof Piston || e.getSourceBlock() instanceof TechnicalPiston) {
            return;
        }
        Block block = e.getBlock();
        if (!(e.getBlock().getBlockData() instanceof Piston piston)) {
            return;
        }
        if (!piston.isExtended() && block.getBlockPower() > 0) {
            e.setCancelled(move(block, true));
        } else if (piston.isExtended() && block.getBlockPower() == 0 && block.hasMetadata(PISTON_TAG)) {
            move(block, false);
        }
    }

    public boolean move(Block pistonBlock, boolean isPush) {
        Piston piston = (Piston) pistonBlock.getBlockData();
        Vector direction = piston.getFacing().getDirection();
        Block oldBlock = pistonBlock.getRelative(
        direction.getBlockX(),
        direction.getBlockY(),
        direction.getBlockZ());
        Block newBlock = pistonBlock.getRelative(
        direction.getBlockX() * 2,
        direction.getBlockY() * 2,
        direction.getBlockZ() * 2);
        if (isPush) {
            return move0(pistonBlock, oldBlock, newBlock, () -> pistonBlock.setMetadata(PISTON_TAG, new FixedMetadataValue(this, true)));
        } else {
            return move0(pistonBlock, newBlock, oldBlock, () -> pistonBlock.removeMetadata(PISTON_TAG, this));
        }
    }

    private boolean move0(Block piston, Block from, Block to, Runnable onSuccess) {
        BlockState blockState = from.getState();
        if (from.getBlockPower() != 0 ||
        !(from.getState() instanceof TileState) ||
        from.getBlockData() instanceof TechnicalPiston ||
        (blockState instanceof Chest && (((Chest) blockState).getInventory() instanceof DoubleChestInventory))) {
            return false;
        }
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (to.getType() != Material.AIR) {
                return;
            }
            piston.getState().update();
            to.setType(from.getType());
            to.getState().update();
            to.setBlockData(from.getBlockData());
            for (Pair<Class<?>> pair : funcList) {
                if (pair.first().isInstance(blockState)) {
                    pair.second().apply(from.getState(), to.getState());
                }
            }
            from.setType(Material.AIR);
            from.getState().update();
            to.getState().update();
            onSuccess.run();
        }, 2);
        return true;
    }

    public static boolean is20OrHigher() {
        String version = Bukkit.getBukkitVersion();
        String[] parts = version.split("[^0-9]+");
        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        return major > 1 || (major == 1 && minor >= 20);
    }

    static {
        ImmutableList.Builder<Pair<Class<?>>> builder = ImmutableList.builder();

        MoveFunction<TileState, TileState> generic = (from, to) -> {
            to.setType(from.getType());
            to.setData(from.getData());
            to.setBlockData(from.getBlockData());
            to.getPersistentDataContainer().copyTo(from.getPersistentDataContainer(), true);
        };

        MoveFunction<Structure, Structure> structure = (from, to) -> {
            to.setAuthor(from.getAuthor());
            to.setMetadata(from.getMetadata());
            to.setBoundingBoxVisible(from.isBoundingBoxVisible());
            to.setIntegrity(from.getIntegrity());
            to.setIgnoreEntities(from.isIgnoreEntities());
            to.setMirror(from.getMirror());
            to.setRelativePosition(from.getRelativePosition());
            to.setRotation(from.getRotation());
            to.setSeed(from.getSeed());
            to.setShowAir(from.isShowAir());
            to.setStructureName(from.getStructureName());
            to.setStructureSize(from.getStructureSize());
            to.setUsageMode(from.getUsageMode());
        };

        MoveFunction<Jukebox, Jukebox> jukebox = (from, to) -> {
            to.setPlaying(from.getPlaying());
            to.setRecord(from.getRecord());
            to.getInventory().setContents(from.getInventory().getContents());
            if (from.isPlaying()) to.startPlaying();
            else to.stopPlaying();
        };

        MoveFunction<BrushableBlock, BrushableBlock> brushableBlock = (from, to) -> {
            to.setItem(from.getItem());
        };

        MoveFunction<Skull, Skull> skull = (from, to) -> {
            to.setRotation(from.getRotation());
            from.setNoteBlockSound(from.getNoteBlockSound());
            if (from.hasOwner()) {
                to.setOwnerProfile(from.getOwnerProfile());
            }
        };

        MoveFunction<Beacon, Beacon> beacon = (from, to) -> {
            to.setPrimaryEffect(from.getPrimaryEffect().getType());
            to.setSecondaryEffect(from.getSecondaryEffect().getType());
        };

        MoveFunction<Banner, Banner> banner = (from, to) -> {
            to.setPatterns(from.getPatterns());
        };

        MoveFunction<EndGateway, EndGateway> endGateway = (from, to) -> {
            to.setAge(from.getAge());
            to.setExactTeleport(from.isExactTeleport());
            to.setExitLocation(from.getExitLocation());
        };

        MoveFunction<Sign, Sign> sign = (from, to) -> {
            to.setWaxed(from.isWaxed());
            to.setColor(from.getColor());
            to.setGlowingText(from.isGlowingText());
            for (int i=0; i<from.getLines().length; i++) {
                to.setLine(i, from.getLine(i));
            }
        };

        MoveFunction<SculkSensor, SculkSensor> sculkSensor = (from, to) -> {
            to.setLastVibrationFrequency(from.getLastVibrationFrequency());
        };

        MoveFunction<Conduit, Conduit> conduit = (from, to) -> {
            to.setTarget(from.getTarget());
        };

        MoveFunction<CreatureSpawner, CreatureSpawner> creatureSpawner = (from, to) -> {
            to.setDelay(from.getDelay());
            to.setMaxNearbyEntities(from.getMaxNearbyEntities());
            to.setMaxSpawnDelay(from.getMaxSpawnDelay());
            to.setMinSpawnDelay(from.getMinSpawnDelay());
            to.setPotentialSpawns(from.getPotentialSpawns());
            to.setRequiredPlayerRange(from.getRequiredPlayerRange());
            to.setSpawnCount(from.getSpawnCount());
            to.setSpawnedType(from.getSpawnedType());
            to.setSpawnRange(from.getSpawnRange());
        };

        MoveFunction<CommandBlock, CommandBlock> commandBlock = (from, to) -> {
            to.setName(from.getName());
            to.setCommand(from.getCommand());
        };

        MoveFunction<SculkShrieker, SculkShrieker> sculkShrieker = (from, to) -> {
            to.setWarningLevel(from.getWarningLevel());
        };

        MoveFunction<Campfire, Campfire> campfire = (from, to) -> {
            for (int i=0; i<4; i++) {
                to.setCookTime(i, from.getCookTime(i));
                to.setCookTimeTotal(i, from.getCookTimeTotal(i));
                to.setItem(i, from.getItem(i));
            }
        };

        MoveFunction<DecoratedPot, DecoratedPot> decoratedPot = (from, to) -> {
            for (Map.Entry<DecoratedPot.Side, Material> entry : from.getSherds().entrySet()) {
                to.setSherd(entry.getKey(), entry.getValue());
            }
        };

        MoveFunction<InventoryHolder, InventoryHolder> inventoryHolder = (from, to) -> {
            to.getInventory().setContents(from.getInventory().getContents());
        };

        MoveFunction<Container, Container> container = (from, to) -> {
            to.getInventory().setContents(from.getInventory().getContents());
        };

        MoveFunction<Furnace, Furnace> furnace = (from, to) -> {
            to.setBurnTime(from.getBurnTime());
            to.setCookTime(from.getCookTime());
            to.setCookTimeTotal(from.getCookTimeTotal());
        };

        MoveFunction<BrewingStand, BrewingStand> brewingStand = (from, to) -> {
            to.setBrewingTime(from.getBrewingTime());
            to.setFuelLevel(from.getFuelLevel());
        };

        MoveFunction<Lectern, Lectern> lectern = (from, to) -> {
            to.setPage(from.getPage());
        };

        MoveFunction<EntityBlockStorage, EntityBlockStorage> entityBlockStorage = (from, to) -> {
            to.setMaxEntities(from.getMaxEntities());
            for (Object e : from.releaseEntities()) {
                to.addEntity((Entity) e);
            }
        };

        MoveFunction<Beehive, Beehive> beehive = (from, to) -> {
            to.setFlower(from.getFlower());
        };

        builder.add(new Pair<>(TileState.class, generic));
        builder.add(new Pair<>(Structure.class, structure));
        builder.add(new Pair<>(Jukebox.class, jukebox));
        builder.add(new Pair<>(BrushableBlock.class, brushableBlock));
        builder.add(new Pair<>(Skull.class, skull));
        builder.add(new Pair<>(Beacon.class, beacon));
        builder.add(new Pair<>(Banner.class, banner));
        builder.add(new Pair<>(EndGateway.class, endGateway));
        builder.add(new Pair<>(Sign.class, sign));
        builder.add(new Pair<>(SculkSensor.class, sculkSensor));
        builder.add(new Pair<>(Conduit.class, conduit));
        builder.add(new Pair<>(CreatureSpawner.class, creatureSpawner));
        builder.add(new Pair<>(CommandBlock.class, commandBlock));
        builder.add(new Pair<>(SculkShrieker.class, sculkShrieker));
        builder.add(new Pair<>(Campfire.class, campfire));
        builder.add(new Pair<>(DecoratedPot.class, decoratedPot));
        builder.add(new Pair<>(InventoryHolder.class, inventoryHolder));
        builder.add(new Pair<>(Container.class, container));
        builder.add(new Pair<>(Furnace.class, furnace));
        builder.add(new Pair<>(BrewingStand.class, brewingStand));
        builder.add(new Pair<>(Lectern.class, lectern));
        builder.add(new Pair<>(EntityBlockStorage.class, entityBlockStorage));
        builder.add(new Pair<>(Beehive.class, beehive));

        if (is20OrHigher()) {
            MoveFunction<ChiseledBookshelf, ChiseledBookshelf> chiseledBookshelf = (from, to) -> {
                to.setLastInteractedSlot(from.getLastInteractedSlot());
            };

            MoveFunction<Crafter, Crafter> crafter = (from, to) -> {
                to.setCraftingTicks(from.getCraftingTicks());
                to.setTriggered(from.isTriggered());
                for (int i = 0; i < 9; i++) {
                    to.setSlotDisabled(i, from.isSlotDisabled(i));
                }
            };

            builder.add(new Pair<>(Crafter.class, crafter));
            builder.add(new Pair<>(ChiseledBookshelf.class, chiseledBookshelf));
        }

        funcList = builder.build();
    }
}
//Made by WizarTheGreat