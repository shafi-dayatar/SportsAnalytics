'use strict';


var mongoose = require('mongoose'),
    Player = mongoose.model('Player');



exports.create_player = function(req, res) {
    var new_player = new Player(req.body);
    new_player.save(function(err, player) {
        if (err)
            res.send(err);
        res.json(player);
    });
};

