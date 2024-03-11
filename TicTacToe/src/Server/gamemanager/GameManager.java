package Server.gamemanager;

import Server.gameroom.GameRoom;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class GameManager
{
    static HashMap<String , GameRoom> mapOfGameRooms;

    private final Map<String, AbstractMap.SimpleEntry<Integer, GameRoom>> sessionIdToGameRoomMap = new HashMap<>();
    private final ExecutorService threadsOfGameRooms;

    private static Integer arrayIndex =-1;

    final Integer FIRST_PORT = 49152;
    final Integer LAST_PORT = 49162;

    private final List<Integer> ports = new ArrayList<>();
    public GameManager(){
        threadsOfGameRooms = Executors.newCachedThreadPool();
        mapOfGameRooms = new HashMap<>();
        for(int index = 0; index < (LAST_PORT-FIRST_PORT); index++)
        {
            ports.add(FIRST_PORT + index);
        }
    }

    public synchronized List<String > createAndStartNewRoom(){
        Integer portNo = allocatePort();
        String  sessionId = generateSessionId();
        String roomId = sessionId + "-" + portNo.toString();
        System.out.println(roomId);
        if(portNo != -1){
            GameRoom gameRoomThread = new GameRoom(roomId,this);
            mapOfGameRooms.put(roomId, gameRoomThread);
            sessionIdToGameRoomMap.put(sessionId, new AbstractMap.SimpleEntry<>(portNo, gameRoomThread));
            threadsOfGameRooms.submit(gameRoomThread);
        }
        return List.of(portNo.toString(),sessionId);
    }

    public synchronized Integer validateSessionId(String sessionId) {
        List<String> roomId;
        try
        {
            roomId = mapOfGameRooms.keySet().stream().filter(e -> e.contains(sessionId)).collect(Collectors.toList());
            System.out.println(roomId);
            if (roomId.isEmpty()) {
                return -1;
            }
        }catch(NullPointerException e){
            System.out.println("Wrong Session Id !!");
            return -1;
        }
        return Integer.parseInt(roomId.get(0).split("-")[1]);
    }
    public synchronized void removeGameRoom(String roomId) {

        mapOfGameRooms.remove(roomId);
    }

    public Integer allocatePort(){
        arrayIndex = (arrayIndex+1)%(LAST_PORT-FIRST_PORT);
        return ports.get(arrayIndex);
    }

    public synchronized String generateSessionId() {
        long unique_no = System.currentTimeMillis();
        return Long.toString(unique_no);
    }

    public Integer getPortNoForSessionId(String sessionId) {
        AbstractMap.SimpleEntry<Integer, GameRoom> entry = sessionIdToGameRoomMap.get(sessionId);
        return entry != null ? entry.getKey() : -1;
    }

    public GameRoom getGameRoomForSessionId(String sessionId) {
        AbstractMap.SimpleEntry<Integer, GameRoom> entry = sessionIdToGameRoomMap.get(sessionId);
        return entry != null ? entry.getValue() : null;
    }
}
