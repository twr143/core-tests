package gasStation;

/**
 * Created by ilya on 28.11.2016.
 */
public class GasStationEntry
{
  //returns first failed point in the journey or -1 if success
  static int journeyFailIndex(int[] gas,int[] costs, int start_ind){
    int length= gas.length;
    int g=0;
    int index;
    for (int i=0;i<length;i++){
      index = (start_ind+i)%length;
      g+=gas[index];
      g-=costs[index];
      if (g<0) return index;
    }
    return -1;
  }
  static int canCompleteCircuit(int[] gas, int[] cost) {
  	int sumRemaining = 0; // track current remaining
  	int total = 0; // track total remaining
  	int start = 0;

  	for (int i = 0; i < gas.length; i++) {
  		int diff = gas[i] - cost[i];

  		//if sum remaining of (i-1) >= 0, continue
  		if (sumRemaining >= 0) {
  			sumRemaining += diff;
  		//otherwise, reset start index to be current
  		} else {
  			sumRemaining = diff;
  			start = i;
  		}
  		total += diff;
  	}

  	if (total >= 0){
  		return start;
  	}else{
  		return -1;
  	}
  }
  public static void main(String[] args)
  {
    int[] gas={1,3,2,4,1};
    int[] cost={2,3,1,5,0};
//    for (int start_ind= 0;start_ind<gas.length;start_ind++)
//      System.out.println("s:"+start_ind+" res:"+journeyFailIndex(gas,cost,start_ind));
    System.out.println( "res:"+canCompleteCircuit(gas,cost));
  }
}
