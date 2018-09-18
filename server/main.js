var express  = require('express');
var sockio   = require('socket.io');
var app      = express();
var http     = require('http').Server(app);
var io       = sockio.listen(http);
var mongoose = require('mongoose');

http.listen(3000);

io.set("polling duration",10);
io.set("close timeout",60);

var scoreSchema;

mongoose.connect("mongodb://localhost/WaveGameB4").then(
  scoreSchema = mongoose.Schema({
    username : String,
    score    : Number
  }));

//create a schema for storing scores


Score = mongoose.model('Score',scoreSchema);


io.on('connection',function(socket){

  socket.on("getScore",function(data){
    Score.findOne({}).sort('-score').exec(function(err,result){
      if (err){
        console.log(err);
      } else {
        if (result != null){
          console.log(result);
          socket.send(result.username + ": "+result.score);
        }

      }
    });
  });

  console.log('Connected');


  socket.on('setScore',function(data){
    var workingScore = new Score(data);
    console.log("WorkingScore:");
    console.log(workingScore);

    workingScore.save(function(err){
      if(err){console.log(err);}
      else {console.log("Saved!");}
    });

  });


  socket.on('getBoard',function(data){

    var leaderboardString = "";

    Score.find().sort('-score').limit(10).exec(function(err,scores){
      if (err) console.log(err);
      else {
        console.log("Reached");
        for (var i = 0; i < scores.length; i++){
          leaderboardString += scores[i].username+" : "+scores[i].score+",";
        }

        console.log(leaderboardString);

        socket.send(leaderboardString);
      }
    });
  });

  socket.on('getTopScore',function(socket){
    Score.find().sort(-score).limit(1).exec(function(err,scores){
      console.log(scores);
    });
  });

});
