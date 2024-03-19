package com.server.gamemanager;

import com.server.gameroom.GameRoom;
import com.server.utils.ConfigReader;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class GameManager
{
    private final Map<Long, AbstractMap.SimpleEntry<Integer, GameRoom>> sessionIdToGameRoomMap = new HashMap<>();

    private final ExecutorService threadsOfGameRooms;

    private static Integer arrayIndex =-1;

    private static final AtomicLong ssid = new AtomicLong(1000);

    final int FIRST_PORT_OF_GAME_ROOM;

    final int LAST_PORT_OF_GAMEROOM;

    private final List<Integer> ports = new ArrayList<>();
    public GameManager(ConfigReader configReader){

        FIRST_PORT_OF_GAME_ROOM = configReader.getFIRST_PORT_OF_GAME_ROOM();

        LAST_PORT_OF_GAMEROOM = configReader.getLAST_PORT_OF_GAMEROOM();

        threadsOfGameRooms = Executors.newCachedThreadPool();

        for(int index = 0; index < (LAST_PORT_OF_GAMEROOM - FIRST_PORT_OF_GAME_ROOM); index++)
        {
            ports.add(FIRST_PORT_OF_GAME_ROOM + index);
        }
    }

    public synchronized List<String > createAndStartNewRoom()
    {
        Integer portNo = allocatePort();

        Long sessionId = ssid.getAndAdd(10);

        String roomId = sessionId + "-" + portNo.toString();

        if(portNo != -1)
        {
            GameRoom gameRoomThread = new GameRoom(roomId);

            sessionIdToGameRoomMap.put(sessionId, new AbstractMap.SimpleEntry<>(portNo, gameRoomThread));

            threadsOfGameRooms.submit(gameRoomThread);

        }

        return List.of(portNo.toString(),sessionId.toString());
    }

    public Integer allocatePort(){

        arrayIndex = (arrayIndex+1)%(LAST_PORT_OF_GAMEROOM - FIRST_PORT_OF_GAME_ROOM);

        return ports.get(arrayIndex);
    }

    public Integer validateSessionAndSendPortNo(Long sessionId) {

        AbstractMap.SimpleEntry<Integer, GameRoom> entry = sessionIdToGameRoomMap.get(sessionId);

        return entry != null ? entry.getKey() : -1;
    }

}