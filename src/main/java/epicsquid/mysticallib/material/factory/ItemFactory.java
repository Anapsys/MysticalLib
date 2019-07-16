package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.item.Item;

public class ItemFactory implements IItemMaterialFactory {

	private String name = "";

	public ItemFactory() {
	}

	public ItemFactory(String name) {
		this.name = name;
	}

	@Override
	public Item create(IMaterial material, String modid) {
		return new Item(material.getItemProps()).setRegistryName(modid, material.getName() + getSuffix());
	}

	@Override
	public String getSuffix() {
		return !name.equals("") ? "_" + getName() : "";
	}

	@Override
	public String getName() {
		return name;
	}
}
