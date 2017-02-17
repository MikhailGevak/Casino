# Simple Casino
Maven project "Simple Casino" consists of 4 modules:server-and-client, wallet-service, game-service and tests.
##SERVER-AND-CLIENT
Module contains the a common functionality and used by *wallet-service* and *game-service*.

##WALLET-SERVER
Module contains a *Wallet REST Service*. 
###Main API
####Function: Register a wallet (create balance)
```
Path: /wallet/register/{player_id}
Type: POST
Body: -
Result: JSON wich contains created balance
```
####Function: Deposit
```
Path: /wallet/deposit/{player_id}
Type: POST
Body: Amount of deposit as a plain/text
Result: JSON wich contains updated balance
Response code: 200
```
####Function: Withdraw
```
Path: /wallet/withdraw/{player_id}
Type: POST
Body: Amount of withdraw as a plain/text
Result: JSON wich contains updated balance
Response code: 200
```
####Function: Remove Balance
```
Path: /wallet/balance/{player_id}
Type: GET
Result: JSON wich contains balance of player with id {palyer_id}
Response code: 200
```
####Function: Delete Balance
```
Path: **/wallet/remove/{player_id}**
Type: POST
Body: -
Result: true
Response code: 200
```
####Exceptions
If error is occured it is returned as JSON with suitable http-status.
For example:
```
{
"errorCode":140,
"errorMessage":"Player not found (playerId:3454545)",
"errorClass":"newage.wallet.api.exception.PlayerNotFoundException"
}
```
###Properties
Default properties of the server:
```
database.uri = jdbc:sqlite:wallet.db
database.driver=org.sqlite.JDBC

rest.server.host_name = http://localhost
rest.server.port = 9999
rest.server.context_path = 
```
You can override it using VM argumnts (-D...)

*errorClass* property is a service information and used in service's clients for deserializing JSON.
##GAME_SERVICE
Module contains a *Game REST Service*. 
###API
####Function: Place Bet
```
Path: bet/create/{player_id}/{game_id}
Type: POST
Body: Amount of bet as a plain/text
Result: JSON wich contains updated balance
Response code: 200
```
####Function: Show Bets
```
Path: bet/get/{player_id}
Type: GET
Result: JSON wich contains a list of bets for player with id {player_id}
Response code: 200
```
####Exceptions
If error is occured it is returned as JSON with suitable http-status.
For example:
```
{
"errorCode":140,
"errorMessage":"Player not found (playerId:3454545)",
"errorClass":"newage.wallet.api.exception.PlayerNotFoundException"
}
```
*errorClass* property is a service information and used in service's clients for deserializing JSON.
###Properties
Default properties of the server:
```
database.uri = jdbc:sqlite:bets.db
database.driver=org.sqlite.JDBC

rest.server.host_name = http://localhost
rest.server.port = 9998
rest.server.context_path = 

rest.servlet.property.com.sun.jersey.config.property.packages = newage.game.rest

wallet.rest.server = http://localhost:9999/wallet
```
You can override it using VM argumnts (-D...)

##TESTS
Module contains functional tests.

#BUIILD SERVICE
You can build service using maven (mvn install). Also you can run each service from its projects folder.
wallet-service:
```
mvn exec:java -Dexec.args="src/main/resources/wallet.properties"

```
game-service:
```
mvn exec:java -Dexec.args="src/main/resources/wallet.properties"

```
#Run Services
##Wallet Service
###Run Service
```
./run-wallet.sh <port_number>
```
###Stop Service
```
./stop-wallet.sh
```
##Game Service
###Run Service
```
./run-game.sh <port_number>
```
###Stop Service
```
./stop-game.sh
```
##Simple Casino
3. Scripts and guides how to start the whole system

4. Simple Casino should be started as

◦ One instance of Wallet Service

◦ Two instances of Game Service

5. Source code for functional test scenario

6. Brief guide how to execute functional test scenario against working system
