var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#monitoring-events").show();
    } else {
        $("#monitoring-events").hide();
    }
    $("#events").html("");
}

function connect() {
    var socket = new SockJS('monitor-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        $("connectionMessage").val("websocket connection established!");
        console.log('Connected: ' + frame);
        stompClient.subscribe('/web-socket/events', function (message) {
            showEvent(JSON.parse(message.body).payload);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/web-socket/events", {}, JSON.stringify({'payload': 'Test message sent through stomp client.'}));
}

function showEvent(message) {
    $("#events").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
    $("#monitoring-events").hide();
});