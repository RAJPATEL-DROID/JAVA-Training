const { response } = require("express");
const http = require("http");
const app = require("express")();
app.get("/",(req,res)=>{
    res.sendFile(__dirname + "/index.html");
})
app.listen(9091, ()=>{
    console.log("Listening on HTTP port");
})
const webSocketServer = require("websocket").server;
const httpServer = http.createServer();

httpServer.listen(9090, () =>{
    console.log("Listeninig...on 9090");
});

//hashmap
const clients = {};
const games ={}


const wsServer = new webSocketServer({
    "httpServer" : httpServer
})

wsServer.on("request", request =>{

    const connection = request.accept(null, request.origin);
    connection.on("open",()=>{console.log("opened!")})
    connection.on("close",()=>{console.log("closed!")})
 
    connection.on("message",(message=>{
        const result = JSON.parse(message.utf8Data);

                // Create new game
                if(result.method === "create"){
                    const client = result.clientId;
                    const gameId = guid();
                    games[gameId] = {
                        "id" : gameId,
                        "balls" : 20,
                        "clients" : []
                    }
                    const payLoad = {
                        "method" : "create",
                        "game" : games[gameId]
                    }

                    const con = clients[clientId].connection;
                    con.send(JSON.stringify(payLoad));
                }

                // Client want to join game
                if(result.method === "join"){
                    const clientId = result.clientId;
                    const gameId = result.gameId;
                    const game = games[gameId];
                    if(game.clients.length >= 3){
                        // MAx Player limit
                        return;
                    }
                    const color = {"0":"Red","1":"Green","2" : "Blue"}[game.clients.length]
                    game.clients.push({
                        "clientId" : clientId,
                        "color" : color
                    })
                    
                    // Start the game
                    if(game.clients.length === 3) updateGameState();
                    const payLoad = {
                        "method"  : "join",
                        "game" : game
                    }

                    // Broadcast other clients 
                    game.clients.forEach(c => {
                        clients[c.clientId].connection.send(JSON.stringify(payLoad))
                    })
                }

                // User play
                if(result.method === "play"){
                    
                    const gameId = result.gameId;
                    const ballId = result.ballId;
                    const color = result.color;
                    let state = games[gameId].state;

                    if(!state){
                        state = {}
                    }

                    state[ballId] = color;
                    games[gameId].state = state

                    // const payLoad = {
                    //     "method": "play",
                    //     "game" : game
                    // }
                }
        })
    );

    // Generating a new clientID
    const clientId = guid();
    clients[clientId] = {
        "connection" : connection
    }

    const payLoad = {
        "method" : "connect",
        "clientId" : clientId
    }

    connection.send(JSON.stringify(payLoad));
    
})


function updateGameState(){
    
    for(const g of Object.keys(games)){
        const game = games[g];
        const payLoad = {
            "method" : "update",
            "game" : game
        }
        game.clients.forEach(c => {
            clients[c.clientId].connection.send(JSON.stringify(payLoad));
        })
    }
    setTimeout(updateGameState, 500);
}

function S4(){
    return (((1+ Math.random())*0x10000) | 0).toString(16).substring(1);

}

const guid = () => (S4() + S4() + "-" + S4() + "-4" + S4().substring(0,3)+"-"+S4() + "-" + S4() + S4() + S4()).toLowerCase();
