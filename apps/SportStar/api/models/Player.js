'use strict';
var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var PlayerSchema = new Schema({
    name: {
        type: String,
        required: 'Kindly enter the name of the player'
    },
    email: {
        type: String,
        required: 'Kindly enter the email of the player'
    },
    ageRange:{
        type: Number,
        required: 'Kindly enter the age range of the player'

    },
    password:{
        type: String,
        required: 'Kindly enter the password of the player'

    },
    handed: {
        type: [{
            type: String,
            enum: ['left', 'right']
        }],
        default: ['right']
    }
});

module.exports = mongoose.model('Player', PlayerSchema);
