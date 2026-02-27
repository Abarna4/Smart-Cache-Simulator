import java.util.*;
public class Cache_Simulator {
    public static void main(String[] args) {
        int cache_Size=3;
        int arr[]={1, 2, 3, 1, 4, 5, 2, 1, 6};
        FIFO(cache_Size,arr);
        LRU(cache_Size, arr);
        LFU(cache_Size, arr);
    }
    public static void FIFO(int size,int[] arr){
        Set<Integer> cache=new HashSet<>();
        Queue<Integer> queue=new LinkedList<>();
        int hit=0;
        int miss=0;
        for(int x:arr){
            if(cache.contains(x)){
                hit++;
            }
            else{
                miss++;
                if(cache.size()==size){
                    int removed=queue.poll();
                    cache.remove(removed);
                }
                cache.add(x);
                queue.add(x);
            }
        }
        Display("FIFO",hit,miss);
    }
    public static void LRU(int size,int arr[]){
        Set<Integer> cache=new HashSet<>();
        Map<Integer,Integer> recent=new HashMap<>();
        int time=0,hit=0,miss=0;
        for(int x:arr){
            time++;
            if(cache.contains(x)){
                hit++;
            }
            else{
                miss++;
                if(cache.size()==size){
                    int lru=Integer.MAX_VALUE,key=-1;
                    for(int k:cache){
                        if(recent.get(k)<lru){
                            lru=recent.get(k);
                            key=k;
                        }
                    }
                    cache.remove(key);
                }
                cache.add(x);
            }
            recent.put(x,time);
        }
        
        Display("LRU", hit, miss);
    }
    public static void LFU(int size,int arr[]){
        Map<Integer,Integer> freq=new HashMap<>();
        Set<Integer> cache=new HashSet<>();
        int hit=0,miss=0;
        for(int x:arr){
            if(cache.contains(x)){
                hit++;
                freq.put(x,freq.get(x)+1);
            }
            else{
                miss++;
                if(cache.size()==size){
                int lfu=Integer.MAX_VALUE,key=-1;
                for(int k:cache){
                    if(freq.get(k)<lfu){
                        lfu=freq.get(k);
                        key=k;
                    }
                }
                cache.remove(key);
                freq.remove(key);
            }
            cache.add(x);
            freq.put(x,1);
            }
        }
        Display("LFU", hit, miss);
    }
    public static void Display(String algo,int hit,int miss){
        int total=hit+miss;
        double ratio=(double)hit/total;
        System.out.println(algo+" Policy");
        System.out.println("Hits: "+hit);
        System.out.println("Misses "+miss);
        System.out.println("Hit Ratio: "+ratio);
        System.out.println("------------------------------");
    }
}
