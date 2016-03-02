package com.nubebuster.hg.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

import com.nubebuster.hg.HG;
import com.nubebuster.hg.utils.DataPair;

import net.minecraft.server.v1_8_R3.ChunkProviderServer;
import net.minecraft.server.v1_8_R3.WorldServer;

public class GenerationHandler {

	public static void deleteWorld() {
		File worldFolder = new File("world");
		if (!worldFolder.exists())
			worldFolder.mkdirs();
		clear(worldFolder);
	}

	private static List<DataPair> toGenerate = new ArrayList<DataPair>();
	private static int task;

	public static void generateChunks() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(HG.HG, new Runnable() {
			public void run() {
				int chunks1 = Bukkit.getViewDistance() + 3;
				int chunks2 = Bukkit.getViewDistance() + 32;

				int chunksDistance = Math.max(chunks1, chunks2);
				Chunk spawn = Bukkit.getWorld(HG.HG.getConfig().getString("world")).getSpawnLocation().getChunk();
				for (int x = -chunksDistance; x <= chunksDistance; x++) {
					for (int z = -chunksDistance; z <= chunksDistance; z++) {
						DataPair pair = new DataPair(spawn.getX() + x, spawn.getZ() + z);
						toGenerate.add(pair);
					}
				}
				final double totalChunks = toGenerate.size();
				task = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.HG, new Runnable() {
					private double chunksGenerated;
					private long lastLogged;

					public void run() {
						World world = Bukkit.getWorld("world");
						if (this.lastLogged + 5000L < System.currentTimeMillis()) {
							System.out.print(
									"Generating: " + (int) Math.floor(this.chunksGenerated / totalChunks * 100D) + "%");
							this.lastLogged = System.currentTimeMillis();
						}
						long startedGeneration = System.currentTimeMillis();
						Iterator<DataPair> cordsItel = toGenerate.iterator();
						WorldServer nmsWorld;
						ChunkProviderServer nmsChunkProviderServer;
						int cx;
						while ((cordsItel.hasNext()) && (startedGeneration + 50L > System.currentTimeMillis())) {
							DataPair pair = (DataPair) cordsItel.next();

							if (!world.isChunkLoaded(pair.getX(), pair.getZ())) {
								world.loadChunk(pair.getX(), pair.getZ());
								nmsWorld = ((CraftWorld) world).getHandle();
								nmsChunkProviderServer = nmsWorld.chunkProviderServer;
								for (cx = pair.getX() - 1; cx <= pair.getX() + 1; cx++)
									for (int cz = pair.getZ() - 1; cz <= pair.getZ() + 1; cz++)
										nmsChunkProviderServer.getChunkAt(cx, cz);
								nmsChunkProviderServer.getChunkAt(nmsChunkProviderServer, pair.getX(), pair.getZ());

								world.unloadChunk(pair.getX(), pair.getZ());
							}
							cordsItel.remove();
							this.chunksGenerated += 1D;
						}
						if (!cordsItel.hasNext()) {
							System.out.println("World is done generating");
							try {
								Bukkit.getPluginManager()
										.disablePlugin(Bukkit.getPluginManager().getPlugin("TerrainControl"));
							} catch (Exception e) {
							}
							for (Player op : Bukkit.getOnlinePlayers())
								if (op.isOp())
									op.sendMessage(ChatColor.RED + "[NubesHG] The chunks are done generating.");
							Bukkit.getScheduler().cancelTask(task);
						}
					}
				}, 1L, 1L);
			}
		}, 0L);
	}

	private static void clear(File file) {
		if (!file.exists())
			return;
		if (file.isFile()) {
			file.delete();
		} else {
			for (File f : file.listFiles())
				clear(f);
			file.delete();
		}
	}
}
