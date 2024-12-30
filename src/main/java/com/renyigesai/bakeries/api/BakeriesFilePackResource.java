package com.renyigesai.bakeries.api;

import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;

import java.nio.file.Path;

public class BakeriesFilePackResource extends PathPackResources {
	protected final IModFile modFile;
	protected final String sourcePath;

	public BakeriesFilePackResource(String name, IModFile modFile, String sourcePath) {
		super(name, true, modFile.findResource(sourcePath));
		this.modFile = modFile;
		this.sourcePath = sourcePath;
	}
}
