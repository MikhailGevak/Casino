# Simple Casino
Maven project "Simple Casino" consists of 4 modules:server-and-client, wallet-service, game-service and tests.
##SERVER-AND-CLIENT
Module contains the a common functionality and used by *wallet-service* and *game-service*.

##WALLET-SERVER
Module contains a *Wallet REST Service*. 
###Main API
Function: Register a wallet (create balance)<p>
Path: **/wallet/register/{player_id}**<p>
Type: POST<p>
Body: -
Result: JSON wich contains created balance<p>
<p>
Function: Deposit <p>
Path: **/wallet/deposit/{player_id}**<p>
Type: POST<p>
Body: Amount of deposit as a plain/text<p>
Result: JSON wich contains updated balance<p>
Response code: 200<p>
<p>
Function: Withdraw <p>
Path: **/wallet/withdraw/{player_id}**<p>
Type: POST<p>
Body: Amount of withdraw as a plain/text<p>
Result: JSON wich contains updated balance<p>
Response code: 200<p>
<p>
Function: Remove Balance <p>
Path: **/wallet/balance/{player_id}**<p>
Type: GET<p>
Result: JSON wich contains balance of player with id {palyer_id}<p>
Response code: 200<p>
<p>
Function: Delete Balance<p>
Path: **/wallet/remove/{player_id}**<p>
Type: POST<p>
Body: -
Result: true <p>
Response code: 200<p>
###Exceptions
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
Function: Place Bet <p>
Path: **bet/create/{player_id}/{game_id}**<p>
Type: POST<p>
Body: Amount of bet as a plain/text<p>
Result: JSON wich contains updated balance<p>
Response code: 200<p>
<p>
Function: Show Bets<p>
Path: **bet/get/{player_id}**<p>
Type: GET<p>
Result: JSON wich contains a list of bets for player with id {player_id}<p>
Response code: 200<p>
###Exceptions
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
2. Brief guide how to build services

3. Scripts and guides how to start the whole system

4. Simple Casino should be started as

◦ One instance of Wallet Service

◦ Two instances of Game Service

5. Source code for functional test scenario

6. Brief guide how to execute functional test scenario against working system
