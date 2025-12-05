import java.util.ArrayList;
import java.util.List;

public class FoodEach
{
  private String name;
  private int price = 0;
  private int reqMoney;
  private boolean unlocked = false;
  public static ArrayList<String> allUnlockedFood = new ArrayList<String>();
  
  public static java.util.Map<String, FoodEach> registry = new java.util.HashMap<>();
  public FoodEach(String n, int p, int r, boolean u) {
    name = n;
    price = p;
    reqMoney = r;
    unlocked = u;
    if (u) {
      allUnlockedFood.add(n);
    }
    registry.put(n, this);
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
    
    return money >= price;
  }

  public static FoodEach getByName(String n) {
    return registry.get(n);
  }

}
