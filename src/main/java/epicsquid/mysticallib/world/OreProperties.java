package epicsquid.mysticallib.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;

import java.util.function.Predicate;

public class OreProperties {

	private OreFeatureConfig oreFeature;
	private CountRangeConfig countRange;
	private Predicate<Biome> match;

	public OreProperties(OreFeatureConfig oreFeature, CountRangeConfig countRange, Predicate<Biome> match) {
		this.oreFeature = oreFeature;
		this.countRange = countRange;
		this.match = match;
	}

	public OreProperties(OreFeatureConfig oreFeature, CountRangeConfig countRange) {
		this(oreFeature, countRange, x -> true);
	}

	public OreFeatureConfig getOreFeature() {
		return oreFeature;
	}

	public CountRangeConfig getCountRange() {
		return countRange;
	}

	public Predicate<Biome> getMatch() {
		return match;
	}
}