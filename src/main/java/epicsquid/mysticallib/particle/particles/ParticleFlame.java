package epicsquid.mysticallib.particle.particles;

import epicsquid.mysticallib.particle.ParticleBase;
import net.minecraft.world.World;

public class ParticleFlame extends ParticleBase {
  public float colorR = 0;
  public float colorG = 0;
  public float colorB = 0;
  public float initScale = 0;
  public float initAlpha = 0;

  public ParticleFlame(World world, double x, double y, double z, double vx, double vy, double vz, double[] data) {
    super(world, x, y, z, vx, vy, vz, data);
    this.colorR = (float) data[1];
    this.colorG = (float) data[2];
    this.colorB = (float) data[3];
    if (this.colorR > 1.0) {
      this.colorR = this.colorR / 255.0f;
    }
    if (this.colorG > 1.0) {
      this.colorG = this.colorG / 255.0f;
    }
    if (this.colorB > 1.0) {
      this.colorB = this.colorB / 255.0f;
    }
    this.setColor(colorR, colorG, colorB);
    this.setAlphaF((float) data[4]);
    this.initAlpha = (float) data[4];
    this.particleScale = (float) data[5];
    this.initScale = (float) data[5];
  }

  @Override
  public void tick() {
    super.tick();
    float lifeCoeff = (float) this.age / (float) this.maxAge;
    this.particleScale = initScale - initScale * lifeCoeff;
    this.particleAlpha = initAlpha * (1.0f - lifeCoeff);
  }

}
