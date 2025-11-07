package com.renyigesai.bakeries.api;

import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PathPackResources;
import net.neoforged.neoforgespi.locating.IModFile;

public class BakeriesFilePackResource extends PathPackResources{
	protected final IModFile modFile;
	protected final String sourcePath;
	public BakeriesFilePackResource(PackLocationInfo location, IModFile modFile, String sourcePath) {
		super(location, modFile.findResource(sourcePath));
        this.modFile = modFile;
        this.sourcePath = sourcePath;
    }
}
