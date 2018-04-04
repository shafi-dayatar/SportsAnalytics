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
    age:{
        type: Number,
        required: 'Kindly enter the age of the player'

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
    },
    height: {
        type: Number,
        required: 'Kindly enter the height of the player'
    },
    weight: {
        type: Number,
        required: 'Kindly enter the weight of the player'
    }
});

module.exports = mongoose.model('Player', PlayerSchema);
