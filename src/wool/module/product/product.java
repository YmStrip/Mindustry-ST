package wool.module.product;


import wool.module.product.t1.ProductCNT;
import wool.root.AppModule;

public class product extends AppModule {
    //t1
    public static ProductCNT ProductCnt;

    //
    public static void init() {
        ProductCnt = new ProductCNT();
    }

    public static void deploy() {

    }
}
