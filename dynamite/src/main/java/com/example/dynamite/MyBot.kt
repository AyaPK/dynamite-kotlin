package com.example.dynamite

import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.Gamestate
import com.softwire.dynamite.game.Move
import kotlin.random.Random

class `MyBot` : Bot {
    var dynamiteCount:Int = 100;
    var arrayOfMoves = arrayOf(Move.R, Move.P, Move.S);
    var dynWater = arrayOf(Move.W, Move.D);
    var opposingLastMoves : MutableList<String> = ArrayList<String>();
    var moveScience : HashMap<String, String> = HashMap<String, String>();
    override fun makeMove(gamestate: Gamestate): Move {
        // Are you debugging?
        // Put a breakpoint in this method to see when we make a move
        if (gamestate.getRounds().size > 0 && gamestate.getRounds().size < 1010){
            var myLastMove:String = gamestate.getRounds()[gamestate.getRounds().size - 1].getP1().toString();
            var theirLastMove:String = gamestate.getRounds()[gamestate.getRounds().size - 1].getP2().toString();
            opposingLastMoves.add(theirLastMove);
            var lastResult : String = moveCompare(myLastMove, theirLastMove);
            var myMove = Move.D;
            var phaseTwo = false;
            var phaseTwoMove = "D"
            var arrayOfMovesTwo = arrayOf("R", "P", "S");
            if (opposingLastMoves.size > 10) {
                for (move in arrayOfMovesTwo) {
                    val predicate: (String) -> Boolean = {it == move}
                    val result = opposingLastMoves.count(predicate)
                    if (result > opposingLastMoves.size/2){
                        phaseTwo = true;
                        phaseTwoMove = move;
                    }
                }
            }
            if (phaseTwo) {
                myMove = when(phaseTwoMove) {
                    "D" -> Move.W
                    "W" -> Move.P
                    "R" -> Move.P
                    "P" -> Move.S
                    "S" -> Move.R
                    else -> Move.R
                }
                return myMove
            }

            if (lastResult == "Loss"){
                myMove = when(theirLastMove) {
                    "D" -> Move.W
                    "W" -> Move.S
                    "R" -> Move.P
                    "P" -> Move.S
                    "S" -> Move.R
                    else -> Move.P
                }
            } else if (lastResult == "Win"){
                myMove = when(myLastMove) {
                    "D" -> Move.W
                    "W" -> Move.S
                    "R" -> Move.P
                    "P" -> Move.S
                    "S" -> Move.R
                    else -> Move.R
                }
            } else {
                myMove = Move.D
            }
            if (myMove.toString() == "D" && this.dynamiteCount > 5){
                this.dynamiteCount -= 1
            } else if (myMove.toString() == "D" && this.dynamiteCount > 5){
                return Move.W;
            }
            return myMove;
        } else if (gamestate.getRounds().size >= 101) {

        }
        else // Turn 1
        {
            this.dynamiteCount -= 1;
            return Move.D
        }
        return Move.P
    }

    fun moveCompare(myMove:String, theirMove:String):String{
        if (myMove == "D"){
            if (theirMove == "D") {
                return "Draw";
            } else if (theirMove == "W") {
                return "Loss"
            } else {
                return "Win"
            }
        } else if (myMove == "W") {
            if (theirMove == "D") {
                return "Win";
            } else if (theirMove == "W") {
                return "Draw"
            } else {
                return "Loss"
            }
        } else if (myMove == "R") {
            if (theirMove == "D") {
                return "Loss";
            } else if (theirMove == "W") {
                return "Win"
            } else if (theirMove == "R") {
                return "Draw"
            } else if (theirMove == "P") {
                return "Loss"
            } else if (theirMove == "S") {
                return "Win"
            }
        } else if (myMove == "P") {
            if (theirMove == "D") {
                return "Loss";
            } else if (theirMove == "W") {
                return "Win"
            } else if (theirMove == "R") {
                return "Win"
            } else if (theirMove == "P") {
                return "Draw"
            } else if (theirMove == "S") {
                return "Loss"
            }
        } else if (myMove == "S") {
            if (theirMove == "D") {
                return "Loss";
            } else if (theirMove == "W") {
                return "Win"
            } else if (theirMove == "R") {
                return "Loss"
            } else if (theirMove == "P") {
                return "Win"
            } else if (theirMove == "S") {
                return "Draw"
            }
        }
        return "Draw"
    }

    init {
        // Are you debugging?
        // Put a breakpoint on the line below to see when we start a new match
        println("Started new match")
        opposingLastMoves = ArrayList<String>();
    }
}