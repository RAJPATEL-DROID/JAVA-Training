package Server.gamemanager;

import Server.gameroom.GameRoom;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class GameManager
{
    static HashMap<String , GameRoom> mapOfGameRooms;

    private final Map<Long, AbstractMap.SimpleEntry<Integer, GameRoom>> sessionIdToGameRoomMap = new HashMap<>();
    private final ExecutorService threadsOfGameRooms;

    private static Integer arrayIndex =-1;

    private static final AtomicLong ssid = new AtomicLong(1000);

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
        Long sessionId = ssid.getAndAdd(10);
//        String  sessionId = generateSessionId();
        String roomId = sessionId + "-" + portNo.toString();
        System.out.println(roomId);
        if(portNo != -1){
            GameRoom gameRoomThread = new GameRoom(roomId,this);
            mapOfGameRooms.put(roomId, gameRoomThread);
            sessionIdToGameRoomMap.put(sessionId, new AbstractMap.SimpleEntry<>(portNo, gameRoomThread));
            threadsOfGameRooms.submit(gameRoomThread);
        }
        return List.of(portNo.toString(),sessionId.toString());
    }

    public Integer allocatePort(){
        arrayIndex = (arrayIndex+1)%(LAST_PORT-FIRST_PORT);
        return ports.get(arrayIndex);
    }

    public Integer getPortNoForSessionId(Long sessionId) {
        System.out.println("SessionId provided by the user: " + sessionId);
        AbstractMap.SimpleEntry<Integer, GameRoom> entry = sessionIdToGameRoomMap.get(sessionId);
        return entry != null ? entry.getKey() : -1;
    }


}