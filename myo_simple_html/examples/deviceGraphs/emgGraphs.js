//This tells Myo.js to create the web sockets needed to communnicate with Myo Connect
var stroke_type = "forehand"
var swing_type = "topspin"
var hand = "right_handed"
var battery_level = -1
var bluetooth_strength = 0
var rssi = 0
var arm_synched = false
var direction = ""
var warmupState = ""
var synced = ""
var rawData = [0,0,0,0,0,0,0,0]
var emg_header = ["timestamp", "orientation_x", "orientation_y", 
				  "orientation_z", "orientation_w", "AccX", 
				  "AccY", "AccZ", "GyroX", "GyroY", "GyroZ",
				  "connected","arm_synched", "device_direction", "warmupState", 
				  "synced", "battery_level", "rssi", "bluetooth_strength", 
				  "player_hand", "swing_type", "stroke_type" ]

var emg = [emg_header]

var eventsCapture = ["imu", 'arm_synced_status', 'battery_status', 'bluetooth_status']

var old_timestamp = 0

var download_every_five_min;


Myo.on('connected', function(){
	console.log('Connected to myo');
	this.streamEMG(true);
	connected = true;
	/*Myo.on('emg', function(data){
		rawData = data;
	});*/
	Myo.on('imu', function(imu_data, timestamp){
		if (timestamp>old_timestamp){
			old_timestamp = timestamp
			data = [timestamp, imu_data.orientation.x, 
			imu_data.orientation.y, imu_data.orientation.z,
			imu_data.orientation.w, imu_data.accelerometer.x, 
			imu_data.accelerometer.y, imu_data.accelerometer.z,  
			imu_data.gyroscope.x, imu_data.gyroscope.y, 
			imu_data.gyroscope.z, connected, arm_synched, 
			direction, warmupState, synced, battery_level, rssi,
			bluetooth_strength, hand, swing_type, stroke_type]
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
	Myo.off('imu')
	Myo.off('bluetooth_status')
	Myo.off('battery_status')
	clearInterval(download_every_five_min);
	console.log("Device has been disconnected")
})


function download(data, filename) {
	date = new Date()
	filename = "imu" + date.toUTCString().replace(/[, :]/g, "_") + ".csv"
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
		Myo.events = []
		$("#disconnect").prop('disabled', true);
		$("#connect").prop('disabled', false);
	});


	$("#forehand").click(function(){
		stroke_type = "Forehand"
	});
	$("#backhand").click(function(){
		stroke_type = "Backhand"
	});
	$("#topspin").click(function(){
		swing_type = "Topspin"
	});
	$("#slice").click(function(){
		swing_type = "Slice"
	});
	$("#flat").click(function(){
		swing_type = "Flat"
	});
	

});



var range = 150;
var resolution = 50;
var emgGraphs;

var graphData= [
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0)
]

/*$(document).ready(function(){

	emgGraphs = graphData.map(function(podData, podIndex){
		return $('#pod' + podIndex).plot(formatFlotData(podData), {
			colors: ['#8aceb5'],
			xaxis: {
				show: false,
				min : 0,
				max : resolution
			},
			yaxis : {
				min : -range,
				max : range,
			},
			grid : {
				borderColor : "#427F78",
				borderWidth : 1
			}
		}).data("plot");
	});


});

var formatFlotData = function(data){
		return [data.map(function(val, index){
				return [index, val]
			})]
}


var updateGraph = function(emgData){

	graphData.map(function(data, index){
		graphData[index] = graphData[index].slice(1);
		graphData[index].push(emgData[index]);

		emgGraphs[index].setData(formatFlotData(graphData[index]));
		emgGraphs[index].draw();


	})

}




*/