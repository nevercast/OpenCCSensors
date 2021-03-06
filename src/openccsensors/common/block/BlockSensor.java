package openccsensors.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;
import openccsensors.OpenCCSensors;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.common.tileentity.TileEntitySensor;
import openccsensors.common.util.MiscUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockSensor extends BlockContainer {

	public Icon turtleIcon;
	private Icon icon;
	
	public BlockSensor() {
		
		super(OpenCCSensors.Config.sensorBlockID, Material.ground);
		setHardness(0.5F);
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
		GameRegistry.registerBlock(this, "sensor");
		GameRegistry.registerTileEntity(TileEntitySensor.class, "sensor");
		setUnlocalizedName("openccsensors.sensor");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySensor();
	}

	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
		return icon;
    }

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int i, int damage)
	{
		return icon;
	}

	@Override
	public void func_94332_a(IconRegister iconRegister)
	{
		turtleIcon = iconRegister.func_94245_a("openccsensors:turtleSensor");
		icon = iconRegister.func_94245_a("openccsensors:sensor");
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		MiscUtils.dropInventoryItems(world.getBlockTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z,
			int metadata, ForgeDirection face) {
		return 0;
	}
	
	@Override
	public int idDropped(int metadata, Random random, int zero) {
		return this.blockID;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
		ForgeDirection orientation = MiscUtils.get2dOrientation(Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ), Vec3.createVectorHelper(x, y, z));
		world.setBlockMetadataWithNotify(x, y, z, orientation.getOpposite().ordinal(),1);
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return OpenCCSensors.renderId;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				return false;
			}
			player.openGui(OpenCCSensors.instance, 1987, world, x, y, z);
			return true;
		}
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(World world, int x, int y, int z)
	{
	    return false;
	}
	
	@Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
	{
		return false;
	}
}
