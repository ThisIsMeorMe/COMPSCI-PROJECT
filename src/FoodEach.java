import java.util.ArrayList;
import java.util.List;

public class FoodEach
{
  private String name;
  private int price = 0;
  private int reqMoney;
  private boolean unlocked = false;
  public static ArrayList<String> allUnlockedFood = new ArrayList<String>();
  public FoodEach(String n, int p, int r, boolean u) {
    name = n;
    price = p;
    reqMoney = r;
    unlocked = u;
    if (u) {
      allUnlockedFood.add(n);
    }
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
      allUnlockedFood.add(this.name);
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
