'use strict';

module.exports = function(app) {
    var playerController = require('../controllers/playerController');

    app.route('/player')
        .post(playerController.create_player);

};