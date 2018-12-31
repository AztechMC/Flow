package com.aztech.flow;

import com.aztech.flow.handler.ModGuiHandler;
import com.aztech.flow.proxy.CommonProxy;
import com.aztech.flow.tab.CustomCreativeTab;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import org.apache.logging.log4j.Logger;

@Mod(modid = Flow.MODID, name = Flow.NAME, version = Flow.VERSION)
public class Flow {
	
	@Instance
	public static Flow instance;
	
    public static final String MODID = "flow";
    public static final String NAME = "Flow";
    public static final String VERSION = "0.1";

    public static Logger logger;
   


    @SidedProxy(clientSide = "com.aztech.flow.proxy.ClientProxy", serverSide = "com.aztech.flow.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    public static final CustomCreativeTab CREATIVE_TAB = new CustomCreativeTab("flowTab");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
        logger.info("Done with PreInitialization");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Done with Initialization");
    }
}
