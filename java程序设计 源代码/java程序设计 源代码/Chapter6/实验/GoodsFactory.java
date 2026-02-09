  package test;
  public classGoodsFactory {
      public static Goods getorder(String name) {
          if (name.equalsIgnoreCase("cola")) {//如果需要cola，则创建cola对象
              return new cola();
          } else if (name.equalsIgnoreCase("Sprite")) { //如果需要Sprite，则创建Sprite对象
              return new Sprite ();
          } else {
              return null;  //暂时不支持其他车型
         }
     }
 }
