package wool.module.product;


import wool.module.product.t1.ProductAntimatter;
import wool.module.product.t1.ProductCNT;
import wool.module.product.t2.*;
import wool.module.product.t3.*;
import wool.module.product.t4.ProductElementDark;
import wool.module.product.t4.ProductElementLight;
import wool.root.AppModule;

public class product extends AppModule {
    //t1
    public static ProductCNT ProductCnt;
    public static ProductAntimatter ProductAntimatter;
    public static ProductChrominar ProductChrominar;
    public static ProductKyrium ProductKyrium;
    public static ProductNexarion ProductNexarion;
    public static ProductSkyforge ProductSkyforge;
    public static ProductSuperconductor ProductSuperconductor;
    public static ProductElementMetal ProductElementMetal;
    public static ProductElementWood ProductElementWood;
    public static ProductElementWater ProductElementWater;
    public static ProductElementFire ProductElementFire;
    public static  ProductElementEarth ProductElementEarth;
    public static ProductElementLight ProductElementLight;
    public static ProductElementDark ProductElementDark;

    //
    public static void init() {
        ProductCnt = new ProductCNT();
        ProductAntimatter = new ProductAntimatter();
        ProductChrominar = new ProductChrominar();
        ProductKyrium = new ProductKyrium();
        ProductNexarion = new ProductNexarion();
        ProductSkyforge = new ProductSkyforge();
        ProductSuperconductor = new ProductSuperconductor();
        ProductElementMetal = new ProductElementMetal();
        ProductElementWood = new ProductElementWood();
        ProductElementWater = new ProductElementWater();
        ProductElementFire = new ProductElementFire();
        ProductElementEarth = new ProductElementEarth();
        ProductElementLight = new ProductElementLight();
        ProductElementDark = new ProductElementDark();
    }

    public static void deploy() {

    }
}
