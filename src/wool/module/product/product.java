package wool.module.product;


import wool.module.product.t1.ProductAntimatter;
import wool.module.product.t1.ProductCNT;
import wool.module.product.t2.ProductChrominar;
import wool.module.product.t2.ProductKyrium;
import wool.module.product.t2.ProductNexarion;
import wool.root.AppModule;

public class product extends AppModule {
    //t1
    public static ProductCNT ProductCnt;
    public static ProductAntimatter ProductAntimatter;
    public static ProductChrominar ProductChrominar;
    public static ProductKyrium ProductKyrium;
    public static ProductNexarion ProductNexarion;

    //
    public static void init() {
        ProductCnt = new ProductCNT();
        ProductAntimatter = new ProductAntimatter();
        ProductChrominar = new ProductChrominar();
        ProductKyrium = new ProductKyrium();
        ProductNexarion = new ProductNexarion();
    }

    public static void deploy() {

    }
}
