package epicsquid.mysticallib.model.block;

import epicsquid.mysticallib.model.CustomModelBase;
import epicsquid.mysticallib.model.DefaultTransformations;
import epicsquid.mysticallib.model.ModelUtil;
import epicsquid.mysticallib.model.parts.Cube;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class BakedModelBlock implements IBakedModel {

	public Function<ResourceLocation, TextureAtlasSprite> getter;
	public VertexFormat format;
	public TextureAtlasSprite particle, texnorth, texsouth, texup, texdown, texeast, texwest;
	private Cube cube;

	public BakedModelBlock(@Nullable IModelState state, @Nonnull VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter,
												 @Nonnull CustomModelBase model) {
		this(format, bakedTextureGetter, model);
	}

	protected BakedModelBlock(@Nonnull VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter,
														@Nonnull CustomModelBase model) {
		this.getter = bakedTextureGetter;
		this.format = format;
		particle = getter.apply(model.textures.get("particle"));
		texnorth = getter.apply(model.textures.get("north"));
		texsouth = getter.apply(model.textures.get("south"));
		texup = getter.apply(model.textures.get("up"));
		texdown = getter.apply(model.textures.get("down"));
		texeast = getter.apply(model.textures.get("east"));
		texwest = getter.apply(model.textures.get("west"));
		cube = ModelUtil
						.makeCube(format, 0, 0, 0, 1, 1, 1, ModelUtil.FULL_FACES, new TextureAtlasSprite[]{texwest, texeast, texdown, texup, texnorth, texsouth}, 0);
	}

	// TODO Clean this up
	@Override
	@Nonnull
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		List<BakedQuad> finalQuads = new ArrayList<>();
		if (state != null) {
			TextureAtlasSprite[] sprites = new TextureAtlasSprite[]{getParticleTexture()};
			for (int i = 0; i < sprites.length; i++) {
				addGeometry(finalQuads, side, state, new TextureAtlasSprite[]{sprites[i], sprites[i], sprites[i], sprites[i], sprites[i], sprites[i]}, 1);
			}
		} else {
			addItemModel(finalQuads, side);
		}

		return finalQuads;
	}

	public void addGeometry(@Nonnull List<BakedQuad> quads, @Nonnull Direction side, BlockState state, TextureAtlasSprite[] texes, int tintIndex) {
		ModelUtil.makeCube(format, 0, 0, 0, 1, 1, 1, ModelUtil.FULL_FACES, texes, tintIndex).addToList(quads, side);
	}

	public void addItemModel(@Nonnull List<BakedQuad> quads, @Nonnull Direction side) {
		cube.addToList(quads, side);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	@Nonnull
	public TextureAtlasSprite getParticleTexture() {
		return particle;
	}

	@Override
	@Nonnull
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.EMPTY;
	}

	@Override
	@Nonnull
	public Pair<? extends IBakedModel, javax.vecmath.Matrix4f> handlePerspective(@Nonnull ItemCameraTransforms.TransformType type) {
		Matrix4f matrix;
		if (DefaultTransformations.blockTransforms.containsKey(type)) {
			matrix = DefaultTransformations.blockTransforms.get(type).getMatrix(Direction.byIndex(0));
			return Pair.of(this, matrix);
		}
		return net.minecraftforge.client.ForgeHooksClient.handlePerspective(this, type);
	}

}
