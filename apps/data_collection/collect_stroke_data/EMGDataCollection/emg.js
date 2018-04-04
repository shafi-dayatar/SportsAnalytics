//This tells Myo.js to create the web sockets needed to communnicate with Myo Connect
var grip_type = "None"
var player_hand = "right_handed"
var battery_level = -1
var bluetooth_strength = 0
var rssi = 0
var arm_synched = false
var direction = ""
var warmupState = ""
var synced = ""
var rawData = [0,0,0,0,0,0,0,0]
var emg_header = ["timestamp", "pod1", "pod2", "pod3", "pod4", "pod5",
                  "pod6", "pod7", "pod8",
				  "connected","arm_synched", "device_direction", "warmupState", 
				  "synced", "battery_level", "rssi", "bluetooth_strength", 
				  "player_hand", "grip_type" ]

var emg = [emg_header]

var eventsCapture = ["imu", 'arm_synced_status', 'battery_status', 'bluetooth_status']

var old_timestamp = 0

var download_every_five_min = 1000 * 60 * 5;


Myo.on('connected', function(){
	console.log('Connected to myo');
	this.streamEMG(true);
	connected = true;
	/*Myo.on('emg', function(data){
		rawData = data;
	});*/
	Myo.on('emg', function(emg_data, timestamp){
		if (timestamp>old_timestamp){
			old_timestamp = timestamp
			data = [timestamp, emg_data, connected, arm_synched, 
			direction, warmupState, synced, battery_level, rssi,
			bluetooth_strength, player_hand, grip_type]
			emg.push(data);

		}
	})

	Myo.on('arm_synced_status', function(data, timestamp){
		arm_synched = data.arm
		direction = data.x_direction
		warmupState = data.warmup_state
		synced = data.synced
	})

	Myo.on('battery_status', function(data, timestamp){
		battery_level = data.battery_level;
	})

	Myo.on('bluetooth_status', function(data, timestamp){
		rssi = data.rssi
		bluetooth_strength = data.bluetooth_strength
	})

	download_every_five_min = setInterval(function(){ download(emg.join("\n")) }, 300000);
})

Myo.on('disconnected', function(){
	download(emg.join("\n"))
	connected = false
	Myo.off('emg');
	Myo.off('bluetooth_status')
	Myo.off('battery_status')
	clearInterval(download_every_five_min);
	console.log("Device has been disconnected")
})


function download(data) {
	date = new Date()
	filename = "emg" + date.toString().replace(/[, :]/g, "_") + ".csv"
    var file = new Blob([data], {type: 'text/csv'});
    if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(file, filename);
    else { // Others
        var a = document.createElement("a"),
                url = URL.createObjectURL(file);
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        setTimeout(function() {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);  
        }, 0); 
    }
}


//This tells Myo.js to create the web sockets needed to communnicate with Myo Connect

$(document).ready(function() {
	$("#disconnect").prop('disabled', true);


	$("#connect").click(function(){
		Myo.connect('com.myojs.emgGraphs');
		console.log(Myo)
		$("#connect").prop('disabled', true);
		$("#disconnect").prop('disabled', false);
	});
	$("#disconnect").click(function(){
		Myo.disconnect();
		download(emg.join("\n"))
		Myo.events = []
		$("#disconnect").prop('disabled', true);
		$("#connect").prop('disabled', false);
	});

	$("#FBevel2").click(function(){
		grip_type = "The Continental Grip  (Bevel #2)"
	});
	$("#FBevel3").click(function(){
		grip_type = "The Eastern Forehand Grip (Bevel #3)"
	});
	$("#FBevel4").click(function(){
		grip_type = "The Semi-Western Forehand Grip (Bevel #3)"
	});
	$("#FBevel5").click(function(){
		grip_type = "The Western Forehand Grip (Bevel #5)"
	});
	$("#BBevel1").click(function(){
		grip_type = "The Eastern Backhand Grip (Bevel #1)"
	});
	$("#BBevel8").click(function(){
		grip_type = "The Semi-Western Backhand grip (Bevel #8)"
	});
	$("#BBevel6").click(function(){
		grip_type = "The Double-Handed Backhand Grip (F: Bevel #2 + B: Bevel #6)"
	});
	$("#fist").click(function(){
		grip_type = "Fist"
	});
	$("#openFingers").click(function(){
		grip_type = "Open Hand"
	});
	$("#waveLeft").click(function(){
		grip_type = "Wave Left"
	});
	$("#waveRight").click(function(){
		grip_type = "Wave Right"
	});
	

});



var range = 150;
var resolution = 50;
var emgGraphs;