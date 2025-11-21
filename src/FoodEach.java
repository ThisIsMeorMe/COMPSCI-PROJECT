public class FoodEach
{
  private String name;
  private int price = 0;
  private int reqMoney;
  private boolean unlocked = false;
  public FoodEach(String n, int p, int r, boolean u) {
    name = n;
    price = p;
    reqMoney = r;
    unlocked = u;
  }
  public String getName() {
    return name;
  }
  public int getPrice() {
    return price;
  }
  public int getReqMoney() {
    return reqMoney;
  }
  public boolean getUnlocked() {
    return unlocked;
  }
  public boolean unlock(int money) {
    //return true if successfully unlocks
    if (unlocked) {
      return false;
    }
    if (money >= reqMoney) {
      unlocked = true;
      return true;
    } else {
      return false;
    }
  }
  public boolean buy(int money) {
    //return true if purchase is successful
    return money >= price;
  }

}
